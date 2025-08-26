@echo off
set JAR=%~dp0\credit-simulator-1.0.0-shaded.jar
if not exist "%JAR%" (
  echo JAR not found. Please run: mvn package
  exit /b 1
)
if "%~1"=="" (
  java -jar "%JAR%"
) else (
  java -jar "%JAR%" "%~1"
)