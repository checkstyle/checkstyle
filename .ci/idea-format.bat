@echo off

::----------------------------------------------------------------------
:: IntelliJ IDEA code formatting for checkstyle.
::
:: Example:
:: SET IDEA_PATH=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2017.2.1\bin
:: .ci\idea-format.bat
::----------------------------------------------------------------------

SET PROJECT_DIR=%CD%\
SET FORMAT_PATH=%CD%\config\intellij-idea-code-style.xml
SET RESULTS_DIR=%CD%\target\format-results
SET NOISE_LVL=v1
SET IDEA_LOCATION=

::Check IDEA_PATH env variable
IF DEFINED IDEA_PATH (
    IF EXIST "%IDEA_PATH%" (
        SET "IDEA_LOCATION=%IDEA_PATH%"
        echo Using IDEA from environment variable: %IDEA_LOCATION%
        goto run
    )
)
echo IDEA_PATH variable not found or invalid.

::Try to search in path
FOR /f "delims=" %%i IN ('where idea.bat 2^>nul') DO (
    SET "IDEA_LOCATION=%%~dpi"
    echo Found IDEA in PATH, using directory: %IDEA_LOCATION%
    goto run
)
echo IntelliJ IDEA was not found in path.
exit /b 1

:run
IF NOT EXIST "%RESULTS_DIR%" mkdir "%RESULTS_DIR%"
IF EXIST "%RESULTS_DIR%" del /s /q "%RESULTS_DIR%\*.*"

::Execute compilation of Checkstyle to generate all source files
call mvn -e compile

::Launch formatting
echo Running IDEA code formatting with settings from: %FORMAT_PATH%
call "%IDEA_LOCATION%format.bat" -settings "%FORMAT_PATH%" "%PROJECT_DIR%"

echo Formatting completed.
