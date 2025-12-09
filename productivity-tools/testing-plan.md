# Testing Plan - Deep Work Tracker

## Unit Tests

### 1. Input Validation Tests

#### 1.1 Day Input Validation
- **Test:** Day number within valid range (1 to days in month)
  - Input: Day 15 in November (30 days)
  - Expected: Accept and proceed to edit view
  
- **Test:** Day number at lower boundary
  - Input: Day 1
  - Expected: Accept and proceed to edit view
  
- **Test:** Day number at upper boundary
  - Input: Day 30 for November, Day 31 for January
  - Expected: Accept and proceed to edit view
  
- **Test:** Day number below valid range
  - Input: Day 0
  - Expected: Reject with error message "Invalid day. Please enter a day between 1 and X"
  
- **Test:** Day number above valid range
  - Input: Day 31 for November (has 30 days)
  - Expected: Reject with error message "Invalid day. Please enter a day between 1 and 30"
  
- **Test:** Day number far beyond range
  - Input: Day 100, Day 365, Day -5
  - Expected: Reject with appropriate error message
  
- **Test:** Non-numeric day input
  - Input: "abc", "!@#", empty string
  - Expected: Ignore or show error, return to main menu
  
- **Test:** Leap year February validation
  - Input: Day 29 in February 2024 (leap year)
  - Expected: Accept
  - Input: Day 29 in February 2025 (non-leap year)
  - Expected: Reject

#### 1.2 Hours Input Validation
- **Test:** Hours within valid range (0-4)
  - Input: 0, 1, 2, 3, 4
  - Expected: Set hours to exact value
  
- **Test:** Hours below valid range
  - Input: -1, -10
  - Expected: Reject or clamp to 0
  
- **Test:** Hours above valid range
  - Input: 5, 10, 100
  - Expected: Reject or clamp to 4
  
- **Test:** Non-numeric hours input
  - Input: "abc", "5.5", "two"
  - Expected: Ignore and stay in edit mode
  
- **Test:** Empty hours input
  - Input: "" (empty string)
  - Expected: Ignore and stay in edit mode

#### 1.3 Navigation Command Validation
- **Test:** Valid navigation commands
  - Input: "n", "p", "t", "q" (case insensitive)
  - Expected: Execute corresponding action
  
- **Test:** Case insensitivity
  - Input: "N", "P", "T", "Q"
  - Expected: Execute corresponding action
  
- **Test:** Invalid navigation commands
  - Input: "x", "help", "exit"
  - Expected: Ignore and redisplay prompt

### 2. Data Persistence Tests

#### 2.1 Save Operations
- **Test:** Save hours for a specific day
  - Action: Edit day, set hours to 3, save
  - Expected: Data persisted to SQLite database
  
- **Test:** Update existing hours
  - Setup: Day already has 2 hours logged
  - Action: Change to 4 hours, save
  - Expected: Database updated to 4 hours
  
- **Test:** Save zero hours (clear)
  - Setup: Day has 3 hours logged
  - Action: Set to 0 hours, save
  - Expected: Database updated to 0 hours
  
- **Test:** Save without changes
  - Setup: Day has 2 hours
  - Action: Open edit view, save immediately
  - Expected: No database changes, no errors

#### 2.2 Load Operations
- **Test:** Load existing hours for a day
  - Setup: Database has 3 hours for 2025-11-15
  - Action: Edit day 15
  - Expected: Display shows "Current: 3/4 hours"
  
- **Test:** Load non-existent day
  - Setup: No entry for 2025-11-20
  - Action: Edit day 20
  - Expected: Display shows "Current: 0/4 hours"
  
- **Test:** Load hours across month boundaries
  - Setup: November has entries, December doesn't
  - Action: Navigate to December, edit day
  - Expected: Shows 0 hours for December days

#### 2.3 Database Integrity
- **Test:** Concurrent access (if applicable)
  - Action: Multiple rapid saves
  - Expected: All saves complete without data corruption
  
