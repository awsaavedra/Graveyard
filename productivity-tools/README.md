# Deep Work Tracker

Simple daily calendar tracker for 4 hours of deep work.

## Installation

```bash
./run-app.sh
```

**That's it.** First run auto-installs Java 21 and Gradle if needed, then builds and runs the app.

- **Instant runs:** ~0.20 seconds (with CDS cache)
  - No rebuild needed, just launches the JAR
- **Warm rebuild:** ~2.5 seconds (daemon running)
  - Gradle daemon already running, incremental compilation
- **Cold build:** ~8 seconds (daemon startup + build)
  - Fresh Gradle daemon startup, full compilation

Run `./test-app.sh` to verify performance and functionality on your system.

**Supported:** Linux (apt/yum/dnf), macOS (Homebrew), Windows (manual install prompts)

## How It Works

**Calendar view** - Shows monthly calendar with daily progress:
- `✓` = 4 hours logged (complete!)
- `⊙⊙⊙` = 3 hours logged
- `⊙⊙` = 2 hours logged
- `⊙` = 1 hour logged
- Empty = 0 hours logged

**Commands:**
- `[n]` - Next month
- `[p]` - Previous month
- `[t]` - Edit today's hours (0-4)
- `[1-30]` - Edit specific day (range adapts to month length)
- `[q]` - Quit

**Edit View** - Set hours for a day:
- Enter `0-4` to set exact hours
- Press `[s]` to save and return

## Data

All data stored locally at: `~/.productivity-tracker/deep-work.db`

## Stack

- **Kotlin** (statically typed)
- **SQLite** (local-only persistence)
- **Gradle** (fast incremental builds with daemon + cache)
- **Java 21 LTS** (runtime with CDS optimization)
