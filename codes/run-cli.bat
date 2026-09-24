@echo off
title Placement Hub - DSA Verification Suite (CO1 to CO6)
echo Running Algorithm Verification Suite...
cd /d "%~dp0"
mvn exec:java -Dexec.args="--cli"
pause
