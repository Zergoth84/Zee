@echo off
setlocal EnableDelayedExpansion

set found=0

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (
adb -s %%d shell screencap /sdcard/screen_%%d_%1.png
)
echo All tasks done
