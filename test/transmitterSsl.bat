:: transmitter
@echo off
set /p file=Enter file name: 
java -jar transfer.jar -ts 127.0.0.1 9988 "C:\Windows\System32\server.jks" PSW "%file%"
pause