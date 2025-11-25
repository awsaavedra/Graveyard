#!/bin/bash

set -e

JAR_FILE="target/deep-work-tracker-1.0.0-jar-with-dependencies.jar"
CDS_ARCHIVE="target/app.jsa"
SRC_DIR="src/main/kotlin"
POM_FILE="pom.xml"
REQUIRED_JAVA_VERSION=21

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
install_maven() {
    local os=$(detect_os)
    echo "╔═══════════════════════════════════════════════════════════╗"
    echo "║  Installing Apache Maven...                              ║"
    echo "╚═══════════════════════════════════════════════════════════╝"
    
    case $os in
        linux)
            if command -v apt-get &> /dev/null; then
                echo "📦 Installing Maven via apt..."
                sudo apt-get install -y maven
            elif command -v yum &> /dev/null; then
                echo "📦 Installing Maven via yum..."
                sudo yum install -y maven
            elif command -v dnf &> /dev/null; then
                echo "📦 Installing Maven via dnf..."
                sudo dnf install -y maven
            else
                echo "❌ Unsupported package manager"
                exit 1
            fi
            ;;
        mac)
            if command -v brew &> /dev/null; then
                echo "📦 Installing Maven via Homebrew..."
                brew install maven
            else
                echo "❌ Homebrew not found"
                exit 1
            fi
            ;;
        windows)
            echo "📦 For Windows, Maven will be installed automatically on first build"
            echo "   Or install manually: https://maven.apache.org/download.cgi"
            ;;
    esac
    
    echo "✓ Maven installed successfully!"
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

# Check and install Maven if needed
if ! command -v mvn &> /dev/null; then
    echo "⚠️  Maven not found"
    install_maven
else
    echo "✓ Maven detected"
fi

# Verify Maven installation
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven installation failed"
    echo "   Please install manually: https://maven.apache.org/download.cgi"
    exit 1
fi

echo ""

# Check if rebuild is needed
NEEDS_BUILD=false

if [ ! -f "$JAR_FILE" ]; then
    NEEDS_BUILD=true
elif [ "$POM_FILE" -nt "$JAR_FILE" ]; then
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
    mvn package -q -DskipTests -Dmaven.compiler.useIncrementalCompilation=true
    # Regenerate CDS archive after rebuild
    [ -f "$CDS_ARCHIVE" ] && rm -f "$CDS_ARCHIVE"
fi

# Create or update CDS archive for faster startup
if [ ! -f "$CDS_ARCHIVE" ] || [ "$JAR_FILE" -nt "$CDS_ARCHIVE" ]; then
    java -XX:ArchiveClassesAtExit="$CDS_ARCHIVE" -jar "$JAR_FILE" <<< "q" &>/dev/null || true
fi

# Run with CDS for 2-3x faster startup
exec java -XX:SharedArchiveFile="$CDS_ARCHIVE" -jar "$JAR_FILE"

