@echo off
set /p FILE=Enter file name: 
java -jar transfer.jar -ts 127.0.0.1 9988 "%USERPROFILE%\client.jks" JKS_PASSWORD "%FILE%"
pause