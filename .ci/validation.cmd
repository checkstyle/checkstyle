@echo off

::----------------------------------------------------------------------
:: Validation script to run on local for Windows users.
:: Example of usage:
:: ./.ci/validation.cmd  verify_without_checkstyle
::----------------------------------------------------------------------

SET OPTION=%1

if "%OPTION%" == "sevntu" (
  call mvnw.cmd -e --no-transfer-progress verify -Pno-validations,sevntu^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" == "run_checkstyle" (
  call mvnw.cmd -e --no-transfer-progress clean compile antrun:run@ant-phase-verify^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" ==  "verify_without_checkstyle" (
  call mvnw.cmd -e --no-transfer-progress verify -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" ==  "site_without_verify" (
  call mvnw.cmd -e --no-transfer-progress -Pno-validations site^
    || goto :ERROR
  goto :END_CASE
)

if "%OPTION%" == "no-error-trino" (
  echo Detecting Checkstyle version from pom.xml...
  for /f "tokens=2 delims=<>" %%a in ('findstr "<version>" pom.xml') do (
    set CS_POM_VERSION=%%a
    goto :found_version
  )

  :found_version
  echo CS_version: %CS_POM_VERSION%

  call mvnw.cmd -e --no-transfer-progress clean install -Pno-validations || goto :ERROR

  echo Checkout Trino sources...
  rmdir /s /q .ci-temp\trino 2>nul
  mkdir .ci-temp 2>nul
  cd .ci-temp

  git clone --depth 1 https://github.com/trinodb/trino.git || goto :ERROR
  cd trino

  echo Checking Java version...
  java -version 2>&1 | findstr "version \"17" >nul
  if errorlevel 1 (
    echo Java 17+ is required for Trino checkstyle.
    echo Please set JAVA_HOME to a Java 17+ installation.
    goto :ERROR
  )

  call mvn -e --no-transfer-progress checkstyle:check ^
    -Dcheckstyle.version=%CS_POM_VERSION% ^
    -Dcheckstyle.plugin.version=%CS_POM_VERSION% || goto :ERROR

  cd ..\..
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
