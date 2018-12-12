@echo off
setlocal EnableDelayedExpansion

set found=0

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (   
	
@echo delete ims databases for %%d
adb -s %%i shell rm -rf /data/data/com.sec.imsservice/databases
adb -s %%i shell rm /data/data/com.sec.imsservice/shared_prefs/state.xml

)

