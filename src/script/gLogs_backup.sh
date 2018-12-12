#!/bin/bash

#
# Author: Dariusz Matejewski [SPRC] d.matejewski@samsung.com
#
# Version 2.4 - added database gathering for phone app in eng mode.
# Version 2.3
#
# Automatic log gathering script for "Joyn" (the redesigned version) on Android devices.
# The script automatically detect any connected android device and starts gathering "logcat" and "tcpdump" output in background.
# When "Joyn" is installed, the script tries also to extract configuration and database files of the app. 
#

# Internal SD card mount point - vary between device models.
# GS2/GS3 	- "/mnt/sdcard"
# GSx 		- "/storage/sdcard0"
INTERNAL_SD_MOUNT_POINT="/mnt/sdcard"

# Log storage devices. The command line paremeter decides which device to use. Default is internal sdcard. "-e" parameter switches to external sdcard.
LOG_STORAGE=$INTERNAL_SD_MOUNT_POINT						#internal sdcard
# GS2 - ... /external_sd
# GS3 - ... /extSdCard
LOG_STORAGE_EXTERNAL=$INTERNAL_SD_MOUNT_POINT"/extSdCard"	#extenal sdcard

# internal directory structure
LOG_TEMP_DIR="auto_log_temp"

# Remote file names to be downloaded.
RMT_LOG_FILE="log.txt"
RMT_PCAP_FILE="shark"
RMT_SKT_SEND=$INTERNAL_SD_MOUNT_POINT"/data/SktSendDump.txt"
RMT_SKT_RECV=$INTERNAL_SD_MOUNT_POINT"/data/SktRecvDump.txt"	
RMT_SIP_MSG_CNT=$INTERNAL_SD_MOUNT_POINT"/data/SIPMessageCount.txt"
RMT_SIP_MSG_1=$INTERNAL_SD_MOUNT_POINT"/data/SIPMessage1.txt"
RMT_SIP_MSG_2=$INTERNAL_SD_MOUNT_POINT"/data/SIPMessage2.txt"

# Remote directories to be downloaded.
RMT_DATA_LOG_DIR="/data/log"
RMT_DATA_ANR_DIR="/data/anr"
RMT_DATA_TOMBSTONES_DIR="/data/tombstones"
RMT_OBSOLETE_CONFIGURATION_PATH=$INTERNAL_SD_MOUNT_POINT"/data/IMS"
RMT_CONFIGURATION_PATH=$INTERNAL_SD_MOUNT_POINT"/RCSe-cfg"

# Local subdirectories to be created.
SUB_PCAP="pcap"
SUB_DATA_ANR="data_anr"
SUB_DATA_TOMBSTONES="data_tombstones"
SUB_DATA_LOG="data_log"
SUB_CFG="cfg"
SUB_DATABASES="databases"

# Marker file name indicating configuration files export is done. Should contain RCSe/PDA/Lib version information.
RMT_EXPORT_MARKER_NAME="VerInfo.txt"

# RCEs settings export intent name and intent extra parameter.
EXPORT_INTENT_NAME="com.sec.ims.android/.dev.ConfigurationFilesExporter" 
EXPORT_INTENT_EXTRA="EXPORT_DESTINATION_DIR"

# Set prop before launching export service. Allows to be done only on dev PDA's.
RCS_DEV_LOG_PROP="RCS_DEV_LOG 1"

function print_help {
	
	echo "***************************************************************************"
	echo "Script available switches:"
	echo ""
	echo "-a Performs local logs directory archive before reomval on script start."
	echo "-k Keeps logcat buffer intact on start. Do not inssue 'logcat -c' command before logcat start."
	echo "-e Captures logs into external sdcard. Usefull when performing tests that need the internal sdcard be fully filled up." 
	echo "-d dir_name Subdirectoyr name for logs storage."
	echo "-h Print this help message"
	echo ""
	echo "***************************************************************************"
	echo ""
}


# Helper function to remove remote file if it exists
# Param1 - device serial number.
# Param2 - remote file with full path.
#
function remove_remote_file {
	adb -s $1 shell "if ls $2 2>/dev/null 1>/dev/null; then rm $2 ; fi"
}


