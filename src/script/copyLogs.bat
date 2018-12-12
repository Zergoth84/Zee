@echo off
REM SETLOCAL ENABLEDELAYEDEXPANSION

%~d2
cd %2
rmdir /s /q %2\%1\telephony_db
del %2\%1\log.txt 
del %2\%1\props.txt
del %2\%1\shark_0.pcap
xcopy c:\windows\temp\%1 %2\%1 /si
IF EXIST %2/%1 (
rmdir /s /q c:\windows\temp\%1
rmdir c:\windows\temp\%1
echo Logs successful copied from %1
exit
)
echo Failed to copy from %1
REM adb -s %1 shell rm -r /sdcard/%1