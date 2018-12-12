@echo off
setlocal EnableDelayedExpansion

set found=0

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (   
	
	adb -s %%d reboot
	echo done for %%d

)

