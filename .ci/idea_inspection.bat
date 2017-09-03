@echo off

::----------------------------------------------------------------------
:: IntelliJ IDEA inspections for checkstyle.
::
:: Example:
:: SET IDEA_PATH=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2017.2.1\bin\idea.bat
:: .ci\idea_inspection.bat
::----------------------------------------------------------------------

SET PROJECT_DIR=%CD%\
SET INSPECTIONS_PATH=%CD%\config\intellij-idea-inspections.xml
SET RESULTS_DIR=%CD%\target\inspection-results
SET NOISE_LVL=v1
SET IDEA_LOCATION=
SET IDEA_PROPERTIES=%CD%\config\idea.properties

::Check IDEA_PATH env variable
IF EXIST %IDEA_PATH% SET (
    SET IDEA_LOCATION=%IDEA_PATH%
    goto run
) ELSE (
    echo IDEA_PATH variable not found.
)

::Try to search in path
FOR /f "delims=" %%i IN ('"where idea.bat"') DO SET IDEA_LOCATION="%%i"
if [%IDEA_LOCATION%] NEQ [] (
    goto run 
) ELSE (
    echo IntelliJ IDEA was not found in path.
    exit /b
)

:run
mkdir .idea\scopes
copy config\intellij-idea-inspection-scope.xml .idea\scopes

"%IDEA_LOCATION%" inspect %PROJECT_DIR% %INSPECTIONS_PATH% %RESULTS_DIR% -%NOISE_LVL%
