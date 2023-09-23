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

if "%OPTION%" == "git_diff" (
  for /f "delims=" %%a in ('git status ^| findstr /c:"Changes not staged"') do set output=%%a
  if defined output (
    echo Please clean up or update .gitattributes file.
    echo Git status output:
    echo Top 300 lines of diff:
    call git diff | find /v /n "" | findstr /R "^\[[1-2]*[1-9]*[0-9]\]"
    echo There should be no changes in git repository after any CI job/task
    goto :ERROR
  )
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
