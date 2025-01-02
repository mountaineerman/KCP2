::start "" "%ProgramFiles%\Git\bin\sh.exe" ^
::-c "cd /c/dev/KCP2 && git status && echo ' ' && echo 'REMEMBER: switch to the appropriate branch...' && /usr/bin/bash --login -i"

@ECHO OFF
echo " "
echo "Right-click, open Git Bash in C:\dev\KCP2, and switch to the appropriate branch..."
pause