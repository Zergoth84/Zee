@echo off
setlocal EnableDelayedExpansion

set found=0

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (
REM echo install APNCreator for %%d
REM adb -s %%d install src/files/APNCreator.apk
REM	echo install CSCExecutor for %%d
REM	adb -s %%d install src/files/csc_executor_signed.apk
REM 	echo install JoynCleaner for %%d
REM     adb -s %%d install files/JoynCleaner.apk
	echo install SettingsInitializer for %%d
	adb -s %%d install files/SettingsInitializer.apk
REM    echo install RCSConfiguration for %%d
REM    adb -s %%d install files/RCSConfiguration.apk
	echo initialize for %%d
	adb -s %%d shell "am start -n com.samsung.settingsinitializer/.SettingsActivity"
)
echo All tasks done
