#!/bin/bash

set -e

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Building Native Binary with GraalVM                     ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

# Check if GraalVM is installed
if ! command -v native-image &> /dev/null; then
    echo "Installing GraalVM Native Image..."
    sudo apt-get update -qq
    sudo apt-get install -y build-essential libz-dev zlib1g-dev -qq
    
    # Install GraalVM using SDKMAN or download directly
    if ! command -v sdk &> /dev/null; then
        echo "Note: For best results, install GraalVM via SDKMAN:"
        echo "  curl -s https://get.sdkman.io | bash"
        echo "  sdk install java 21.0.1-graal"
        echo ""
        echo "Attempting to use system native-image..."
    fi
fi

echo "Building native binary..."
mvn -Pnative package -DskipTests

if [ -f "target/deep-work-tracker" ]; then
    echo ""
    echo "✓ Native binary created: target/deep-work-tracker"
    echo ""
    echo "Run with: ./run-app.sh (will auto-detect native binary)"
else
    echo "❌ Native build failed. Falling back to JAR execution."
    echo "   You can still run with: ./run-app.sh"
fi
