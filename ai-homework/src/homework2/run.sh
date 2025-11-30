#!/bin/bash
echo "Compiling..."
javac *.java
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi
echo ""
echo "Running tests..."
java GameTest
echo ""
echo "To run the interactive game:"
echo "java Main"

