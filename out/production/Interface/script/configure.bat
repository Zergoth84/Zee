@echo off
set found=0

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do ( 
echo Initial Configuration started for %%d 
adb -s %%d shell input keyevent 66
REM ping 192.0.2.2 -n 1 -w 2000 > nul
adb -s %%d shell input keyevent 66 
REM ping 192.0.2.2 -n 1 -w 1000 > nul
adb -s %%d shell input keyevent 122 122
adb -s %%d shell input keyevent 20 20 20 20 20 20 20 20 20 20 20 20 20 
adb -s %%d shell input keyevent 66
adb -s %%d shell input keyevent 20 20 66
adb -s %%d shell input keyevent 19 19 66 
ping 192.0.2.2 -n 1 -w 2000 > nul
adb -s %%d shell input keyevent 20 20 20 66
REM adb -s %%d shell input keyevent 20 123 123 20 20 66
adb -s %%d shell input keyevent 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 66
adb -s %%d shell input keyevent 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 66
adb -s %%d shell input keyevent 20 66
REM adb -s %%d shell input keyevent 20 66
echo done for %%d
)
