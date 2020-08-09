@echo off
set /p EMAIL_USER="Enter EMAIL: "
@echo off
set /p EMAIL_PASS="Enter PASSWORD: "

setx EMAIL_USER %EMAIL_USER%
setx EMAIL_PASS %EMAIL_PASS%
