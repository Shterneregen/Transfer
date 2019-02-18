:: transmitter
@echo off
REM set /p command=Enter command: 
java -jar transfer.jar -c 127.0.0.1 9988 "C:\Windows\System32\server.jks" PASSWORD
pause