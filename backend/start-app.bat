@echo off
cd /d "C:\Users\Administrator\Documents\code\web\202507\anhuiProjectManager\backend"

echo Setting JAVA_HOME to Java 21...
set JAVA_HOME=C:\Users\Administrator\.cursor\extensions\redhat.java-1.43.1-win32-x64\jre\21.0.7-win32-x86_64
set PATH=%JAVA_HOME%\bin;%PATH%

echo Current Java version:
java -version

echo Compiling project...
mvn clean compile -DskipTests -pl ruoyi-admin -am

if %ERRORLEVEL% EQU 0 (
    echo Starting Spring Boot application...
    mvn spring-boot:run -pl ruoyi-admin
) else (
    echo Compilation failed. Please check the errors above.
    pause
)