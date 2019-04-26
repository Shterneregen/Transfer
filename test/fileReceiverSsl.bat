@echo off
java -jar transfer.jar -rs 9988 "%USERPROFILE%\server.jks" JKS_PASSWORD
pause