@echo off
setlocal EnableDelayedExpansion

set found=0

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (   
    echo install for %%d
	if %1==true (
	adb -s %%d pull %5 %3/%6
	)
	adb -s %%d shell rm %5
	for /f %%i in ('adb -s %%d shell ls %4 ^| find /c "%6"') do set VAR=%%i
	if !VAR! == 1 ( echo "REMOVE FAIL" ) else  ( echo "REMOVE SUCCESS")
	adb -s %%d push %2/%6 %5
	for /f %%i in ('adb -s %%d shell ls %4 ^| find /c "%6"') do set VAR=%%i
	if !VAR! == 1 ( echo "INSTALL SUCCESS" ) else  ( echo "INSTALL FAIL")
	
)

