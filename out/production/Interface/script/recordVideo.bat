@echo off
REM for /f "skip=1 tokens=1,2" %%d in ('adb devices') do ( 
echo Recording video initialized for  %1
adb -s %1 shell mkdir /sdcard/%1
adb -s %1 shell screenrecord /sdcard/%1/%2
REM )
