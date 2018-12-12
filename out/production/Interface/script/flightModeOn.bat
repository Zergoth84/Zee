REM @echo off
setlocal EnableDelayedExpansion
set found=0

adb -s %1 shell settings put global airplane_mode_on 1
adb -s %1 shell am broadcast -a android.intent.action.AIRPLANE_MODE --ez state true