# Extracts comand line parameters and asign them to global variables
#
function parse_comandline_parameters {
	
	PARAMS="haked:"
	
	while getopts $PARAMS optionName; do
		case "$optionName" in
			h) 	print_help 				# Prints help message with all switches described.
				exit 1 ;;
			a) CML_MAKE_OLD_LOG_ARCHIVE="true" ;;	# Indicates that already localy existing logs should be archived before deletion.
			k) CML_KEEP_LOGCAT_BUFFER="true" ;;		# Do not perform "logcat -c" before starting logcat.
			e) CML_EXTERNAL_STORAGE="true" ;;		# Changes storage location from internal sdcard to external sdcard.
			d) CML_LOCAL_SUBDIR="$OPTARG" ;;		
			[?]) 	echo "Unsupported parameter!"
				exit 1 ;;
		esac
	done	
}


# Set up global variables according to command line parameters
#
function set_up_globals {

	if [ "$CML_EXTERNAL_STORAGE"  == "true" ] ; then
		LOG_STORAGE=$LOG_STORAGE_EXTERNAL
	fi
	
	LOG_TEMP_DIR=$LOG_STORAGE"/"$LOG_TEMP_DIR
	
	RMT_LOG_FILE=$LOG_TEMP_DIR"/"$RMT_LOG_FILE  
	RMT_PCAP_FILE=$LOG_TEMP_DIR"/"$RMT_PCAP_FILE
}


# Execute "adb devices" to determine devices connected.
#
function get_device_list {
	echo ""
	echo "*** Searching for connected devices... ***"
	
	adb start-server
	DEVICES=`adb devices | grep -v devices | grep device | cut -f1`

	if [ "$DEVICES" != "" ]; then 
			IND=0;
			for DEV in ${DEVICES[*]}
			do
				DEV_SERIAL[$IND]=$DEV
				let IND=$IND+1
				echo Found device $DEV
			done
	else
		echo No devices found!
		sleep 2
		exit
	fi
}

function disable_knox {

	for SERIAL in ${DEV_SERIAL[@]}
	do
		adb -s $SERIAL shell su -c setenforce 0
	done
}


# Blocking function that delay script execution till async Intent finishes it work. Function wait for couple of seconds for intent to finish configuration files copy.
# Returns when configuration export is done or time exceeds. 
# Param1 - device serial number.
#
function check_export_complete {

	WAIT=1;
	for INDEX in {1..3..1}
	do	
		MARKER_PRESENT=`adb -s $1 shell "ls $RMT_CONFIGURATION_PATH/$RMT_EXPORT_MARKER_NAME 2> /dev/null"`		
		
		if [ "$MARKER_PRESENT" != "" ] ; then		
			break		
		fi

		let WAIT=$INDEX*$WAIT
		sleep $WAIT
	done
	
	#check reg info exist or more than one file	
	CONFIG_DIR_CONTENT=`adb -s $1 shell "ls $RMT_CONFIGURATION_PATH 2>/dev/null" | grep -v $RMT_EXPORT_MARKER_NAME`
		
	if [ "$CONFIG_DIR_CONTENT" != "" ] ; then
			echo "Found SIM dependent configuration for device $1."
			#adb -s $1 shell rm $RMT_CONFIGURATION_PATH/$RMT_EXPORT_MARKER_NAME
			return 0	
	else	
		echo "SIM dependent configuration not found for device $1. Using generic '$RMT_OBSOLETE_CONFIGURATION_PATH' configuration if exist."	
		return 1	
	fi	
}

# Fires an Android Intent to RCSe application. RCSe should than perform configuration files copy to user accessible direcotry. 
# When done, DEV_CONFIG variable is set to point on a exported configuration files path.
#
function export_rcs_configuration {
	echo ""
	echo "*** Broadcasting export configuration intent to all connected devices. ***"
		
	for SERIAL in ${DEV_SERIAL[@]}
	do  
		adb -s $SERIAL shell setprop $RCS_DEV_LOG_PROP 
		adb -s $SERIAL shell am startservice $EXPORT_INTENT_NAME -e $EXPORT_INTENT_EXTRA $RMT_CONFIGURATION_PATH		
	done
		
	IND=0;
	for SERIAL in ${DEV_SERIAL[@]}
	do
		# Wait for export to complete
		check_export_complete $SERIAL
		
		if [ $? -eq 1 ]; then
			DEV_CONFIG[$IND]=$RMT_OBSOLETE_CONFIGURATION_PATH
		else
			DEV_CONFIG[$IND]=$RMT_CONFIGURATION_PATH
		fi
		
		let IND=$IND+1
	done
			
}


