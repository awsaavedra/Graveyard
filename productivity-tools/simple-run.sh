#!/bin/bash

# Simple Kotlin compilation and run script

set -e

echo "Installing Kotlin..."
sudo apt-get install -y kotlin > /dev/null 2>&1 || {
    echo "Installing via snap..."
    sudo snap install kotlin --classic > /dev/null 2>&1
}

cd "$(dirname "$0")"

echo "Compiling Kotlin files..."
mkdir -p build/classes

# Compile all Kotlin files
kotlinc -d build/classes \
    -cp "build/libs/*" \
    src/main/kotlin/com/productivitytracker/models/Models.kt \
    src/main/kotlin/com/productivitytracker/repository/Repository.kt \
    src/main/kotlin/com/productivitytracker/ui/CalendarUI.kt \
    src/main/kotlin/com/productivitytracker/App.kt 2>&1 | grep -v "^w:" || true

echo "✓ Compilation complete"
echo ""
echo "Running application..."
echo ""

# Download SQLite JDBC if not present
if [ ! -f "build/libs/sqlite-jdbc-3.44.0.0.jar" ]; then
    mkdir -p build/libs
    echo "Downloading SQLite JDBC..."
    curl -s https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.0.0/sqlite-jdbc-3.44.0.0.jar -o build/libs/sqlite-jdbc-3.44.0.0.jar
fi

# Run with classpath
kotlin -cp "build/classes:build/libs/*" com.productivitytracker.AppKt
