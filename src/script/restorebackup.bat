@echo off
setlocal EnableDelayedExpansion

set found=0
set /p location2=<"src\location2.txt"

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (   

	echo Starting recovery for %%d

	adb -s %%d remount
    adb -s %%d shell su -c setenforce 0
	adb -s %%d shell stop
	
	if exist "%location2%\libims_engine.so" (
	adb -s %%d shell rm /system/lib/libims_engine.so >nul
	echo Restore libims_engine.so for %%d
	for /f %%i in ('adb -s %%d shell ls /system/lib ^| find /c "libims_engine.so"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/libims_engine.so /system/lib/libims_engine.so
	for /f %%i in ('adb -s %%d shell ls /system/lib ^| find /c "libims_engine.so"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\imsservice.apk" ( 
	echo Restore imsservice.apk for %%d
	adb -s %%d shell rm /system/app/imsservice.apk >nul
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "imsservice.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/imsservice.apk /system/app/imsservice.apk
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "imsservice.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\secimsfw.apk" ( 
	echo Restore secimsfw.apk for %%d
	adb -s %%d shell rm /system/app/secimsfw.apk >nul
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "secimsfw.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push backup/secimsfw.apk /system/app/secimsfw.apk
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "secimsfw.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\libSECIMSJni.so" (
	echo Restore libSECIMSJni.so for %%d
	adb -s %%d shell rm /system/lib/libSECIMSJni.so >nul
	for /f %%i in ('adb -s %%d shell ls /system/lib ^| find /c "libSECIMSJni.so"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/libSECIMSJni.so /system/lib/libSECIMSJni.so
	for /f %%i in ('adb -s %%d shell ls /system/lib ^| find /c "libSECIMSJni.so"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\libcpve-client.so" (
	echo Restore libcpve-client.so for %%d
	adb -s %%d shell rm /system/lib/libcpve-client.so >nul
	for /f %%i in ('adb -s %%d shell ls /system/lib ^| find /c "libcpve-client.so"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/libcpve-client.so /system/lib/libcpve-client.so
	for /f %%i in ('adb -s %%d shell ls /system/lib ^| find /c "libcpve-client.so"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\SecPhone.apk" (
	echo Restore SecPhone.apk for %%d
	adb -s %%d shell rm /system/app/SecPhone.apk >nul
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "SecPhone.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/SecPhone.apk /system/app/SecPhone.apk
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "SecPhone.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\ImsTelephonyService.apk" (
	echo Restore ImsTelephonyService.apk for %%d
	adb -s %%d shell rm /system/app/ImsTelephonyService.apk >nul
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "ImsTelephonyService.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/ImsTelephonyService.apk /system/app/ImsTelephonyService.apk
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "ImsTelephonyService.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\libsec-ril.so" (
	echo Restore libsec-ril.so for %%d
	adb -s %%d shell rm /system/lib/libsec-ril.so >nul
	for /f %%i in ('adb -s %%d shell ls /system/lib ^| find /c "libsec-ril.so"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/libsec-ril.so /system/lib/libsec-ril.so
	for /f %%i in ('adb -s %%d shell ls /system/lib ^| find /c "libsec-ril.so"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\imsmanager.jar" (
	echo Restore imsmanager.jar for %%d
	adb -s %%d shell rm /system/framework/imsmanager.jar >nul
	for /f %%i in ('adb -s %%d shell ls /system/framework ^| find /c "imsmanager.jar"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/imsmanager.jar /system/framework/imsmanager.jar
	for /f %%i in ('adb -s %%d shell ls /system/framework ^| find /c "imsmanager.jar"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	if exist "%location2%\ImsSettings.apk" (
	echo Restore ImsSettings.apk for %%d
	adb -s %%d shell rm /system/app/ImsSettings.apk >nul
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "ImsSettings.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %location2%/ImsSettings.apk /system/app/ImsSettings.apk
	for /f %%i in ('adb -s %%d shell ls /system/app ^| find /c "ImsSettings.apk"') do set VAR=%%i
	if !VAR! == 1 ( echo "RESTORE SUCCESS" ) else  ( echo "RESTORE FAIL")
	)
	adb -s %%d sync
	adb -s %%d reboot
	echo restore done for %%d
	echo.

)
