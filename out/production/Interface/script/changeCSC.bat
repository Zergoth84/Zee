@echo off
set found=0

REM for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (  
echo Change CSC settings initialized for %1
adb -s %1 shell input keyevent 3 <nul
adb -s %1 shell am start -n com.sec.keystringscreen/.KeyStringScreenActivity<nul
adb -s %1 shell input keyevent 17 18 9 11 10 9 7 10 15 12 12 18 20 66 20 66<nul
echo done for %1

REM)
