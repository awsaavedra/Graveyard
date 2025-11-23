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
        repeat(startDayOfWeek) {
            dayLine += "       "
            hoursLine += "       "
        }

        for (day in 1..daysInMonth) {
            val date = LocalDate.of(year, month, day)
            val hours = repository.getEntry(date)?.hoursLogged ?: 0
            
            val display = if (hours >= 4) "✓ $day".padEnd(6) else "$day".padEnd(6)
            val dots = when (hours) {
                4 -> "⊙⊙⊙⊙"
                3 -> "⊙⊙⊙ "
                2 -> "⊙⊙  "
                1 -> "⊙   "
                else -> "    "
            }.padEnd(6)

            dayLine += display + " "
            hoursLine += dots + " "
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
        var hours = repository.getEntry(today)?.hoursLogged ?: 0
        
        while (true) {
            clearScreen()
            println("╔════════════════════════════════════════════════════════════════╗")
            println("║              LOG HOURS FOR TODAY: $today                     ║")
            println("╚════════════════════════════════════════════════════════════════╝")
            println()
            println("Current: $hours/4 hours")
            println()
            
            repeat(4) { i ->
                println("${if (i + 1 <= hours) "☑" else "☐"} Hour ${i + 1}")
            }
            
            println()
            println("[1-4] Toggle hour | [S]ave & back | [C]lear all")
            print("Choice: ")
            val input = readLine()?.trim() ?: continue
            
            when {
                input in "1234" -> hours = if (input.toInt() <= hours) hours - 1 else hours + 1
                input.toLowerCase() == "s" -> {
                    repository.updateEntry(DailyEntry(today.toString(), hours.coerceIn(0, 4)))
                    println("✓ Saved!")
                    Thread.sleep(800)
                    return
                }
                input.toLowerCase() == "c" -> hours = 0
            }
        }
    }

    private fun clearScreen() {
        print("\u001b[2J\u001b[H")
    }
}