- **Test:** Database file creation
  - Setup: No database file exists
  - Action: Save first entry
  - Expected: Database file created at ~/.productivity-tracker/deep-work.db
  
- **Test:** Database migration/schema validation
  - Expected: Correct schema version, all tables exist

### 3. Calendar Display Tests

#### 3.1 Date Calculations
- **Test:** First day of month alignment
  - Input: November 2025 (starts on Saturday)
  - Expected: Calendar shows blank cells for Sun-Fri, day 1 on Saturday
  
- **Test:** Month with different lengths
  - Input: February (28/29 days), April (30 days), January (31 days)
  - Expected: Calendar displays correct number of days
  
- **Test:** Year transition
  - Input: Navigate from December 2025 to January 2026
  - Expected: Year updates correctly, calendar resets
  
- **Test:** Today indicator
  - Expected: Current day highlighted or marked with ✓

#### 3.2 Hours Display
- **Test:** Display symbols for different hour counts
  - 0 hours: Empty space "    "
  - 1 hour: "⊙   "
  - 2 hours: "⊙⊙  "
  - 3 hours: "⊙⊙⊙ "
  - 4 hours: "⊙⊙⊙⊙" or "✓"
  - Expected: Correct symbols displayed in calendar grid

### 4. Performance Tests

#### 4.1 Application Startup
- **Test:** Cold start time
  - Expected: Application starts in < 0.5 seconds
  
- **Test:** Warm start with CDS cache
  - Expected: Application starts in < 0.25 seconds
  
- **Test:** Memory usage
  - Expected: Reasonable memory footprint (< 100MB)

#### 4.2 Database Operations
- **Test:** Load calendar month data
  - Setup: Month with 30 entries
  - Expected: Load completes in < 50ms
  
- **Test:** Save operation speed
  - Expected: Save completes in < 10ms
  
- **Test:** Large dataset handling
  - Setup: Database with years of data (365+ entries)
  - Expected: No performance degradation

#### 4.3 UI Responsiveness
- **Test:** Screen refresh rate
  - Action: Navigate between months rapidly
  - Expected: No lag, smooth transitions
  
- **Test:** Input handling delay
  - Action: Type commands quickly
  - Expected: All commands processed correctly

### 5. Error Handling Tests

#### 5.1 File System Errors
- **Test:** Database directory not writable
  - Setup: Remove write permissions on ~/.productivity-tracker/
  - Expected: Graceful error message, suggest solution
  
- **Test:** Database file corrupted
  - Setup: Corrupt database file
  - Expected: Error message, attempt recovery or create new
  
- **Test:** Disk full scenario
  - Setup: No disk space available
  - Expected: Error message indicating disk full

#### 5.2 Input Stream Errors
- **Test:** EOF on stdin
  - Setup: Close stdin unexpectedly
  - Expected: Application exits gracefully
  
- **Test:** Invalid UTF-8 input
  - Input: Binary data or malformed UTF-8
  - Expected: Handle gracefully without crash

#### 5.3 Runtime Errors
- **Test:** Out of memory
  - Expected: Graceful error or degraded mode
  
- **Test:** Thread interruption
  - Expected: Clean shutdown without data loss

### 6. Edge Cases and Boundary Conditions

#### 6.1 Date Edge Cases
- **Test:** Leap second handling (if applicable)
- **Test:** Daylight saving time transitions
- **Test:** Timezone changes
- **Test:** System clock set backwards
  - Expected: Handle gracefully, no data corruption

#### 6.2 Data Limits
- **Test:** Maximum database size
  - Setup: 100+ years of data
  - Expected: Reasonable performance maintained
  
- **Test:** Extremely old dates
  - Input: Year 1900, 1970
  - Expected: Handle correctly or reject gracefully
  
- **Test:** Future dates
  - Input: Year 2100
  - Expected: Handle correctly

#### 6.3 Concurrent Usage
- **Test:** Multiple instances running
  - Expected: Database locking prevents corruption
  
