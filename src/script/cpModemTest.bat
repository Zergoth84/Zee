@echo off
set found=0

REM for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (  
echo CP modem test settings initialized for %1
adb -s %1 shell input keyevent 3 
adb -s %1 shell am start -n com.sec.keystringscreen/.KeyStringScreenActivity <nul
adb -s %1 shell input keyevent 17 18 9 14 13 13 10 10 13 15 10 14 15 18 20 66 20 66<nul
echo done for %1
REM )
