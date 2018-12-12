
@echo off
setlocal EnableDelayedExpansion

set found=0

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (
echo Removing contacts from %%default
adb -s %%d shell pm clear com.android.providers.contacts
)
echo All tasks done
