# Deep Work Tracker - Simple Version

This is a minimal calendar tracker for 8 hours of deep work per day.

## Files

- **Models.kt** - Just one data class: `DailyEntry(date, hoursLogged)`
- **Repository.kt** - SQLite database with simple get/update
- **CalendarUI.kt** - Monthly calendar display + today editor
- **App.kt** - 10 lines to start everything

**Total: 210 lines of Kotlin code**

## How It Works

```
1. Shows monthly calendar
2. Each day shows: [✓8] or [•6] or [+2] or [ 0]
3. Press [T] to edit today
4. Enter hours 0-8
5. Data saved to SQLite
```

## Run

```bash
chmod +x run-app.sh
./run-app.sh
```

## Commands

- `[N]` - Next month
- `[P]` - Previous month  
- `[T]` - Edit today's hours
- `[Q]` - Quit

Done!