- **Test:** Backup while running
  - Expected: No interference with operation

### 7. Integration Tests

#### 7.1 Full User Workflows
- **Test:** Complete daily logging workflow
  - Steps: Launch → Press 't' → Set hours → Save → Quit
  - Expected: Hours saved and retrievable
  
- **Test:** Month navigation and editing
  - Steps: Launch → Navigate to previous month → Edit day → Save → Return to current month
  - Expected: All data persists correctly
  
- **Test:** Multi-day editing session
  - Steps: Edit day 1, day 15, day 30 in sequence
  - Expected: All changes saved independently

#### 7.2 System Integration
- **Test:** Terminal compatibility
  - Environments: Bash, Zsh, Fish, Windows Git Bash
  - Expected: Displays correctly in all terminals
  
- **Test:** ANSI color support
  - Expected: Degrades gracefully in no-color terminals
  
- **Test:** Screen size handling
  - Test with: 80x24, 120x40, very small terminals
  - Expected: Layout adapts or scrolls appropriately

### 8. Regression Tests

#### 8.1 Previous Bug Fixes
- **Test:** [Document specific bugs when found]
  - Setup: Conditions that triggered original bug
  - Expected: Bug does not recur

#### 8.2 Feature Additions
- **Test:** New features don't break existing functionality
  - Expected: All previous tests still pass

### 9. Build and Deployment Tests

#### 9.1 Build Performance (from test-app.sh)
- **Test:** Cached run performance
  - Expected: < 0.5 seconds
  
- **Test:** Warm rebuild
  - Expected: < 5 seconds
  
- **Test:** Cold build
  - Expected: < 20 seconds

#### 9.2 Package Integrity
- **Test:** JAR file completeness
  - Expected: All dependencies included, correct manifest
  
- **Test:** CDS archive generation
  - Expected: Archive created successfully, improves startup time

#### 9.3 Cross-platform Compatibility
- **Test:** Linux (Ubuntu, RHEL, Arch)
  - Expected: Builds and runs correctly
  
- **Test:** macOS (Intel, Apple Silicon)
  - Expected: Builds and runs correctly
  
- **Test:** Windows (WSL, Git Bash)
  - Expected: Builds and runs correctly

### 10. Timeout and Hanging Tests

#### 10.1 Application Timeouts
- **Test:** Unresponsive application detection
  - Setup: Simulate infinite loop or deadlock
  - Expected: Timeout mechanism terminates gracefully
  
- **Test:** Database query timeout
  - Setup: Very slow database operation
  - Expected: Query timeout, error message displayed

#### 10.2 User Input Timeout
- **Test:** Idle timeout (if implemented)
  - Setup: No user input for extended period
  - Expected: Application continues waiting or exits cleanly
  
- **Test:** Slow user input
  - Setup: Delay between keystrokes
  - Expected: Application remains responsive

### 11. Accessibility and Usability Tests

#### 11.1 Screen Reader Compatibility
- **Test:** Text-only output mode
  - Expected: All UI elements readable without Unicode symbols

#### 11.2 Keyboard Navigation
- **Test:** All features accessible via keyboard
  - Expected: No mouse required

#### 11.3 Error Message Clarity
- **Test:** Error messages are actionable
  - Expected: Messages include what went wrong and how to fix it

---

## Test Execution Strategy

### Priority Levels
1. **P0 (Critical):** Input validation, data persistence, core navigation
2. **P1 (High):** Performance, error handling, calendar display
3. **P2 (Medium):** Edge cases, cross-platform, integration
4. **P3 (Low):** Accessibility, advanced edge cases

### Test Frequency
- **Pre-commit:** P0 tests
- **CI/CD Pipeline:** P0 + P1 tests
- **Release Candidate:** All tests (P0-P3)
- **Nightly:** Full suite + performance benchmarks

