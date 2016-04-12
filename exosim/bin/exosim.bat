@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  exosim startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and EXOSIM_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-splash:src/main/resources/exosim-splash.png"

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\exosim.jar;%APP_HOME%\lib\commons-cli-1.3.jar;%APP_HOME%\lib\jdom-2.0.0.jar;%APP_HOME%\lib\joml-1.7.0.jar;%APP_HOME%\lib\forms_rt-7.0.3.jar;%APP_HOME%\lib\jfreechart-1.0.19.jar;%APP_HOME%\lib\asm-commons-3.0.jar;%APP_HOME%\lib\forms-1.1-preview.jar;%APP_HOME%\lib\jdom-1.0.jar;%APP_HOME%\lib\jcommon-1.0.23.jar;%APP_HOME%\lib\asm-tree-3.0.jar;%APP_HOME%\lib\asm-3.0.jar

@rem Execute exosim
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %EXOSIM_OPTS%  -classpath "%CLASSPATH%" ecumene.exo.Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable EXOSIM_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%EXOSIM_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
