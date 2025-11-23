package com.productivitytracker.ui

import com.productivitytracker.models.DailyEntry
import com.productivitytracker.repository.EntryRepository
import java.time.LocalDate
import java.time.YearMonth

class DeepWorkTracker(private val repository: EntryRepository) {
    private var currentMonth = YearMonth.now()

    fun start() {
        while (true) {
            showCalendar()
            print("Choice: ")
            val input = readLine()?.trim() ?: continue
            
            when (input.toLowerCase()) {
                "n" -> currentMonth = currentMonth.plusMonths(1)
                "p" -> currentMonth = currentMonth.minusMonths(1)
                "t" -> editToday()
                "q" -> {
                    println("Goodbye!")
                    return
                }
            }
        }
    }

    private fun showCalendar() {
        clearScreen()
        println("╔════════════════════════════════════════════════════════════════╗")
        println("║              DEEP WORK TRACKER (4 HOURS/DAY)                  ║")
        println("╚════════════════════════════════════════════════════════════════╝")
        println()

        val year = currentMonth.year
        val month = currentMonth.month
        val firstDay = LocalDate.of(year, month, 1)
        val daysInMonth = firstDay.lengthOfMonth()
        val startDayOfWeek = firstDay.dayOfWeek.value % 7

        println("┌─ ${month.toString().padEnd(10)} $year ${" ".repeat(35)}┐")
        println("│ Sun    Mon    Tue    Wed    Thu    Fri    Sat                  │")
        println("├────────────────────────────────────────────────────────────────┤")

        var dayCounter = 0
        var dayLine = "│ "
        var hoursLine = "│ "
        
        // Leading spaces for first week
        for (i in 0 until startDayOfWeek) {
            dayLine += "       "
            hoursLine += "       "
        }

        for (day in 1..daysInMonth) {
            val date = LocalDate.of(year, month, day)
            val entry = repository.getEntry(date)
            val hours = entry?.hoursLogged ?: 0
            
            val display = when {
                hours >= 4 -> "✓ $day".padEnd(6)
                else -> "$day".padEnd(6)
            }

            val hoursDots = when (hours) {
                4 -> "⊙⊙⊙⊙"
                3 -> "⊙⊙⊙ "
                2 -> "⊙⊙  "
                1 -> "⊙   "
                else -> "    "
            }.padEnd(6)

            dayLine += display + " "
            hoursLine += hoursDots + " "
            dayCounter++

            if ((startDayOfWeek + dayCounter) % 7 == 0 || day == daysInMonth) {
                if (day == daysInMonth) {
                    val padding = 7 - ((startDayOfWeek + dayCounter) % 7)
                    dayLine += " ".repeat(padding * 7)
                    hoursLine += " ".repeat(padding * 7)
                }
                println(dayLine + "│")
                println(hoursLine + "│")
                dayLine = "│ "
                hoursLine = "│ "
            }
        }

        println("├────────────────────────────────────────────────────────────────┤")
        println("│ Legend: ✓=4h complete  ⊙⊙⊙⊙=4h  ⊙⊙⊙=3h  ⊙⊙=2h  ⊙=1h         │")
        println("└────────────────────────────────────────────────────────────────┘")
        println()
        println("[n]ext month | [p]rev month | [t]oday | [q]uit")
    }

    private fun editToday() {
        val today = LocalDate.now()
        var entry = repository.getEntry(today) ?: DailyEntry(today.toString(), 0)
        
        clearScreen()
        println("╔════════════════════════════════════════════════════════════════╗")
        println("║              LOG HOURS FOR TODAY: $today                     ║")
        println("╚════════════════════════════════════════════════════════════════╝")
        println()
        
        while (true) {
            println("Current: ${entry.hoursLogged}/4 hours")
            println()
            
            for (i in 1..4) {
                val checked = if (i <= entry.hoursLogged) "☑" else "☐"
                println("$checked Hour $i")
            }
            
            println()
            println("[1-4] Toggle hour | [S]ave & back | [C]lear all")
            print("Choice: ")
            val input = readLine()?.trim() ?: continue
            
            when {
                input in "1234" -> {
                    val hour = input.toInt()
                    val newHours = if (hour <= entry.hoursLogged) {
                        entry.hoursLogged - 1
                    } else {
                        entry.hoursLogged + 1
                    }
                    entry = entry.copy(hoursLogged = newHours.coerceIn(0, 4))
                    clearScreen()
                }
                input.toLowerCase() == "s" -> {
                    repository.updateEntry(entry)
                    println("✓ Saved!")
                    Thread.sleep(800)
                    return
                }
                input.toLowerCase() == "c" -> {
                    entry = entry.copy(hoursLogged = 0)
                    clearScreen()
                }
            }
        }
    }

    private fun clearScreen() {
        print("\u001b[2J\u001b[H")
    }
}
