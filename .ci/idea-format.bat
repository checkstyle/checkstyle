@echo off

::----------------------------------------------------------------------
:: IntelliJ IDEA inspections for checkstyle.
::
:: Example:
:: SET IDEA_PATH=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2017.2.1\bin\idea.bat
:: .ci\idea-inspection.bat
::----------------------------------------------------------------------

SET PROJECT_DIR=%CD%
SET FORMAT_PATH=%CD%\config\intellij-idea-code-style.xml
SET RESULTS_DIR=%CD%\target\format-results
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
    SET "IDEA_LOCATION=%%i"
    echo Found IDEA in PATH: %%i
    goto found_in_path
)
echo IntelliJ IDEA was not found in path.
exit /b 1

:found_in_path
echo Found IDEA in PATH: %IDEA_LOCATION%

:run
IF NOT EXIST "%RESULTS_DIR%" mkdir "%RESULTS_DIR%"
IF EXIST "%RESULTS_DIR%" del /s /q "%RESULTS_DIR%\."

::Execute compilation of Checkstyle to generate all source files
echo Running Maven compile...
call mvn -e compile

::Launch inspections
echo Running IDEA format from: %IDEA_LOCATION%
echo "%IDEA_LOCATION%" format -r -s "%FORMAT_PATH%" -m "*.java" "%PROJECT_DIR%"
call "%IDEA_LOCATION%" format -r -s "%FORMAT_PATH%" -m "*.java" "%PROJECT_DIR%\src\it\java"
call "%IDEA_LOCATION%" format -r -s "%FORMAT_PATH%" -m "*.java" "%PROJECT_DIR%\src\main\java"
call "%IDEA_LOCATION%" format -r -s "%FORMAT_PATH%" -m "*.java" "%PROJECT_DIR%\src\test\java"
call "%IDEA_LOCATION%" format -r -s "%FORMAT_PATH%" -m "*.java" ^
    "%PROJECT_DIR%\src\xdocs-examples\java"

echo Formatting completed.
