@echo off

::----------------------------------------------------------------------
:: Validation script to run on local for windows users.
:: Example of usage
:: ./.ci/validation.cmd  verify_without_checkstyle
::----------------------------------------------------------------------

SET OPTION=%1

if "%OPTION%" == "sevntu" (
  call mvn -e --no-transfer-progress verify -Pno-validations,sevntu^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" == "run_checkstyle" (
  call mvn -e --no-transfer-progress clean compile antrun:run@ant-phase-verify^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" ==  "verify_without_checkstyle" (
  call mvn -e --no-transfer-progress verify -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" ==  "site_without_verify" (
  call mvn -e --no-transfer-progress -Pno-validations site^
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
echo validation failed with error code #%ERRORLEVEL%.
exit /b %ERRORLEVEL%
