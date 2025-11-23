#!/bin/bash

set -e

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Deep Work Tracker - Build & Run                         ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java not installed. Please install Java 8+:"
    echo "   Ubuntu/Debian: sudo apt-get install openjdk-8-jdk"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=version ")[^"]*' | head -1)
echo "✓ Java $JAVA_VERSION"

# Check Maven
if ! command -v mvn &> /dev/null; then
    echo "Installing Maven..."
    sudo apt-get update -qq && sudo apt-get install -y maven -qq 2>/dev/null || true
fi

if ! command -v mvn &> /dev/null; then
    echo "❌ Maven not installed. Please install:"
    echo "   Ubuntu/Debian: sudo apt-get install maven"
    exit 1
fi

echo "Building project..."
mvn clean package -q -DskipTests

echo "✓ Build successful!"
echo ""
echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Running Deep Work Tracker                               ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

java -jar target/deep-work-tracker-1.0.0-jar-with-dependencies.jar