### Success Criteria
- **Unit Tests:** 100% pass rate required
- **Performance Tests:** Meet defined SLAs
- **Integration Tests:** 95% pass rate minimum
- **Code Coverage:** > 80% line coverage, > 70% branch coverage

### Test Automation
- Run via `./test-app.sh` for shell-based E2E tests
- Run via `./gradlew test` for Kotest unit/integration tests
- Generate reports for coverage and performance trends

---

## Mac M2 First-Run Installation Testing Plan

### Objective
Ensure `./run-app.sh` successfully installs all dependencies and runs the application on a fresh Mac M2 (ARM64) system in a single execution, without requiring manual intervention or terminal restart.

### Current Issue Analysis

#### Root Cause
On Mac M2 systems without Java/Gradle pre-installed:
1. Script installs Java 21 via Homebrew to `/opt/homebrew/opt/openjdk@21/`
2. Java binary is NOT added to current shell's PATH
3. Subsequent `get_java_version()` call fails to detect Java
4. Script exits with "Java 21 installation failed" error
5. **Result:** User must manually update PATH or restart terminal, breaking single-run promise

#### Secondary Issues
- No Homebrew auto-installation if missing
- No architecture detection (ARM64 vs x86_64)
- Hardcoded Homebrew paths don't handle Intel Macs
- No verification that Java is actually accessible after installation

### Implementation Plan

#### 1. Add Homebrew Auto-Installation
**Changes to `install_java()` function:**
- Detect if Homebrew is installed via `command -v brew`
- If missing, auto-install using official Homebrew install script
- Refresh environment variables with `eval "$(/opt/homebrew/bin/brew shellenv)"`
- **Validation:** Check `command -v brew` returns success after installation

#### 2. Fix PATH Refresh After Java Installation
**Changes to `install_java()` function - Mac section:**
- After `brew install openjdk@21`, immediately export to PATH:
  ```bash
  export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"
  ```
- Set JAVA_HOME environment variable:
  ```bash
  export JAVA_HOME="/opt/homebrew/opt/openjdk@21"
  ```
- **Validation:** Run `java -version` immediately after PATH export to confirm accessibility

#### 3. Detect Architecture for Homebrew Paths
**Add new function `get_homebrew_prefix()`:**
- Use `uname -m` to detect architecture
- Return `/opt/homebrew` for ARM64 (M1/M2/M3)
- Return `/usr/local` for x86_64 (Intel Macs)
- **Validation:** Check directory exists and contains `bin/brew`

#### 4. Add Post-Install Verification with Fallback
**Changes to main script flow after `install_java()`:**
- First attempt: Check `command -v java`
- Fallback: Use `/usr/libexec/java_home -v 21` to locate Java installation
- If found via fallback, export that path to PATH
- Final verification: Run `java -version` and parse output
- **Validation:** `get_java_version()` must return >= 21

#### 5. Enhanced Error Messages and User Guidance
**Changes to error handling:**
- If Homebrew installation fails, provide manual installation command
- If Java installation succeeds but isn't accessible, show PATH export command
- Detect if running under sudo and warn about Homebrew limitations
- **Validation:** Error messages include actionable next steps

### Test Scenarios

#### Test Case 1: Fresh Mac M2 - No Homebrew, No Java, No Gradle
**Setup:**
- Mac M2 (ARM64) system
- No Homebrew installed
- No Java installed
- No Gradle installed
- Clean workspace (no `build/` directory)

**Execution:**
```bash
./run-app.sh
```

**Expected Outcome:**
1. Script detects no Homebrew → Auto-installs Homebrew
2. Script detects no Java → Auto-installs Java 21 via Homebrew
3. PATH is refreshed automatically
4. Java 21 detected successfully
5. Gradle wrapper is initialized
6. Application builds successfully
7. CDS archive is created
8. Application launches and displays calendar

**Success Criteria:**
- No manual PATH editing required
- No terminal restart required
- Application runs successfully on first execution
- Total time: < 5 minutes (including Homebrew + Java download)