# Parse "Multibearer.txt" file to determine user friendly name, that will be used for file and directory naming.
#
function get_devices_names {
	echo ""
	echo "*** Determining devices names according to 'Multibearer.txt' file... ***"
	
	IND=0
	for SERIAL in ${DEV_SERIAL[@]}
	do
		NAME=`adb -s $SERIAL shell cat ${DEV_CONFIG[$IND]}/Multibearer.txt | grep Username | head -n1 | cut -d= -f2 | cut -d# -f 1 | cut -d: -f2`
		if [ "$NAME" == "" ]; then
			NAME=$SERIAL
		else
			for EXISTING_NAME in ${DEV_NAME[@]}
			do
				if [ "$NAME" == "$EXISTING_NAME" ]; then
					NAME=$NAME"_"$IND
				fi
			done
		fi
		DEV_NAME[$IND]=$NAME		
		let IND=$IND+1
		
		echo Matching device $SERIAL name to $NAME
	done
}


# Creates archive of already existing localy logs
#
function archive_existing_logs {

	if [ "$CML_MAKE_OLD_LOG_ARCHIVE" == "true" ] ; then
	
		echo ""
		echo "*** Creating archive of already existing logs... ***"
	
		TIME_STAMP=`date +%F_%H-%M-%S`
		
		for NAME in ${DEV_NAME[@]}
		do
			if [ -d $NAME ]; then 				
				ARCHIVE_NAME=./$NAME"_"$TIME_STAMP".tar"
				echo "Creating archive $ARCHIVE_NAME"				
				tar -cf $ARCHIVE_NAME $NAME
			fi
		
		done
	fi
}

# Prepare local directories.
#
function prepare_local_directories {
	echo ""
	echo "*** Preparing local directories for the log dump... ***"

	for NAME in ${DEV_NAME[@]}
	do
		if [ -d $NAME ]; then 			
			rm -R $NAME 
		fi
		mkdir -p $NAME
		mkdir -p $NAME/$SUB_PCAP
		mkdir -p $NAME/$SUB_DATA_ANR
		mkdir -p $NAME/$SUB_DATA_TOMBSTONES
		mkdir -p $NAME/$SUB_DATA_LOG
		mkdir -p $NAME/$SUB_CFG	
	done	
}


# Prepare remote directories.
#
function prepare_remote_directories {
	echo ""
	echo "*** Erasing old logs from all connected devices... ***"
	
	for SERIAL in ${DEV_SERIAL[@]}
	do		
		remove_remote_file $SERIAL $RMT_SKT_SEND
		remove_remote_file $SERIAL $RMT_SKT_RECV
		remove_remote_file $SERIAL $RMT_SIP_MSG_CNT
		remove_remote_file $SERIAL $RMT_SIP_MSG_1
		remove_remote_file $SERIAL $RMT_SIP_MSG_2		

		adb -s $SERIAL shell rm -r $LOG_TEMP_DIR
		adb -s $SERIAL shell mkdir $LOG_TEMP_DIR								
		adb -s $SERIAL shell rm -r $RMT_DATA_LOG_DIR
		adb -s $SERIAL shell mkdir $RMT_DATA_LOG_DIR
		adb -s $SERIAL shell rm -r $RMT_DATA_ANR_DIR
		adb -s $SERIAL shell mkdir $RMT_DATA_ANR_DIR
		adb -s $SERIAL shell rm -r $RMT_DATA_TOMBSTONES_DIR
		adb -s $SERIAL shell mkdir $RMT_DATA_TOMBSTONES_DIR
		
		adb -s $SERIAL shell 'rm -r $RMT_CONFIGURATION_PATH 2> /dev/null'
	done
}


# Start background logcat proceses.
#
function start_logcat {
	echo ""
	echo "*** Starting logcat... ***"

	INDEX=0
	for SERIAL in ${DEV_SERIAL[@]}
	do
		if [ "$CML_KEEP_LOGCAT_BUFFER" != "true" ] ; then
			adb -s $SERIAL logcat -c
		fi		
		adb -s $SERIAL logcat -v threadtime -f $RMT_LOG_FILE &
		PID_LOG[$INDEX]=$!
		let INDEX=$INDEX+1
	done
}

# Function invoked after CTRL+C signalc was caught - to make clean exit and prevent zombies.
#
function script_break(){
	stop_logcat
	stop_shark
	exit 0
}

# Start background tcpdump proceses
#
function start_shark {
	echo ""
	echo "*** Starting wireshark... ***"

	# when CTRL+C is pressed after processes were already thrown to background
	trap script_break SIGINT	
	
	INDEX=0
	for SERIAL in ${DEV_SERIAL[@]}
	do
		shark_process_monitor $SERIAL $RMT_PCAP_FILE &
		PID_CAP[$INDEX]=$!
		let INDEX=$INDEX+1
	done
		
}

