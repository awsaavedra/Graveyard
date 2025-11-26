#!/bin/bash

set -e

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Deep Work Tracker - Build Performance Tests             ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

JAR_FILE="build/libs/deep-work-tracker-1.0.0.jar"

# Test 1: Cached run (no build needed)
echo "Test 1: Cached run (JAR already built)"
echo "----------------------------------------"
for i in {1..3}; do
    echo -n "Run $i: "
    ( time ( echo "q" | java -XX:SharedArchiveFile=build/app.jsa -XX:TieredStopAtLevel=1 -Xshare:on -jar "$JAR_FILE" >/dev/null 2>&1 ) ) 2>&1 | grep real | awk '{print $2}'
done
echo ""

# Test 2: Rebuild with warm daemon and cached dependencies  
echo "Test 2: Rebuild (warm daemon, cached deps)"
echo "-------------------------------------------"
rm -rf build
echo -n "Build time: "
( time ./gradlew jar --parallel --daemon --quiet ) 2>&1 | grep real | awk '{print $2}'
echo -n "Run time: "
( time ( echo "q" | java -jar "$JAR_FILE" >/dev/null 2>&1 ) ) 2>&1 | grep real | awk '{print $2}'
echo ""

# Test 3: Clean build (cold daemon, cached deps)
echo "Test 3: Clean build (cold daemon, cached deps)"
echo "-----------------------------------------------"
./gradlew --stop >/dev/null 2>&1 || true
rm -rf build
echo -n "Build time: "
( time ./gradlew jar --parallel --daemon --quiet ) 2>&1 | grep real | awk '{print $2}'
echo ""

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Performance Summary                                      ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo "✓ Cached runs should be < 0.5 seconds"
echo "✓ Warm rebuild should be < 5 seconds"  
echo "✓ Cold rebuild should be < 20 seconds"
