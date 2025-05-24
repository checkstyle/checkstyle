@echo off

set "file=config\suppressions-xpath.xml"
set "tempfile=config\suppressions-xpath.tmp"

REM Check if file exists
if not exist "%file%" (
    echo File %file% not found!
    exit /b 1
)

REM Wait a moment and ensure temp file is clean
timeout /t 1 /nobreak >nul 2>&1
if exist "%tempfile%" del "%tempfile%" 2>nul

REM Remove the closing tag and write to temp file
findstr /v "</suppressions>" "%file%" > "%tempfile%" 2>nul
if errorlevel 1 (
    echo Error reading source file
    exit /b 1
)

REM Append new suppression block and closing tag
(
echo   ^<suppress-xpath
echo     files=".*"
echo     checks="WhitespaceAfterCheck"
echo     query="//ARRAY_INIT/COMMA[following-sibling::*[1][self::RCURLY]]"/^>
echo ^</suppressions^>
) >> "%tempfile%" 2>nul

REM Wait briefly before file operations
timeout /t 1 /nobreak >nul 2>&1

REM Replace original file with retry logic
:retry
move /Y "%tempfile%" "%file%" >nul 2>&1
if errorlevel 1 (
    timeout /t 2 /nobreak >nul 2>&1
    goto retry
)

echo Suppression added to %file%