# Cleans background tcpdum proceses to prevent zombies
#
function shark_monitor_post_kill(){	
	kill $PID
	exit 0
}


# Starts monitor for tcpdump processes and respawns them on crash.
#
function shark_process_monitor {

	# allows to kill forked proces by main process
	trap shark_monitor_post_kill TERM	

	FILE_INDEX=0;
	while [ 1 ]; do	
		FILE_NAME=$2"_"$FILE_INDEX".pcap"

		# Check if "tcpdump" binary exists on the device.
		check_tcpdump $1
		
			
		if [ $F_RET_VAL == 1 ] ; then
			echo "tcpdump binary not found! You're probably trying to gather logs from user mode device. Wireshark stopped..."
			return 0	
		fi	
		
		# Run "tcpdump".adb 
		adb -s $1 shell tcpdump -i any -p -s 0 -w $FILE_NAME &

		
		# Wait for "tcpdump" to complete. If it has creashed, this thread will respawn it. If This thread was break, the "TRAP" of this tread will kill "tcpdump" correctly and this thread end without respawnig "tcpdump".
		PID=$!		
		wait $PID
	
		echo "tcpdump crashed. Respawning thread..."
		let FILE_INDEX=$FILE_INDEX+1
			
	done	
}


# Checks if "tcpdump" binary exists and provide exit code. Returns "0" if the tcpdump binary exists. "1" if not.
#
function check_tcpdump(){
	
	NOT_FOUND_STRING="No such file or directory"
	
	OUTPUT=`adb -s $1 shell ls /system/xbin/tcpdump` 
			
	RET_VAL=`echo $OUTPUT | grep "$NOT_FOUND_STRING"`
	
	if [ "$RET_VAL" != "" ] ; then	
		# File doesn't exist		
		F_RET_VAL=1
	else
		# File exists
		F_RET_VAL=0
	fi
}

#function tcp_mockup(){
#	echo "Mockup start $1"	
#	sleep 3 
#	echo "Mockup died"
#	return 1
#}

# Wait for the user signal to finish logs gathering.
#
function wait_for_user {
	sleep 2
	echo ""
	echo Press "STOP LOGS" button to stop logs gathering
	echo ""
	read
}


# Kill logcat bakground proceses.
#
function stop_logcat {
	echo ""
	echo "*** Stopping logcat... ***"
	
	for PID in ${PID_LOG[@]}
	do
		kill $PID
	done
}


# Kill tcpdump background proceses.
#
function stop_shark {
	echo ""
	echo "*** Stopping wireshark... ***"
	
	for PID in ${PID_CAP[@]}
	do
		kill -s TERM $PID
	done
}


# Download logs from the devices to local hdd.
#
function download_logs {
	echo ""
	echo "*** Downloading logs... ***"

	INDEX=0
	for SERIAL in ${DEV_SERIAL[@]}
	do
		# Logs, captures and dumps
#		adb -s $SERIAL pull $RMT_LOG_FILE 		./${DEV_NAME[$INDEX]}/${DEV_NAME[$INDEX]}.logcat				
		adb -s $SERIAL pull $LOG_TEMP_DIR 		./${DEV_NAME[$INDEX]}
		adb -s $SERIAL pull $RMT_SKT_SEND 		./${DEV_NAME[$INDEX]}/${DEV_NAME[$INDEX]}_SktSendDump.txt 		2>/dev/null
		adb -s $SERIAL pull $RMT_SKT_RECV		./${DEV_NAME[$INDEX]}/${DEV_NAME[$INDEX]}_SktRecvDump.txt 		2>/dev/null
		adb -s $SERIAL pull $RMT_SIP_MSG_CNT 	./${DEV_NAME[$INDEX]}/${DEV_NAME[$INDEX]}_SIPMessageCount.txt 	2>/dev/null
		adb -s $SERIAL pull $RMT_SIP_MSG_1 		./${DEV_NAME[$INDEX]}/${DEV_NAME[$INDEX]}_SIPMessage1.txt 		2>/dev/null
		adb -s $SERIAL pull $RMT_SIP_MSG_2 		./${DEV_NAME[$INDEX]}/${DEV_NAME[$INDEX]}_SIPMessage2.txt 		2>/dev/null

		adb -s $SERIAL pull $RMT_DATA_ANR_DIR		 					./${DEV_NAME[$INDEX]}/$SUB_DATA_ANR 		2>/dev/null
		adb -s $SERIAL pull $RMT_DATA_TOMBSTONES_DIR					./${DEV_NAME[$INDEX]}/$SUB_DATA_TOMBSTONES 	2>/dev/null
		adb -s $SERIAL pull $RMT_DATA_LOG_DIR							./${DEV_NAME[$INDEX]}/$SUB_DATA_LOG 		2>/dev/null


		# RCSe configuration files
		adb -s $SERIAL pull ${DEV_CONFIG[$INDEX]} 								./${DEV_NAME[$INDEX]}/$SUB_CFG 2>/dev/null
		
		# RCSe version file
		adb -s $SERIAL pull $RMT_CONFIGURATION_PATH 							./${DEV_NAME[$INDEX]}/$SUB_CFG 2>/dev/null
		
		# System properties dump
		adb -s $SERIAL shell getprop > ./${DEV_NAME[$INDEX]}/props.txt
						
		let INDEX=$INDEX+1		
	done
	
	echo ""
	echo "All Done."
}

