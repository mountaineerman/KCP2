@ECHO OFF
cd "C:\dev\KCP2\LaunchScripts"
call uber_launch_KSP_dev.bat

cd "C:\dev\KCP2\LaunchScripts"
call uber_launch_OneNote.bat

cd "C:\dev\KCP2\LaunchScripts"
call uber_launch_VSCode.bat

::Git Bash must be called last, because it shows a message and pauses
cd "C:\dev\KCP2\LaunchScripts"
call uber_launch_GitBash.bat