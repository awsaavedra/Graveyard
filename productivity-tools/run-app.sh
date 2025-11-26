#!/bin/bash

set -e

JAR_FILE="build/libs/deep-work-tracker-1.0.0.jar"
CDS_ARCHIVE="build/app.jsa"
SRC_DIR="src/main/kotlin"
BUILD_FILE="build.gradle.kts"
REQUIRED_JAVA_VERSION=21
GRADLE_WRAPPER="./gradlew"

# Detect OS
detect_os() {
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        echo "linux"
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        echo "mac"
    elif [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" || "$OSTYPE" == "win32" ]]; then
        echo "windows"
    else
        echo "unknown"
    fi
}

# Get current Java version
get_java_version() {
    if command -v java &> /dev/null; then
        java -version 2>&1 | grep -oP 'version "?\K[0-9]+' | head -1
    else
        echo "0"
    fi
}

# Install Java 21
install_java() {
    local os=$(detect_os)
    echo "╔═══════════════════════════════════════════════════════════╗"
    echo "║  Installing Java 21 LTS...                               ║"
    echo "╚═══════════════════════════════════════════════════════════╝"
    
    case $os in
        linux)
            if command -v apt-get &> /dev/null; then
                echo "📦 Installing OpenJDK 21 via apt..."
                sudo apt-get update -qq
                sudo apt-get install -y openjdk-21-jdk
            elif command -v yum &> /dev/null; then
                echo "📦 Installing OpenJDK 21 via yum..."
                sudo yum install -y java-21-openjdk java-21-openjdk-devel
            elif command -v dnf &> /dev/null; then
                echo "📦 Installing OpenJDK 21 via dnf..."
                sudo dnf install -y java-21-openjdk java-21-openjdk-devel
            else
                echo "❌ Unsupported Linux distribution. Please install Java 21 manually:"
                echo "   https://adoptium.net/temurin/releases/"
                exit 1
            fi
            ;;
        mac)
            if command -v brew &> /dev/null; then
                echo "📦 Installing OpenJDK 21 via Homebrew..."
                brew install openjdk@21
                # Link it
                sudo ln -sfn /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk 2>/dev/null || true
            else
                echo "❌ Homebrew not found. Please install Java 21 manually:"
                echo "   https://adoptium.net/temurin/releases/"
                exit 1
            fi
            ;;
        windows)
            echo "📦 For Windows, please install Java 21 manually:"
            echo "   1. Download from: https://adoptium.net/temurin/releases/"
            echo "   2. Install the .msi package"
            echo "   3. Restart your terminal/Git Bash"
            exit 1
            ;;
        *)
            echo "❌ Unknown OS. Please install Java 21 manually:"
            echo "   https://adoptium.net/temurin/releases/"
            exit 1
            ;;
    esac
    
    echo "✓ Java 21 installed successfully!"
}

# Install Maven
install_build_tool() {
    local os=$(detect_os)
    
    # Install Gradle wrapper if not present
    if [ ! -f "$GRADLE_WRAPPER" ]; then
        echo "╔═══════════════════════════════════════════════════════════╗"
        echo "║  Installing Gradle wrapper...                            ║"
        echo "╚═══════════════════════════════════════════════════════════╝"
        
        # Create minimal wrapper using gradle init if gradle is available
        if command -v gradle &> /dev/null; then
            gradle wrapper --gradle-version 8.5
        else
            # Download gradle wrapper manually
            mkdir -p gradle/wrapper
            curl -s -L -o gradle/wrapper/gradle-wrapper.jar https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar
            curl -s -L -o gradle/wrapper/gradle-wrapper.properties https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.properties
            curl -s -L -o gradlew https://raw.githubusercontent.com/gradle/gradle/master/gradlew
            chmod +x gradlew
        fi
        echo "✓ Gradle wrapper installed"
    fi
}

# Environment validation and auto-setup
echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Deep Work Tracker - Environment Check                   ║"
echo "╚═══════════════════════════════════════════════════════════╝"

# Check and install Java if needed
CURRENT_JAVA_VERSION=$(get_java_version)
if [ "$CURRENT_JAVA_VERSION" -eq 0 ]; then
    echo "⚠️  Java not found"
    install_java
    CURRENT_JAVA_VERSION=$(get_java_version)
elif [ "$CURRENT_JAVA_VERSION" -lt "$REQUIRED_JAVA_VERSION" ]; then
    echo "⚠️  Java $CURRENT_JAVA_VERSION found, but Java $REQUIRED_JAVA_VERSION required"
    install_java
    CURRENT_JAVA_VERSION=$(get_java_version)
else
    echo "✓ Java $CURRENT_JAVA_VERSION detected"
fi

# Verify Java installation
if [ "$CURRENT_JAVA_VERSION" -lt "$REQUIRED_JAVA_VERSION" ]; then
    echo "❌ Java $REQUIRED_JAVA_VERSION installation failed"
    echo "   Please install manually: https://adoptium.net/temurin/releases/"
    exit 1
fi

# Setup Gradle wrapper
if [ ! -f "$GRADLE_WRAPPER" ]; then
    echo "⚠️  Gradle wrapper not found"
    install_build_tool
else
    echo "✓ Gradle wrapper detected"
fi

echo ""

# Check if rebuild is needed
NEEDS_BUILD=false

if [ ! -f "$JAR_FILE" ]; then
    NEEDS_BUILD=true
elif [ "$BUILD_FILE" -nt "$JAR_FILE" ]; then
    NEEDS_BUILD=true
else
    # Check if any source file is newer than the JAR
    if [ -d "$SRC_DIR" ]; then
        while IFS= read -r -d '' file; do
            if [ "$file" -nt "$JAR_FILE" ]; then
                NEEDS_BUILD=true
                break
            fi
        done < <(find "$SRC_DIR" -type f -name "*.kt" -print0)
    fi
fi

if [ "$NEEDS_BUILD" = true ]; then
    $GRADLE_WRAPPER jar --build-cache --parallel --daemon
    # Regenerate CDS archive after rebuild
    [ -f "$CDS_ARCHIVE" ] && rm -f "$CDS_ARCHIVE"
fi

# Create or update CDS archive for faster startup
if [ ! -f "$CDS_ARCHIVE" ] || [ "$JAR_FILE" -nt "$CDS_ARCHIVE" ]; then
    timeout 5s java -XX:ArchiveClassesAtExit="$CDS_ARCHIVE" -jar "$JAR_FILE" <<< "q" &>/dev/null || true
fi

# Run with CDS and optimized JVM flags for faster startup
exec java -XX:SharedArchiveFile="$CDS_ARCHIVE" \
    -XX:TieredStopAtLevel=1 \
    -Xshare:on \
    -jar "$JAR_FILE"

