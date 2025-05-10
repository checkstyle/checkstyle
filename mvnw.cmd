@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@echo off
@setlocal

@REM Set title of command window
title %0

@REM set %HOME% to equivalent of $HOME
if "%HOME%" == "" set "HOME=%HOMEDRIVE%%HOMEPATH%"

@REM Execute user-defined pre-scripts
if not "%MAVEN_SKIP_RC%" == "" goto skipRcPre
if exist "%HOME%\mavenrc_pre.bat" call "%HOME%\mavenrc_pre.bat"
if exist "%HOME%\mavenrc_pre.cmd" call "%HOME%\mavenrc_pre.cmd"
:skipRcPre

set ERROR_CODE=0

@REM ==== START VALIDATION ====
if not "%JAVA_HOME%" == "" goto OkJHome

echo.
echo Error: JAVA_HOME not found in your environment. >&2
echo Please set the JAVA_HOME variable in your environment. >&2
goto error

:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" goto init

echo.
echo Error: JAVA_HOME is set to an invalid directory. >&2
echo JAVA_HOME = "%JAVA_HOME%" >&2
goto error

:init

@REM Detect project base directory
set MAVEN_PROJECTBASEDIR=%MAVEN_BASEDIR%
if not "%MAVEN_PROJECTBASEDIR%" == "" goto endDetectBaseDir

set EXEC_DIR=%CD%
set WDIR=%EXEC_DIR%
:findBaseDir
if exist "%WDIR%\.mvn" goto baseDirFound
cd ..
if "%WDIR%" == "%CD%" goto baseDirNotFound
set WDIR=%CD%
goto findBaseDir

:baseDirFound
set MAVEN_PROJECTBASEDIR=%WDIR%
cd "%EXEC_DIR%"
goto endDetectBaseDir

:baseDirNotFound
set MAVEN_PROJECTBASEDIR=%EXEC_DIR%
cd "%EXEC_DIR%"
:endDetectBaseDir

@REM Read jvm.config if it exists
if not exist "%MAVEN_PROJECTBASEDIR%\.mvn\jvm.config" goto endReadAdditionalConfig
setlocal EnableExtensions EnableDelayedExpansion
for /F "usebackq delims=" %%a in ("%MAVEN_PROJECTBASEDIR%\.mvn\jvm.config") do (
    set "JVM_CONFIG_MAVEN_PROPS=!JVM_CONFIG_MAVEN_PROPS! %%a"
)
@endlocal & set "JVM_CONFIG_MAVEN_PROPS=%JVM_CONFIG_MAVEN_PROPS%"
:endReadAdditionalConfig

set "MAVEN_JAVA_EXE=%JAVA_HOME%\bin\java.exe"
set "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set "WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain"

@REM Read wrapperUrl from maven-wrapper.properties
set "DOWNLOAD_URL=https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"
if exist "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties" (
    for /F "usebackq tokens=1,2 delims==" %%A in ("%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties") do (
        if "%%A" == "wrapperUrl" set "DOWNLOAD_URL=%%B"
    )
)

@REM Download maven-wrapper.jar if missing
if not exist "%WRAPPER_JAR%" (
    if not "%MVNW_REPOURL%" == "" (
        set "DOWNLOAD_URL=%MVNW_REPOURL%/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"
    )
    if "%MVNW_VERBOSE%" == "true" (
        echo Couldn't find %WRAPPER_JAR%, downloading it...
        echo Downloading from: %DOWNLOAD_URL%
    )
    powershell -NoProfile -ExecutionPolicy Bypass -Command ^
 "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $webclient = New-Object System.Net.WebClient; if (-not ([string]::IsNullOrEmpty($env:MVNW_USERNAME)) -and -not ([string]::IsNullOrEmpty($env:MVNW_PASSWORD))) { $webclient.Credentials = New-Object System.Net.NetworkCredential($env:MVNW_USERNAME, $env:MVNW_PASSWORD) }; $webclient.DownloadFile('%DOWNLOAD_URL%', '%WRAPPER_JAR%')"
    if "%MVNW_VERBOSE%" == "true" echo Finished downloading %WRAPPER_JAR%
)

@REM Set command-line args and launch Maven
set "MAVEN_CMD_LINE_ARGS=%*"
"%MAVEN_JAVA_EXE%" %JVM_CONFIG_MAVEN_PROPS% %MAVEN_OPTS% %MAVEN_DEBUG_OPTS% ^
    -classpath "%WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" ^
    %WRAPPER_LAUNCHER% %MAVEN_CONFIG% %*
if ERRORLEVEL 1 goto error

:end
@endlocal & set "ERROR_CODE=%ERROR_CODE%"
if not "%MAVEN_SKIP_RC%" == "" goto skipRcPost
if exist "%HOME%\mavenrc_post.bat" call "%HOME%\mavenrc_post.bat"
if exist "%HOME%\mavenrc_post.cmd" call "%HOME%\mavenrc_post.cmd"
:skipRcPost

if "%MAVEN_BATCH_PAUSE%" == "on" pause
if "%MAVEN_TERMINATE_CMD%" == "on" exit %ERROR_CODE%

exit /B %ERROR_CODE%
