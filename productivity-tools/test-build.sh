#!/bin/bash

set -e

# Performance targets (in seconds)
TARGET_CACHED_RUN=0.5
TARGET_WARM_REBUILD=5.0
TARGET_COLD_BUILD=20.0

# Track failures
FAILURES=0

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Deep Work Tracker - Build Performance Tests             ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

JAR_FILE="build/libs/deep-work-tracker-1.0.0.jar"

# Helper function to convert time string to seconds
time_to_seconds() {
    local time_str=$1
    # Extract minutes and seconds from format 0m0.208s
    echo "$time_str" | awk -F'[ms]' '{print ($1 * 60) + $2}'
}

# Helper function to run test and check against target
run_test() {
    local test_name=$1
    local target=$2
    local cmd=$3
    
    echo -n "$test_name: "
    local time_output=$( ( time eval "$cmd" >/dev/null 2>&1 ) 2>&1 | grep real | awk '{print $2}' )
    local actual=$(time_to_seconds "$time_output")
    
    echo -n "$time_output "
    
    # Check if time exceeds target
    if awk -v a="$actual" -v t="$target" 'BEGIN {exit !(a > t)}'; then
        echo "❌ SLOW (target: ${target}s)"
        FAILURES=$((FAILURES + 1))
        return 1
    else
        echo "✅"
        return 0
    fi
}

# Test 1: Cached runs
echo "Test 1: Cached runs (no rebuild)"
echo "----------------------------------------"
for i in {1..3}; do
    run_test "  Run $i" "$TARGET_CACHED_RUN" \
        "echo 'q' | java -XX:SharedArchiveFile=build/app.jsa -XX:TieredStopAtLevel=1 -Xshare:on -jar '$JAR_FILE'"
done
echo ""

# Test 2: Warm rebuild
echo "Test 2: Warm rebuild (daemon running)"
echo "----------------------------------------"
rm -rf build
run_test "  Build" "$TARGET_WARM_REBUILD" \
    "./gradlew jar --parallel --daemon --quiet"
echo ""

# Test 3: Cold build
echo "Test 3: Cold build (daemon restart)"
echo "----------------------------------------"
./gradlew --stop >/dev/null 2>&1 || true
rm -rf build
run_test "  Build" "$TARGET_COLD_BUILD" \
    "./gradlew jar --parallel --daemon --quiet"
echo ""

# Summary
echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Test Results                                             ║"
echo "╚═══════════════════════════════════════════════════════════╝"

if [ $FAILURES -eq 0 ]; then
    echo "✅ All performance tests passed!"
    exit 0
else
    echo "❌ $FAILURES test(s) failed - build performance is degraded"
    echo ""
    echo "Performance targets:"
    echo "  - Cached runs: < ${TARGET_CACHED_RUN}s"
    echo "  - Warm rebuild: < ${TARGET_WARM_REBUILD}s"
    echo "  - Cold build: < ${TARGET_COLD_BUILD}s"
    exit 1
fi
