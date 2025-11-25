#!/bin/bash

set -e

JAR_FILE="target/deep-work-tracker-1.0.0-jar-with-dependencies.jar"
CDS_ARCHIVE="target/app.jsa"
SRC_DIR="src/main/kotlin"
POM_FILE="pom.xml"

# Quick validation
if ! command -v java &> /dev/null; then
    echo "❌ Java not installed"
    exit 1
fi

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

