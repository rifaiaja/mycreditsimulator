@echo off
set JAR=%~dp0\..\target\credit-simulator-1.0.0-shaded.jar
if not exist "%JAR%" (
  echo JAR not found. Please run: mvn clean package
  exit /b 1
)
if "%~1"=="" (
  java -jar "%JAR%"
) else (
  java -jar "%JAR%" "%~1"
)