# Download logs phone app to local hdd. Works only in eng mode for now.
#
function download_logs_from_phone_app {
	echo ""
	echo "*** Downloading db from phone app... ***"

	INDEX=0
	for SERIAL in ${DEV_SERIAL[@]}
	do
		adb -s $SERIAL pull /data/data/com.android.providers.telephony/databases			./${DEV_NAME[$INDEX]}/telephony_db 2>/dev/null
						
		let INDEX=$INDEX+1		
	done
	
	echo ""
	echo "All Done."
}


# Check if script is invoked under cygwin shell. If so, it 'cd' to a default 'c:\logs' directory and saves logs there.
#
function set_shell_defaults {
	
	CYGWIN_SHELL=`bash --version | grep cygwin`
	if [ "$CYGWIN_SHELL" != "" ]; then
		mkdir -p /cygdrive/c/logs/$CML_LOCAL_SUBDIR
		cd /cygdrive/c/logs/$CML_LOCAL_SUBDIR
	else
		if [ "$CML_LOCAL_SUBDIR" != "" ] ; then
			mkdir $CML_LOCAL_SUBDIR
			cd $CML_LOCAL_SUBDIR
		fi
	fi

}


# Function that perfomrs pre exit finishing works.
#
function pre_exit_cleanup {

	INDEX=0
	for SERIAL in ${DEV_SERIAL[@]}
	do
		#rearange Version information file location
		if [ -f ./${DEV_NAME[$INDEX]}/$SUB_CFG/$RMT_EXPORT_MARKER_NAME ] ; then
			mv ./${DEV_NAME[$INDEX]}/$SUB_CFG/$RMT_EXPORT_MARKER_NAME ./${DEV_NAME[$INDEX]}/$RMT_EXPORT_MARKER_NAME						
		fi
		
		if [ -d ./${DEV_NAME[$INDEX]}/$SUB_CFG/$SUB_DATABASES ] ; then
			mv ./${DEV_NAME[$INDEX]}/$SUB_CFG/$SUB_DATABASES ./${DEV_NAME[$INDEX]}/$SUB_DATABASES						
		fi
			
		# Remove exported configuration files from the device
		adb -s $SERIAL shell "rm -r $RMT_CONFIGURATION_PATH 2> /dev/null"

		pcap_rename $INDEX

	done
}

# Finds all *.pcap files in local directory, renames them accordingly to the device's serial number and gathers them in single directory.
#
function pcap_rename(){
	
	PCAPS=`find ./${DEV_NAME[$1]} -name "*.pcap"`

	INDEX=0
	for FILE in $PCAPS ; do	
		mv $FILE ./${DEV_NAME[$1]}/$SUB_PCAP/${DEV_NAME[$1]}"_"$INDEX".pcap"		
		let INDEX=$INDEX+1
	done
	
}

parse_comandline_parameters $@
if [ "$!" == "1" ] ; then
	exit 0;
fi

set_up_globals
set_shell_defaults

get_device_list
disable_knox
prepare_remote_directories

export_rcs_configuration

start_logcat
start_shark

wait_for_user

stop_logcat
stop_shark

export_rcs_configuration
get_devices_names
archive_existing_logs
prepare_local_directories

download_logs
download_logs_from_phone_app

pre_exit_cleanup
echo Gathering logs finished

sleep 2

