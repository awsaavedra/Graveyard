# Deep Work Tracker

Simple daily calendar tracker for 4 hours of deep work.

## Quick Start

```bash
chmod +x run-app.sh
./run-app.sh
```

First build takes ~30 seconds. Subsequent builds are cached.

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

## Requirements

- Java 8+
- Maven (auto-installed if missing)
