#!/bin/bash

# Deep Work Productivity Tracker - Setup and Run Script

set -e

echo "═══════════════════════════════════════════════════════════"
echo "  Deep Work Productivity Tracker - Setup"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java 17+ is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=version ")[^"]*' | head -1)
echo "✓ Java version: $JAVA_VERSION"
echo ""

# Check if Gradle is installed
if ! command -v gradle &> /dev/null; then
    echo "⚠ Gradle not found in PATH. Attempting to use gradlew..."
    if [ ! -f "gradlew" ]; then
        echo "❌ Neither gradle nor gradlew found. Please install Gradle or run from project root."
        exit 1
    fi
    GRADLE_CMD="./gradlew"
else
    GRADLE_CMD="gradle"
fi

echo "Building project with $GRADLE_CMD..."
$GRADLE_CMD clean build

echo ""
echo "✓ Build complete!"
echo ""
echo "═══════════════════════════════════════════════════════════"
echo "  Running Deep Work Productivity Tracker"
echo "═══════════════════════════════════════════════════════════"
echo ""

$GRADLE_CMD run
