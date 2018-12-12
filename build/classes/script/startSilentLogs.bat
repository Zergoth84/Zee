@echo off
set found=0

REM for /f "skip=1 tokens=1,2" %%d in ('adb devices') do ( 
echo Silent Logs gathering for %1 initialized
REM adb -s %%d usb <nul
ping 192.0.2.6 -n 1 -w 500 <nul
adb -s %1 shell rm -r sdcard/log <nul
ping 192.0.2.6 -n 1 -w 500 <nul
adb -s %1 shell input keyevent 3 <nul
REM adb -s %%d shell input keyevent 5 <nul
adb -s %1 shell am start -n com.sec.keystringscreen/.KeyStringScreenActivity<nul
adb -s %1 shell input keyevent 17 18 16 16 7 7 18 20 66 20 66<nul
adb -s %1 shell input keyevent 20 20 20 20 20 20 20 20 20 20 66<nul
adb -s %1 shell input keyevent 66<nul

REM )
