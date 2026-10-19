@echo off
setlocal
set "MAVEN_CACHED=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.10.0\2aaba761688b1a6ea5a46e7e5f4d5406ed7a75ee8da95e5e3079863a23461c9b\bin\mvn.cmd"
if exist "%MAVEN_CACHED%" (
  call "%MAVEN_CACHED%" -Duser.home="%USERPROFILE%" %*
  exit /b %ERRORLEVEL%
)
where mvn >nul 2>nul
if %ERRORLEVEL% EQU 0 (
  call mvn %*
  exit /b %ERRORLEVEL%
)
echo Maven 3.10.0 is not installed in the local wrapper cache and no mvn command was found.
echo Install Maven 3.10.0 or use the wrapper distribution URL in .mvn\wrapper\maven-wrapper.properties.
exit /b 1
