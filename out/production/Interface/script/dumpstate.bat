@echo off
setlocal EnableDelayedExpansion
set found=0

echo Gathering dumpstate for %1 initialized - please wait
adb -s %1 shell dumpstate > %2/%1/%1_dumpstate.txt
echo Dumpstate logs gathering is done for %1