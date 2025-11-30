@echo off
echo Compiling...
javac *.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    exit /b 1
)
echo.
echo Running tests...
java GameTest
echo.
echo To run the interactive game:
echo java Main

