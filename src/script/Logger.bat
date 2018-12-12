echo off
setlocal EnableDelayedExpansion

for /f "skip=1 tokens=1,2" %%d in ('adb devices') do (


@echo Install/Start ImsLogger for %%d
adb -s %%d install src/files/ImsLogger+.apk >nul
adb -s %%d shell am start -n com.sec.imslogger/.MainActivity >nul

@echo Done for %%d

)
