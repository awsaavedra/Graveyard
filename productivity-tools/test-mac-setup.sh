#!/bin/bash

# Test script for Mac M2 first-run installation validation
# This simulates various Mac setup scenarios

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║  Mac M2 Setup Validation Tests                           ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

TESTS_PASSED=0
TESTS_FAILED=0

# Test result tracking
pass_test() {
    echo -e "${GREEN}✓ PASS${NC}: $1"
    ((TESTS_PASSED++))
}

fail_test() {
    echo -e "${RED}✗ FAIL${NC}: $1"
    ((TESTS_FAILED++))
}

warn_test() {
    echo -e "${YELLOW}⚠ SKIP${NC}: $1"
}

# Test 1: Architecture Detection
test_architecture_detection() {
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Test 1: Architecture Detection"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    local arch=$(uname -m)
    echo "Detected architecture: $arch"
    
    if [[ "$arch" == "arm64" ]]; then
        local expected_prefix="/opt/homebrew"
        pass_test "ARM64 architecture detected (Mac M1/M2/M3)"
    elif [[ "$arch" == "x86_64" ]]; then
        local expected_prefix="/usr/local"
        pass_test "x86_64 architecture detected (Intel Mac)"
    else
        fail_test "Unknown architecture: $arch"
        return 1
    fi
    
    # Verify Homebrew prefix logic
    get_homebrew_prefix() {
        local arch=$(uname -m)
        if [[ "$arch" == "arm64" ]]; then
            echo "/opt/homebrew"
        else
            echo "/usr/local"
        fi
    }
    
    local detected_prefix=$(get_homebrew_prefix)
    
    if [[ "$detected_prefix" == "$expected_prefix" ]]; then
        pass_test "Homebrew prefix correctly set to $detected_prefix"
    else
        fail_test "Homebrew prefix incorrect: expected $expected_prefix, got $detected_prefix"
    fi
    
    echo ""
}

# Test 2: Java Detection
test_java_detection() {
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Test 2: Java Detection"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    if command -v java &> /dev/null; then
        local java_version=$(java -version 2>&1 | grep -oP 'version "?\K[0-9]+' | head -1)
        echo "Java version detected: $java_version"
        
        if [[ "$java_version" -ge 21 ]]; then
            pass_test "Java $java_version >= 21 (meets requirements)"
        else
            fail_test "Java $java_version < 21 (upgrade required)"
        fi
        
        # Check JAVA_HOME
        if [[ -n "$JAVA_HOME" ]]; then
            pass_test "JAVA_HOME is set: $JAVA_HOME"
        else
            warn_test "JAVA_HOME not set (optional but recommended)"
        fi
        
        # Check java in PATH
        local java_path=$(which java)
        pass_test "Java found in PATH: $java_path"
        
    else
        warn_test "Java not installed (will be installed by run-app.sh)"
    fi
    
    echo ""
}

# Test 3: Homebrew Detection
test_homebrew_detection() {
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Test 3: Homebrew Detection"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    if command -v brew &> /dev/null; then
        local brew_version=$(brew --version | head -1)
        pass_test "Homebrew installed: $brew_version"
        
        local brew_path=$(which brew)
        echo "Homebrew location: $brew_path"
        
        local arch=$(uname -m)
        if [[ "$arch" == "arm64" && "$brew_path" == "/opt/homebrew/bin/brew" ]]; then
            pass_test "Homebrew in correct ARM64 location"
        elif [[ "$arch" == "x86_64" && "$brew_path" == "/usr/local/bin/brew" ]]; then
            pass_test "Homebrew in correct x86_64 location"
        else
            warn_test "Homebrew in non-standard location: $brew_path"
        fi
    else
        warn_test "Homebrew not installed (will be installed by run-app.sh)"
    fi
    
    echo ""
}

# Test 4: Gradle Wrapper Detection
test_gradle_wrapper() {
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Test 4: Gradle Wrapper Detection"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    if [[ -f "./gradlew" ]]; then
        pass_test "Gradle wrapper exists (./gradlew)"
        
        if [[ -x "./gradlew" ]]; then
            pass_test "Gradle wrapper is executable"
        else
            fail_test "Gradle wrapper is not executable"
        fi
        
        # Test gradle wrapper execution
        if ./gradlew --version &> /dev/null; then
            local gradle_version=$(./gradlew --version | grep "Gradle" | head -1)
            pass_test "Gradle wrapper functional: $gradle_version"
        else
            fail_test "Gradle wrapper failed to execute"
        fi
    else
        warn_test "Gradle wrapper not found (will be created by run-app.sh)"
    fi
    
    echo ""
}

