@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
%~d2
cd %2
rmdir /s /q %2\%1\IMS
echo IMS logs downloading from %1 initialized
adb -s %1 shell ls sdcard/ims_logs > C:\Windows\Temp\%1.txt <nul
sort /R C:\Windows\Temp\%1.txt >C:\Windows\Temp\%1_2.txt <nul
set /p temp= <C:\Windows\Temp\%1_2.txt
echo !temp!
del C:\Windows\Temp\%1
del C:\Windows\Temp\%1_2.txt
adb -s %1 pull sdcard/ims_logs/!temp!/ %2/%1/IMS
IF EXIST %2/%1/IMS (
echo IMS logs successful copied from %1
exit
)