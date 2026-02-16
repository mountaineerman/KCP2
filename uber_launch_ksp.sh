game_folder="/home/anton/KerbalSpaceProgram/KSP_GameArchive/KSP_linux"
game_settings="$game_folder/settings.cfg"
launch_scripts="/home/anton/KerbalSpaceProgram/KCP2/LaunchScripts"
dev_settings="$launch_scripts/settings.cfg.dev"
play_settings="$launch_scripts/settings.cfg.play"

echo "==============================================================================================================="
echo "KSP"
echo "---------------------------------------------------------------------------------------------------------------"

# 1) Update Settings File
argument="$1"
if [[ "$argument" != "-d" && "$argument" != "-p" ]]; then
    echo "Error: Specify launch mode: -d (Dev) or -p (Play)."
    exit 1
fi

echo "Overwriting settings.cfg file..."

if ! cmp -s "$game_settings" "$dev_settings" && ! cmp -s "$game_settings" "$play_settings"; then
    echo "Error: current GAME_SETTINGS do not match DEV_SETTINGS or PLAY_SETTINGS:"
    echo "  game_settings:$game_settings"
    echo "   dev_settings:$dev_settings"
    echo "  play_settings:$play_settings"
    echo ""
    echo "Discard/backup:"
    echo "====================================================== Settings vs Dev ======================================================"
    diff -y -W 150 --left-column --suppress-common-lines $game_settings $dev_settings
    echo " "
    echo "====================================================== Settings vs Play ====================================================="
    diff -y -W 150 --left-column --suppress-common-lines $game_settings $play_settings
    echo ""
    echo "Aborting..."
    exit 1
fi

if [[ "$argument" == "-d" ]]; then #Launch with DEV settings
    cp -p $dev_settings $game_settings
else #Launch with PLAY settings
    cp -p $play_settings $game_settings
fi


# 2) Launch KSP
echo "Launching KSP..."
cd $game_folder
TERM=xterm ./KSP.x86_64 > ksp_script_launch_output.txt &


echo " "