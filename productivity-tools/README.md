# Deep Work Tracker

Simple daily calendar tracker for 4 hours of deep work.

## Installation

```bash
./run-app.sh
```

**That's it.** First run auto-installs Java 21 and Maven if needed, then builds and runs the app.

- First run: ~30 seconds (setup + build)
- Subsequent runs: ~6 seconds (cached)

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
- `[q]` - Quit

**Edit View** - Toggle hours for today:
- Shows checkbox for each hour (1-4)
- Press [1-4] to toggle, [s]ave to commit, [c]lear all

## Data

All data stored locally at: `~/.productivity-tracker/deep-work.db`

## Stack

- **Kotlin** (statically typed)
- **SQLite** (local-only persistence)
- **Maven** (build system)
- **Java 21 LTS** (runtime)