#### Test Case 2: Fresh Mac M2 - Homebrew Installed, No Java
**Setup:**
- Mac M2 (ARM64) system
- Homebrew already installed
- No Java installed
- No Gradle installed

**Execution:**
```bash
./run-app.sh
```

**Expected Outcome:**
1. Script detects Homebrew ✓
2. Script detects no Java → Installs Java 21
3. PATH refreshed automatically
4. Application builds and runs
5. Total time: < 3 minutes

**Success Criteria:**
- Single-run success
- Java accessible immediately after installation

#### Test Case 3: Intel Mac - No Homebrew, No Java
**Setup:**
- Intel Mac (x86_64) system
- No Homebrew installed
- No Java installed

**Execution:**
```bash
./run-app.sh
```

**Expected Outcome:**
1. Architecture detection: x86_64
2. Homebrew installed to `/usr/local`
3. Java installed to `/usr/local/opt/openjdk@21`
4. Correct paths used throughout
5. Application runs successfully

**Success Criteria:**
- Script handles Intel Mac paths correctly
- No ARM64-specific assumptions break execution

#### Test Case 4: Mac M2 - Java 17 Installed (Upgrade Scenario)
**Setup:**
- Mac M2 with Java 17 already installed
- Requires Java 21 upgrade

**Execution:**
```bash
./run-app.sh
```

**Expected Outcome:**
1. Script detects Java 17 < 21 required
2. Installs Java 21 alongside Java 17
3. PATH prioritizes Java 21
4. Application uses Java 21

**Success Criteria:**
- Java 17 remains installed (no removal)
- Java 21 is active for this application

#### Test Case 5: Network Failure Simulation
**Setup:**
- Mac M2, no Java
- Simulate network failure during Homebrew/Java download

**Execution:**
```bash
# Disconnect network mid-installation
./run-app.sh
```

**Expected Outcome:**
1. Homebrew/Java installation fails
2. Script provides clear error message
3. Script does not corrupt existing installation
4. Re-running after network restore succeeds

**Success Criteria:**
- Graceful error handling
- No partial/broken installations
- Recovery possible by re-running script

### Validation Methods

#### Automated Validation Script
**Create `test-mac-setup.sh`:**
```bash
#!/bin/bash
# Simulates fresh Mac M2 environment

# Test 1: Check Homebrew detection and installation
test_homebrew_setup() {
    # Mock: Homebrew not in PATH
    PATH_BACKUP="$PATH"
    export PATH="/usr/bin:/bin:/usr/sbin:/sbin"
    
    # Run relevant portion of run-app.sh
    # Verify Homebrew installation triggered
    # Verify PATH refreshed
    
    export PATH="$PATH_BACKUP"
}

# Test 2: Check Java PATH refresh
test_java_path_refresh() {
    # After Java installation, verify:
    # - java command available
    # - java -version shows 21
    # - JAVA_HOME set correctly
}

# Test 3: Architecture detection
test_architecture_detection() {
    ARCH=$(uname -m)
    if [[ "$ARCH" == "arm64" ]]; then
        # Verify /opt/homebrew used
    elif [[ "$ARCH" == "x86_64" ]]; then
        # Verify /usr/local used
    fi
}
```

#### Manual Validation Checklist
**For each test case:**
- [ ] Script completes without errors
- [ ] No manual PATH editing required
- [ ] No terminal restart required
- [ ] Application launches successfully
- [ ] Calendar displays correctly
- [ ] Can log hours and persist to database
- [ ] CDS optimization works (startup < 0.5s on second run)

#### CI/CD Integration
**GitHub Actions workflow for Mac M2:**
```yaml
name: Mac M2 First-Run Test
on: [push, pull_request]
jobs:
  test-mac-arm64:
    runs-on: macos-14-arm64  # M1/M2 runner
    steps:
      - uses: actions/checkout@v4
      - name: Remove existing Java/Gradle
        run: |
          brew uninstall openjdk@21 || true
          rm -rf ~/.gradle
      - name: Test first-run installation
        run: ./run-app.sh <<< "q"
      - name: Verify Java 21 installed
        run: java -version | grep "21"
      - name: Test second run (CDS optimization)
        run: time ./run-app.sh <<< "q"
```

