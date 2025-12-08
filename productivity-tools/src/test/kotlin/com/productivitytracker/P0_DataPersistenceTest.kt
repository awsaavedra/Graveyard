package com.productivitytracker

import com.productivitytracker.models.DailyEntry
import com.productivitytracker.repository.EntryRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

class P0_DataPersistenceTest : FunSpec({
    
    lateinit var repository: EntryRepository
    
    beforeTest {
        // Use default repository (no custom path needed)
        repository = EntryRepository()
    }
    
    // Note: Using shared database for tests
    // In production, consider isolating test data
    
    context("P0: Save Operations") {
        
        test("Should save hours for a specific day") {
            val date = LocalDate.of(2025, 12, 7)
            val entry = DailyEntry(date.toString(), 3)
            
            repository.updateEntry(entry)
            val retrieved = repository.getEntry(date)
            
            retrieved shouldNotBe null
            retrieved?.hoursLogged shouldBe 3
        }
        
        test("Should update existing hours") {
            val date = LocalDate.of(2025, 12, 7)
            
            // Initial save
            repository.updateEntry(DailyEntry(date.toString(), 2))
            repository.getEntry(date)?.hoursLogged shouldBe 2
            
            // Update
            repository.updateEntry(DailyEntry(date.toString(), 4))
            repository.getEntry(date)?.hoursLogged shouldBe 4
        }
        
        test("Should save zero hours (clear)") {
            val date = LocalDate.of(2025, 12, 7)
            
            // Set initial hours
            repository.updateEntry(DailyEntry(date.toString(), 3))
            repository.getEntry(date)?.hoursLogged shouldBe 3
            
            // Clear to 0
            repository.updateEntry(DailyEntry(date.toString(), 0))
            repository.getEntry(date)?.hoursLogged shouldBe 0
        }
        
        test("Should handle multiple days independently") {
            val day1 = LocalDate.of(2025, 12, 1)
            val day2 = LocalDate.of(2025, 12, 15)
            val day3 = LocalDate.of(2025, 12, 30)
            
            repository.updateEntry(DailyEntry(day1.toString(), 1))
            repository.updateEntry(DailyEntry(day2.toString(), 3))
            repository.updateEntry(DailyEntry(day3.toString(), 4))
            
            repository.getEntry(day1)?.hoursLogged shouldBe 1
            repository.getEntry(day2)?.hoursLogged shouldBe 3
            repository.getEntry(day3)?.hoursLogged shouldBe 4
        }
    }
    
    context("P0: Load Operations") {
        
        test("Should load existing hours for a day") {
            val date = LocalDate.of(2025, 12, 15)
            repository.updateEntry(DailyEntry(date.toString(), 3))
            
            val entry = repository.getEntry(date)
            entry?.hoursLogged shouldBe 3
        }
        
        test("Should return null for non-existent day") {
            val date = LocalDate.of(2025, 12, 20)
            
            val entry = repository.getEntry(date)
            entry shouldBe null
        }
        
        test("Should load hours across different months") {
            val nov15 = LocalDate.of(2025, 11, 15)
            val dec15 = LocalDate.of(2025, 12, 15)
            
            repository.updateEntry(DailyEntry(nov15.toString(), 2))
            repository.updateEntry(DailyEntry(dec15.toString(), 4))
            
            repository.getEntry(nov15)?.hoursLogged shouldBe 2
            repository.getEntry(dec15)?.hoursLogged shouldBe 4
        }
        
        test("Should handle rapid consecutive saves") {
            val date = LocalDate.of(2025, 12, 7)
            
            for (hours in 0..4) {
                repository.updateEntry(DailyEntry(date.toString(), hours))
                repository.getEntry(date)?.hoursLogged shouldBe hours
            }
        }
    }
})
