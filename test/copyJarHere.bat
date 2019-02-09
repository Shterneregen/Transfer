set jarName=transfer.jar
echo f | xcopy /y ..\build\libs\%jarName% .\%jarName%
REM pause