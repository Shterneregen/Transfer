@echo off
set /p FILE=Enter file name: 
java -jar transfer.jar -t 127.0.0.1 9988 "%FILE%"
pause