@echo off

::----------------------------------------------------------------------
:: Appveyor bat file to run on local for windows users.
:: Example of usage
:: ./.ci/appveyor.bat  verify_without_checkstyle
::----------------------------------------------------------------------

SET OPTION=%1

if "%OPTION%" == "sevntu" (
mvn -e verify -DskipTests -DskipITs -Dpmd.skip=true^
 -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true
    goto :END_CASE
)

if "%OPTION%" ==  "verify_without_checkstyle" (
mvn -e verify -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true
  goto :END_CASE
)

if "%OPTION%" ==  "verify_without_checkstyle_JDK11" (
mvn -e verify -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true
  goto :END_CASE
)

if "%OPTION%" ==  "verify_without_checkstyle_JDK13" (
  mvn -e verify -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true
  goto :END_CASE
)

if "%OPTION%" ==  "site_without_verify" (
mvn -e -Pno-validations site
  goto :END_CASE
)

echo  Unexpected argument %OPTION%
goto :END_CASE

:END_CASE
  VER > NUL
  EXIT