# Test 5: Build Environment
test_build_environment() {
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Test 5: Build Environment"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    # Check for required files
    local required_files=("build.gradle.kts" "settings.gradle.kts" "src/main/kotlin")
    
    for file in "${required_files[@]}"; do
        if [[ -e "$file" ]]; then
            pass_test "Required file/directory exists: $file"
        else
            fail_test "Missing required file/directory: $file"
        fi
    done
    
    # Check for build output
    if [[ -d "build" ]]; then
        pass_test "Build directory exists"
        
        if [[ -f "build/libs/deep-work-tracker-1.0.0.jar" ]]; then
            pass_test "JAR file exists (already built)"
        else
            warn_test "JAR file not found (needs build)"
        fi
    else
        warn_test "Build directory not found (needs initial build)"
    fi
    
    echo ""
}

# Test 6: Network Connectivity
test_network_connectivity() {
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Test 6: Network Connectivity"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    # Test connection to Homebrew
    if curl -s --connect-timeout 5 https://brew.sh > /dev/null; then
        pass_test "Can reach brew.sh (Homebrew installation source)"
    else
        fail_test "Cannot reach brew.sh (network issue or firewall)"
    fi
    
    # Test connection to Gradle
    if curl -s --connect-timeout 5 https://services.gradle.org > /dev/null; then
        pass_test "Can reach services.gradle.org (Gradle distribution source)"
    else
        fail_test "Cannot reach services.gradle.org (network issue or firewall)"
    fi
    
    # Test connection to Maven Central
    if curl -s --connect-timeout 5 https://repo1.maven.org/maven2/ > /dev/null; then
        pass_test "Can reach Maven Central (dependency repository)"
    else
        fail_test "Cannot reach Maven Central (network issue or firewall)"
    fi
    
    echo ""
}

# Test 7: Permissions Check
test_permissions() {
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Test 7: Permissions Check"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    # Check if run-app.sh is executable
    if [[ -x "./run-app.sh" ]]; then
        pass_test "run-app.sh is executable"
    else
        fail_test "run-app.sh is not executable (run: chmod +x run-app.sh)"
    fi
    
    # Check write permissions in workspace
    if [[ -w "." ]]; then
        pass_test "Write permissions in current directory"
    else
        fail_test "No write permissions in current directory"
    fi
    
    # Check ~/.productivity-tracker directory
    local data_dir="$HOME/.productivity-tracker"
    if [[ -d "$data_dir" ]]; then
        if [[ -w "$data_dir" ]]; then
            pass_test "Data directory writable: $data_dir"
        else
            fail_test "Data directory not writable: $data_dir"
        fi
    else
        warn_test "Data directory doesn't exist yet (will be created): $data_dir"
    fi
    
    echo ""
}

# Test 8: Mac-Specific Features
test_mac_specific() {
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Test 8: Mac-Specific Features"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    if [[ "$OSTYPE" == "darwin"* ]]; then
        pass_test "Running on macOS"
        
        # Check for java_home utility
        if [[ -x /usr/libexec/java_home ]]; then
            pass_test "/usr/libexec/java_home utility available"
            
            # Try to find Java installations
            local java_homes=$(/usr/libexec/java_home -V 2>&1 | grep -E "^[ ]*[0-9]" || true)
            if [[ -n "$java_homes" ]]; then
                echo "Java installations found:"
                echo "$java_homes"
            fi
        else
            warn_test "/usr/libexec/java_home utility not found"
        fi
        
        # Check macOS version
        local macos_version=$(sw_vers -productVersion)
        echo "macOS version: $macos_version"
        pass_test "macOS version detected"
        
    else
        warn_test "Not running on macOS (Mac-specific tests skipped)"
    fi
    
    echo ""
}

# Run all tests
main() {
    test_architecture_detection
    test_homebrew_detection
    test_java_detection
    test_gradle_wrapper
    test_build_environment
    test_network_connectivity
    test_permissions
    test_mac_specific
    
    # Summary
    echo "╔═══════════════════════════════════════════════════════════╗"
    echo "║  Test Summary                                             ║"
    echo "╚═══════════════════════════════════════════════════════════╝"
    echo ""
    echo -e "${GREEN}Passed:${NC} $TESTS_PASSED"
    echo -e "${RED}Failed:${NC} $TESTS_FAILED"
    echo ""
    
    if [[ $TESTS_FAILED -eq 0 ]]; then
        echo -e "${GREEN}✓ All critical tests passed!${NC}"
        echo ""
        echo "Your environment is ready. Run: ./run-app.sh"
        return 0
    else
        echo -e "${RED}✗ Some tests failed.${NC}"
        echo ""
        echo "Fix the failed tests before running ./run-app.sh"
        echo "Or run ./run-app.sh anyway - it will attempt to auto-install missing dependencies."
        return 1
    fi
}

# Execute main function
main