### Performance Benchmarks

#### Installation Time Targets
- **Fresh Mac M2 (no Homebrew):** < 5 minutes total
  - Homebrew installation: ~2 minutes
  - Java 21 installation: ~2 minutes
  - Gradle wrapper + build: ~1 minute
  
- **Mac with Homebrew (no Java):** < 3 minutes total
  - Java 21 installation: ~2 minutes
  - Build: ~1 minute

- **Mac with Java 21 (rebuild only):** < 10 seconds
  - Incremental build with cache

#### Startup Time Targets (After Installation)
- **Cold start (no CDS):** < 1 second
- **Warm start (with CDS):** < 0.3 seconds

### Rollback Plan

If Mac M2 first-run changes cause regressions:

1. **Immediate rollback:** Revert `run-app.sh` to previous version
2. **Preserve test coverage:** Keep Kotest tests (P0/P1) as they're isolated
3. **Alternative approach:** Create separate `setup-mac.sh` script for one-time setup
4. **Documentation:** Update README with manual installation steps

### Success Metrics

#### Quantitative
- **100%** success rate on fresh Mac M2 (no pre-installed dependencies)
- **< 5 minutes** total time from `./run-app.sh` to running application
- **Zero** manual PATH edits required
- **Zero** terminal restarts required

#### Qualitative
- User feedback: "Installation just worked"
- No GitHub issues related to Mac M2 setup failures
- Script handles errors gracefully with actionable messages

### Post-Implementation Verification

After implementing changes:

1. **Test on physical Mac M2:**
   - Borrow/rent Mac M2 device or use GitHub Actions mac-14-arm64 runner
   - Factory reset or create new user account (clean environment)
   - Run `./run-app.sh` and document all output
   - Verify single-run success

2. **Test on Intel Mac (if available):**
   - Verify architecture detection works
   - Confirm `/usr/local` paths used correctly

3. **Regression testing:**
   - Run on Linux (ensure Mac changes don't break Linux)
   - Run all P0/P1 Kotest tests
   - Run `test-app.sh` functional tests

4. **Update documentation:**
   - Add "Tested on Mac M2" badge to README
   - Document known limitations (e.g., Homebrew sudo prompts)
   - Add troubleshooting section for Mac-specific issues

### Known Limitations & Risks

#### Homebrew Installation Requires User Interaction
- Homebrew install script may prompt for sudo password
- Cannot be fully automated without user input
- **Mitigation:** Warn user upfront about sudo prompt requirement

#### Network Dependency
- All installations require active internet connection
- No offline installation support
- **Mitigation:** Check network connectivity before attempting downloads

#### Homebrew Install Time Variability
- Can take 2-10 minutes depending on network speed
- User may think script is hung
- **Mitigation:** Add progress indicators and estimated time remaining

#### macOS Security Restrictions
- Gatekeeper may block downloaded binaries
- User may need to approve in Security & Privacy settings
- **Mitigation:** Use signed Homebrew packages (automatically trusted)

#### Potential PATH Pollution
- Exporting to PATH affects only current session
- User's shell config remains unchanged
- **Consideration:** Should we append to ~/.zshrc for persistence?
  - **Pro:** Permanent fix, works across sessions
  - **Con:** Modifies user's shell config without explicit permission

### Future Enhancements

1. **Add `--dry-run` flag:** Show what would be installed without executing
2. **Add `--verbose` flag:** Show detailed installation progress
3. **Support alternative package managers:** MacPorts, SDKMAN for Java
4. **Cache downloaded files:** Speed up reinstalls by keeping local copies
5. **Parallel downloads:** Install Homebrew and Gradle simultaneously
6. **Health check command:** `./run-app.sh --doctor` to diagnose setup issues
