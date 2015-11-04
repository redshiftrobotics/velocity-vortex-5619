@echo off
if exist notice.vbs (
    rem file exists
) else (
(
    echo Set objArgs = WScript.Arguments
    echo messageText = objArgs^(0^) 
    echo MsgBox messageText
) >notice.vbs
)
echo obligatory "F*** Gradle"
rem Delete the app if it exists, if not whatever.
adb uninstall com.qualcomm.ftcrobotcontroller
rem Install the new app...
adb install "./build/outputs/apk/FtcRobotController-debug.apk"
rem Good. Now let the user know
cscript "./notice.vbs"  "Finished install tasks."
pause
exit