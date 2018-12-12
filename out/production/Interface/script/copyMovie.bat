@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
%~d2
cd %2
RMDIR /S /Q "%2/%1/video"
adb -s %1 pull /sdcard/%1 %2/%1/video
IF EXIST %2/%1/video (
echo Movies successful copied from %1
adb -s %1 shell rm -r /sdcard/%1
EXIT
)
echo Failed to copy from %1
REM adb -s %1 shell rm -r /sdcard/%1