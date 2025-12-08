package com.productivitytracker

import com.productivitytracker.models.DailyEntry
import com.productivitytracker.repository.EntryRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File
import java.time.LocalDate

class P1_DataIntegrityTest : FunSpec({
    
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
    
    context("P1: Update Operations") {
        
        test("Should handle updating same day multiple times in sequence") {
            val date = LocalDate.of(2025, 12, 7)
            
            repository.updateEntry(DailyEntry(date.toString(), 1))
            repository.getEntry(date)?.hoursLogged shouldBe 1
            
            repository.updateEntry(DailyEntry(date.toString(), 2))
            repository.getEntry(date)?.hoursLogged shouldBe 2
            
            repository.updateEntry(DailyEntry(date.toString(), 3))
            repository.getEntry(date)?.hoursLogged shouldBe 3
        }
        
        test("Should preserve other days when updating one day") {
            val day1 = LocalDate.of(2025, 12, 1)
            val day2 = LocalDate.of(2025, 12, 15)
            
            repository.updateEntry(DailyEntry(day1.toString(), 2))
            repository.updateEntry(DailyEntry(day2.toString(), 3))
            
            // Update day1, verify day2 unchanged
            repository.updateEntry(DailyEntry(day1.toString(), 4))
            
            repository.getEntry(day1)?.hoursLogged shouldBe 4
            repository.getEntry(day2)?.hoursLogged shouldBe 3
        }
        
        test("Should handle clearing hours back to zero") {
            val date = LocalDate.of(2025, 12, 7)
            
            // Set hours
            repository.updateEntry(DailyEntry(date.toString(), 4))
            repository.getEntry(date)?.hoursLogged shouldBe 4
            
            // Clear
            repository.updateEntry(DailyEntry(date.toString(), 0))
            repository.getEntry(date)?.hoursLogged shouldBe 0
            
            // Set again
            repository.updateEntry(DailyEntry(date.toString(), 2))
            repository.getEntry(date)?.hoursLogged shouldBe 2
        }
    }
    
    context("P1: Cross-Month Data Integrity") {
        
        test("Should maintain data when querying different months") {
            val nov15 = LocalDate.of(2025, 11, 15)
            val dec15 = LocalDate.of(2025, 12, 15)
            val jan15 = LocalDate.of(2026, 1, 15)
            
            repository.updateEntry(DailyEntry(nov15.toString(), 2))
            repository.updateEntry(DailyEntry(dec15.toString(), 3))
            repository.updateEntry(DailyEntry(jan15.toString(), 4))
            
            // Query in different order
            repository.getEntry(dec15)?.hoursLogged shouldBe 3
            repository.getEntry(nov15)?.hoursLogged shouldBe 2
            repository.getEntry(jan15)?.hoursLogged shouldBe 4
        }
        
        test("Should handle same day number in different months") {
            val nov7 = LocalDate.of(2025, 11, 7)
            val dec7 = LocalDate.of(2025, 12, 7)
            val jan7 = LocalDate.of(2026, 1, 7)
            
            repository.updateEntry(DailyEntry(nov7.toString(), 1))
            repository.updateEntry(DailyEntry(dec7.toString(), 2))
            repository.updateEntry(DailyEntry(jan7.toString(), 3))
            
            repository.getEntry(nov7)?.hoursLogged shouldBe 1
            repository.getEntry(dec7)?.hoursLogged shouldBe 2
            repository.getEntry(jan7)?.hoursLogged shouldBe 3
        }
        
        test("Should handle querying non-existent days in different months") {
            val nov10 = LocalDate.of(2025, 11, 10)
            val dec10 = LocalDate.of(2025, 12, 10)
            
            repository.getEntry(nov10) shouldBe null
            repository.getEntry(dec10) shouldBe null
        }
    }
    
    context("P1: Data Consistency") {
        
        test("Should maintain correct date format in database") {
            val date = LocalDate.of(2025, 12, 7)
            val entry = DailyEntry(date.toString(), 3)
            
            repository.updateEntry(entry)
            val retrieved = repository.getEntry(date)
            
            retrieved?.date shouldBe "2025-12-07"
        }
        
        test("Should handle maximum valid hours (4)") {
            val date = LocalDate.of(2025, 12, 7)
            
            repository.updateEntry(DailyEntry(date.toString(), 4))
            repository.getEntry(date)?.hoursLogged shouldBe 4
        }
        
        test("Should handle minimum valid hours (0)") {
            val date = LocalDate.of(2025, 12, 7)
            
            repository.updateEntry(DailyEntry(date.toString(), 0))
            repository.getEntry(date)?.hoursLogged shouldBe 0
        }
        
        test("Should handle rapid alternating updates") {
            val date = LocalDate.of(2025, 12, 7)
            
            for (i in 1..10) {
                val hours = if (i % 2 == 0) 4 else 0
                repository.updateEntry(DailyEntry(date.toString(), hours))
                repository.getEntry(date)?.hoursLogged shouldBe hours
            }
        }
    }
    
    context("P1: Query Patterns") {
        
        test("Should handle querying past dates") {
            val pastDate = LocalDate.of(2020, 1, 1)
            repository.updateEntry(DailyEntry(pastDate.toString(), 2))
            
            repository.getEntry(pastDate)?.hoursLogged shouldBe 2
        }
        
        test("Should handle querying future dates") {
            val futureDate = LocalDate.of(2030, 12, 31)
            repository.updateEntry(DailyEntry(futureDate.toString(), 3))
            
            repository.getEntry(futureDate)?.hoursLogged shouldBe 3
        }
        
        test("Should handle today's date") {
            val today = LocalDate.now()
            repository.updateEntry(DailyEntry(today.toString(), 4))
            
            repository.getEntry(today)?.hoursLogged shouldBe 4
        }
        
        test("Should handle yesterday's date") {
            val yesterday = LocalDate.now().minusDays(1)
            repository.updateEntry(DailyEntry(yesterday.toString(), 2))
            
            repository.getEntry(yesterday)?.hoursLogged shouldBe 2
        }
        
        test("Should handle tomorrow's date") {
            val tomorrow = LocalDate.now().plusDays(1)
            repository.updateEntry(DailyEntry(tomorrow.toString(), 1))
            
            repository.getEntry(tomorrow)?.hoursLogged shouldBe 1
        }
    }
})
