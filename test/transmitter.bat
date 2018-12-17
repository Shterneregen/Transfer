:: transmitter
@echo off
set /p file=Enter file name: 
java -jar transfer.jar -t 127.0.0.1 9988 "%file%"
REM java -jar transfer.jar -c 9988 DSC_0258.jpg
pause