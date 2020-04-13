@echo off

::----------------------------------------------------------------------
:: Appveyor bat file to run on local for windows users.
:: Example of usage
:: ./.ci/appveyor.bat  verify_without_checkstyle
::----------------------------------------------------------------------

SET OPTION=%1

if "%OPTION%" == "sevntu" (
  call mvn -e verify -DskipTests -DskipITs -Dpmd.skip=true^
    -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" ==  "verify_without_checkstyle" (
  call mvn -e verify -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" ==  "verify_without_checkstyle_JDK11" (
  call mvn -e verify -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" ==  "verify_without_checkstyle_JDK14" (
  call mvn -e verify -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" ==  "site_without_verify" (
  call mvn -e -Pno-validations site^
    || goto :ERROR
  goto :END_CASE
)

echo  Unexpected argument %OPTION%
SET ERRORLEVEL=-1
goto :END_CASE

:END_CASE
  VER > NUL
  EXIT /b %ERRORLEVEL%

:ERROR
echo appveyor.bat failed with error code #%ERRORLEVEL%.
exit /b %ERRORLEVEL%
