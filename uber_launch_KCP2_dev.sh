clear

echo "==============================================================================================================="
echo "OneNote"
echo "-------------------------------------"
echo "(!) Reminder: Open OneNote"

echo "==============================================================================================================="
echo "git"
echo "-------------------------------------"
git status

echo "==============================================================================================================="
echo "KSP"
echo "-------------------------------------"
cd /home/anton/KerbalSpaceProgram/KSP_GameArchive/KSP_linux
echo "Overwriting settings.cfg file and launching KSP..."
cp -p /home/anton/KerbalSpaceProgram/KCP2/LaunchScripts/settings.cfg.dev settings.cfg
./KSP.x86_64 > ksp_script_launch_output.txt &

echo "==============================================================================================================="
echo "VS Code"
echo "-------------------------------------"
code
echo "(TBC) To launch KKIM, hit F5..."
