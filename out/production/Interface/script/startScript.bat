 @echo off
adb -s %1 shell rm /sdcard/movie.mp4
adb -s %1 shell rm /sdcard/movie2.mp4
adb -s %1 shell rm /sdcard/movie3.mp4
adb -s %1 shell rm /sdcard/movie4.mp4
adb -s %1 shell rm /sdcard/movie5.mp4
adb -s %1 shell rm /sdcard/movie6.mp4
adb -s %1 shell rm /sdcard/movie7.mp4
adb -s %1 shell rm /sdcard/movie8.mp4
adb -s %1 shell rm /sdcard/movie9.mp4
adb -s %1 shell rm /sdcard/movie10.mp4
REM for /f "tokens=2 delims==; " %%a in (' wmic /node:%COMPUTERNAME% process call create "%CD%\recordVideo.bat %1" ^| find "ProcessId" ') do set PID=%%a
for /f "tokens=2 delims==; " %%a in (' wmic /node:ASDA2 process call create "C:\Users\z.moszko\Documents\NetBeansProjects\Interface\src\script\recordVideo.bat %1" ^| find "ProcessId" ') do set PID=%%a
echo "%PID%"
echo %COMPUTERNAME%
echo %CD%
pause
taskkill /F /T /PID %PID%
adb -s %1 pull /sdcard/movie.mp4 c:\logs\%1\movie.mp4

adb -s %1 pull /sdcard/movie2.mp4 c:\logs\%1\movie2.mp4

adb -s %1 pull /sdcard/movie3.mp4 c:\logs\%1\movie3.mp4

adb -s %1 pull /sdcard/movie4.mp4 c:\logs\%1\movie4.mp4

adb -s %1 pull /sdcard/movie5.mp4 c:\logs\%1\movie5.mp4

adb -s %1 pull /sdcard/movie6.mp4 c:\logs\%1\movie6.mp4

adb -s %1 pull /sdcard/movie7.mp4 c:\logs\%1\movie7.mp4

adb -s %1 pull /sdcard/movie8.mp4 c:\logs\%1\movie8.mp4

adb -s %1 pull /sdcard/movie9.mp4 c:\logs\%1\movie9.mp4

adb -s %1 pull /sdcard/movie10.mp4 c:\logs\%1\movie10.mp4
