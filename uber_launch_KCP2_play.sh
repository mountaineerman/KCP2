#KSP
cd /home/anton/KerbalSpaceProgram/KSP_GameArchive/KSP_linux
#echo "Overwriting settings.cfg file and launching KSP..."
#cp -p /home/anton/KerbalSpaceProgram/KCP2/LaunchScripts/settings.cfg.play settings.cfg
TERM=xterm ./KSP.x86_64 > ksp_script_launch_output.txt &

#KKIM (via JAR)
cd /home/anton/KerbalSpaceProgram/KCP2/KKIM
java -jar KKIM.jar
#TODO KKIM needs to handle being unable to connect to kRPC...