# Deep Work Tracker

Simple daily calendar tracker for 8 hours of deep work.

## Quick Start

```bash
cd /home/aws/workspace/random/productivity-tracker
chmod +x run-app.sh
./run-app.sh
```

First build takes ~30 seconds. Subsequent builds are cached.

## How It Works

**Calendar view** - Shows monthly calendar with daily progress:
- `[✓8]` = 8 hours logged (complete!)
- `[•6]` = 4+ hours logged
- `[+2]` = 1-3 hours logged
- `[ 0]` = 0 hours logged (not started)

**Commands:**
- `[N]` - Next month
- `[P]` - Previous month
- `[T]` - Edit today's hours (0-8)
- `[Q]` - Quit

**Edit View** - Select hours for today:
- Shows checklist from 1-8 hours
- Enter number to save

## Data

All data stored locally at: `~/.productivity-tracker/deep-work.db`

## Files

- **4 Kotlin files** (210 lines total)
  - Models.kt - Data class
  - Repository.kt - SQLite database
  - CalendarUI.kt - Calendar display
  - App.kt - Entry point

- **Maven build** (pom.xml)
- **Simple run script** (run-app.sh)

## Requirements

- Java 8+
- Maven (auto-installed if missing)

That's it! Simple and minimal.
