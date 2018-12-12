 
@echo off
setlocal EnableDelayedExpansion
for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (
adb -s %%d remount
adb -s %%d shell su -c setenforce 0
if %1==1 adb -s %%d shell ls -R "/system" > %2\systemApp.txt
if %1==2 adb -s %%d shell ls -R "/system" > C:\Windows\Temp\systemApp.txt
)
