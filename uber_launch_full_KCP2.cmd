cd "C:\Users\anton\Documents\Kerbal Space Program\_KSP_CKAN_1.5.1_v3\KSP_win64"
start "KSP" KSP_x64.exe

start "Eclipse" "C:\Users\anton\eclipse\java-2020-09\eclipse\eclipse.exe"
::TODO: create executable instead of launching KKIM from Eclipse

start "" "%ProgramFiles%\Git\bin\sh.exe" ^
-c "cd /c/dev/KCP2 && git status && echo ' ' && echo 'REMEMBER: switch to the appropriate branch...' && /usr/bin/bash --login -i"
::echo "Remember to switch to the appropriate branch..."

echo "To launch KKIM: Ctrl+F11"
PAUSE
