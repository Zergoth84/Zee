@echo off
set found=0

REM for /f "skip=1 tokens=1,2" %%d in ('adb devices') do ( 
echo Stop silent logs for %1 initialized
adb -s %1 shell input keyevent 3 <nul
REM adb -s %%d shell input keyevent 5 <nul
adb -s %1 shell am start -n com.sec.keystringscreen/.KeyStringScreenActivity<nul
adb -s %1 shell input keyevent 17 18 16 16 7 7 18 20 66 20 66<nul
adb -s %1 shell input keyevent 20 20 20 20 20 20 20 20 20 20 66 <nul
adb -s %1 shell input keyevent 66<nul
adb -s %1 pull sdcard/log/ %2/%1
REM echo Gathering dumpstate for %1 initialized - please wait
REM adb -s %1 shell dumpstate > %2/%1/%1_dumpstate.txt
echo Silent logs gathering is done for %1
REM )