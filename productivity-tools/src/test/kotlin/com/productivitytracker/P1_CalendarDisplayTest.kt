package com.productivitytracker

import com.productivitytracker.models.DailyEntry
import com.productivitytracker.repository.EntryRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File
import java.time.LocalDate
import java.time.YearMonth

class P1_CalendarDisplayTest : FunSpec({
    
    lateinit var repository: EntryRepository
    lateinit var testDbFile: File
    
    beforeTest {
        testDbFile = File.createTempFile("test-deep-work-", ".db")
        repository = EntryRepository(testDbFile.absolutePath)
    }
    
    afterTest {
        repository.close()
        testDbFile.delete()
    }
    
    context("P1: Date Calculations") {
        
        test("Should calculate correct month length for 31-day month") {
            val yearMonth = YearMonth.of(2025, 1) // January
            
            yearMonth.lengthOfMonth() shouldBe 31
        }
        
        test("Should calculate correct month length for 30-day month") {
            val yearMonth = YearMonth.of(2025, 11) // November
            
            yearMonth.lengthOfMonth() shouldBe 30
        }
        
        test("Should calculate correct month length for February non-leap year") {
            val yearMonth = YearMonth.of(2025, 2) // February 2025
            
            yearMonth.lengthOfMonth() shouldBe 28
        }
        
        test("Should calculate correct month length for February leap year") {
            val yearMonth = YearMonth.of(2024, 2) // February 2024
            
            yearMonth.lengthOfMonth() shouldBe 29
        }
        
        test("Should handle year transition from December to January") {
            val december = YearMonth.of(2025, 12)
            val january = december.plusMonths(1)
            
            january.year shouldBe 2026
            january.monthValue shouldBe 1
        }
        
        test("Should handle year transition from January to December") {
            val january = YearMonth.of(2026, 1)
            val december = january.minusMonths(1)
            
            december.year shouldBe 2025
            december.monthValue shouldBe 12
        }
        
        test("Should correctly identify day of week for month start") {
            val november2025 = YearMonth.of(2025, 11)
            val firstDay = november2025.atDay(1)
            
            // November 1, 2025 is a Saturday (day 6)
            firstDay.dayOfWeek.value shouldBe 6
        }
    }
    
    context("P1: Data Display Logic") {
        
        test("Should display empty for 0 hours") {
            val hours = 0
            val display = "⊙".repeat(hours)
            
            display shouldBe ""
        }
        
        test("Should display one symbol for 1 hour") {
            val hours = 1
            val display = "⊙".repeat(hours)
            
            display shouldBe "⊙"
        }
        
        test("Should display two symbols for 2 hours") {
            val hours = 2
            val display = "⊙".repeat(hours)
            
            display shouldBe "⊙⊙"
        }
        
        test("Should display three symbols for 3 hours") {
            val hours = 3
            val display = "⊙".repeat(hours)
            
            display shouldBe "⊙⊙⊙"
        }
        
        test("Should display four symbols for 4 hours") {
            val hours = 4
            val display = "⊙".repeat(hours)
            
            display shouldBe "⊙⊙⊙⊙"
        }
    }
    
    context("P1: Month Navigation") {
        
        test("Should navigate to next month") {
            val current = YearMonth.of(2025, 11)
            val next = current.plusMonths(1)
            
            next.year shouldBe 2025
            next.monthValue shouldBe 12
        }
        
        test("Should navigate to previous month") {
            val current = YearMonth.of(2025, 12)
            val previous = current.minusMonths(1)
            
            previous.year shouldBe 2025
            previous.monthValue shouldBe 11
        }
        
        test("Should handle return to current month") {
            val today = LocalDate.now()
            val currentMonth = YearMonth.from(today)
            
            currentMonth.year shouldBe today.year
            currentMonth.monthValue shouldBe today.monthValue
        }
        
        test("Should navigate multiple months forward") {
            val start = YearMonth.of(2025, 1)
            val end = start.plusMonths(6)
            
            end.year shouldBe 2025
            end.monthValue shouldBe 7
        }
        
        test("Should navigate multiple months backward") {
            val start = YearMonth.of(2025, 12)
            val end = start.minusMonths(6)
            
            end.year shouldBe 2025
            end.monthValue shouldBe 6
        }
    }
})
