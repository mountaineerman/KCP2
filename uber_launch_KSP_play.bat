cd "C:\Users\anton\Documents\Kerbal Space Program\_KSP_CKAN_1.5.1_v3\KSP_win64"
echo "Overwriting settings.cfg file..."
copy settings.cfg settings.cfg.backup
copy "C:\dev\KCP2\settings.cfg.play" "C:\Users\anton\Documents\Kerbal Space Program\_KSP_CKAN_1.5.1_v3\KSP_win64\settings.cfg"
start "KSP" KSP_x64.exe