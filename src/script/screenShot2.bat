@echo off
setlocal EnableDelayedExpansion
SET number=1
echo %number%
adb -s %1 shell screencap /sdcard/screen.png
:loop
IF EXIST c:/logs/%1_%number%.png (
set /A number=number+1
 goto loop
)
adb -s %1 pull /sdcard/screen.png c:/logs/%1_%number%.png"
adb -s %1 shell rm -r /sdcard/screen.png