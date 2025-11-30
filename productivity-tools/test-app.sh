#!/bin/bash

set -e

# Performance targets (in seconds)
TARGET_CACHED_RUN=0.5
TARGET_WARM_REBUILD=5.0
TARGET_COLD_BUILD=20.0

# Track failures
FAILURES=0

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Deep Work Tracker - Complete Test Suite                 ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

JAR_FILE="build/libs/deep-work-tracker-1.0.0.jar"

# ============================================================================
# SECTION 1: BUILD PERFORMANCE TESTS
# ============================================================================

echo "═══════════════════════════════════════════════════════════"
echo "  BUILD PERFORMANCE TESTS"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Helper function to convert time string to seconds
time_to_seconds() {
    local time_str=$1
    # Extract minutes and seconds from format 0m0.208s
    echo "$time_str" | awk -F'[ms]' '{print ($1 * 60) + $2}'
}

# Helper function to run test and check against target
run_perf_test() {
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
    run_perf_test "  Run $i" "$TARGET_CACHED_RUN" \
        "echo 'q' | java -XX:SharedArchiveFile=build/app.jsa -XX:TieredStopAtLevel=1 -Xshare:on -jar '$JAR_FILE'"
done
echo ""

# Test 2: Warm rebuild
echo "Test 2: Warm rebuild (daemon running)"
echo "----------------------------------------"
rm -rf build
run_perf_test "  Build" "$TARGET_WARM_REBUILD" \
    "./gradlew jar --parallel --daemon --quiet"
echo ""

# Test 3: Cold build
echo "Test 3: Cold build (daemon restart)"
echo "----------------------------------------"
./gradlew --stop >/dev/null 2>&1 || true
rm -rf build
run_perf_test "  Build" "$TARGET_COLD_BUILD" \
    "./gradlew jar --parallel --daemon --quiet"
echo ""

# ============================================================================
# SECTION 2: DAY INPUT VALIDATION TESTS
# ============================================================================

echo "═══════════════════════════════════════════════════════════"
echo "  DAY INPUT VALIDATION TESTS"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Helper to run functional test
run_func_test() {
    local test_name=$1
    local input=$2
    local expected_pattern=$3
    
    echo -n "$test_name: "
    local output=$(echo -e "$input" | java -jar "$JAR_FILE" 2>&1)
    
    if echo "$output" | grep -q "$expected_pattern"; then
        echo "✅"
        return 0
    else
        echo "❌ FAILED"
        echo "  Expected pattern: $expected_pattern"
        FAILURES=$((FAILURES + 1))
        return 1
    fi
}

# Test 1: Valid day within range (day 15)
echo "Test 1: Valid day input"
echo "----------------------------------------"
run_func_test "  Day 15 (valid)" "15\n2\ns\nq" "LOG HOURS FOR: 2025-11-15"
echo ""

# Test 2: Out of bounds - day 0
echo "Test 2: Out of bounds - day 0"
echo "----------------------------------------"
run_func_test "  Day 0 (invalid)" "0\nq" "Invalid day. Please enter a day between 1 and 30"
echo ""

# Test 3: Out of bounds - day 31 (November has 30 days)
echo "Test 3: Out of bounds - day 31"
echo "----------------------------------------"
run_func_test "  Day 31 (invalid)" "31\nq" "Invalid day. Please enter a day between 1 and 30"
echo ""

# Test 4: Out of bounds - day 32
echo "Test 4: Out of bounds - day 32"
echo "----------------------------------------"
run_func_test "  Day 32 (invalid)" "32\nq" "Invalid day. Please enter a day between 1 and 30"
echo ""

# Test 5: Boundary - day 1
echo "Test 5: Boundary - day 1"
echo "----------------------------------------"
run_func_test "  Day 1 (valid)" "1\n4\ns\nq" "LOG HOURS FOR: 2025-11-01"
echo ""

# Test 6: Boundary - day 30 (last day of November)
echo "Test 6: Boundary - day 30"
echo "----------------------------------------"
run_func_test "  Day 30 (valid)" "30\n1\ns\nq" "LOG HOURS FOR: 2025-11-30"
echo ""

# Test 7: Hours input validation (0-4)
echo "Test 7: Hours input validation"
echo "----------------------------------------"
run_func_test "  Set 0 hours" "t\n0\ns\nq" "Current: 0/4 hours"
run_func_test "  Set 4 hours" "t\n4\ns\nq" "Current: 4/4 hours"
echo ""

# ============================================================================
# SUMMARY
# ============================================================================

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Test Results Summary                                     ║"
echo "╚═══════════════════════════════════════════════════════════╝"

if [ $FAILURES -eq 0 ]; then
    echo "✅ All tests passed!"
    echo ""
    echo "Performance metrics:"
    echo "  - Cached runs: < ${TARGET_CACHED_RUN}s"
    echo "  - Warm rebuild: < ${TARGET_WARM_REBUILD}s"
    echo "  - Cold build: < ${TARGET_COLD_BUILD}s"
    exit 0
else
    echo "❌ $FAILURES test(s) failed"
    echo ""
    echo "Performance targets:"
    echo "  - Cached runs: < ${TARGET_CACHED_RUN}s"
    echo "  - Warm rebuild: < ${TARGET_WARM_REBUILD}s"
    echo "  - Cold build: < ${TARGET_COLD_BUILD}s"
    exit 1
fi
