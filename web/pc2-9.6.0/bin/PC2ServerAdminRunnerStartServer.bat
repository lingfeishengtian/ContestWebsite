@echo off

cd /D "%~dp0"
start /b cmd /c call pc2server.bat --login %1 --password %2 --contestpassword %3
start /b cmd /c call pc2admin.bat --login %4 --password %5
start /b cmd /c call pc2judge.bat --login %6 --password %7
start /b cmd /c call pc2board.bat --login %8 --password %9
start /b cmd /c call pc2team.bat --login %{10} --password %{11}