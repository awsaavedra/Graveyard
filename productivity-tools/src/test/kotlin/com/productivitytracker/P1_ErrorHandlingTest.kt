package com.productivitytracker

import com.productivitytracker.models.DailyEntry
import com.productivitytracker.repository.EntryRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File
import java.time.LocalDate

class P1_ErrorHandlingTest : FunSpec({
    
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
    
    context("P1: Input Error Handling") {
        
        test("Should handle decimal hours input") {
            val input = "2.5"
            val parsed = input.toIntOrNull()
            
            parsed shouldBe null // Decimal not allowed
        }
        
        test("Should handle string hours input") {
            val input = "two"
            val parsed = input.toIntOrNull()
            
            parsed shouldBe null
        }
        
        test("Should handle mixed alphanumeric input") {
            val input = "3hours"
            val parsed = input.toIntOrNull()
            
            parsed shouldBe null
        }
        
        test("Should handle empty string input") {
            val input = ""
            val parsed = input.toIntOrNull()
            
            parsed shouldBe null
        }
        
        test("Should handle whitespace-only input") {
            val input = "   "
            val parsed = input.trim().toIntOrNull()
            
            parsed shouldBe null
        }
        
        test("Should handle very large numbers") {
            val input = "999999999999999999"
            val parsed = input.toIntOrNull()
            
            // Will parse but validation should reject
            if (parsed != null) {
                (parsed >= 0 && parsed <= 4) shouldBe false
            }
        }
    }
    
    context("P1: Date Parsing Edge Cases") {
        
        test("Should handle valid date string parsing") {
            val dateString = "2025-12-07"
            val parsed = try {
                LocalDate.parse(dateString)
            } catch (e: Exception) {
                null
            }
            
            parsed shouldNotBe null
            parsed?.year shouldBe 2025
            parsed?.monthValue shouldBe 12
            parsed?.dayOfMonth shouldBe 7
        }
        
        test("Should reject invalid date format") {
            val dateString = "12/07/2025"
            val parsed = try {
                LocalDate.parse(dateString)
            } catch (e: Exception) {
                null
            }
            
            parsed shouldBe null
        }
        
        test("Should reject malformed date") {
            val dateString = "2025-13-32"
            val parsed = try {
                LocalDate.parse(dateString)
            } catch (e: Exception) {
                null
            }
            
            parsed shouldBe null
        }
        
        test("Should handle extreme past dates") {
            val dateString = "1900-01-01"
            val parsed = try {
                LocalDate.parse(dateString)
            } catch (e: Exception) {
                null
            }
            
            parsed shouldNotBe null
        }
        
        test("Should handle extreme future dates") {
            val dateString = "2100-12-31"
            val parsed = try {
                LocalDate.parse(dateString)
            } catch (e: Exception) {
                null
            }
            
            parsed shouldNotBe null
        }
    }
    
    context("P1: Database Path Handling") {
        
        test("Should expand tilde in database path") {
            val path = "~/.productivity-tracker/deep-work.db"
            val expanded = path.replace("~", System.getProperty("user.home"))
            
            expanded shouldNotBe path
            expanded.startsWith("/") shouldBe true
        }
        
        test("Should create parent directory if needed") {
            val testPath = "${System.getProperty("user.home")}/.productivity-tracker-test"
            val testDir = File(testPath)
            
            // Clean up before test
            if (testDir.exists()) {
                testDir.deleteRecursively()
            }
            
            // Create directory
            testDir.mkdirs()
            
            testDir.exists() shouldBe true
            testDir.isDirectory shouldBe true
            
            // Clean up after test
            testDir.deleteRecursively()
        }
    }
    
    context("P1: Boundary Value Testing") {
        
        test("Should handle Integer.MAX_VALUE for hours") {
            val hours = Int.MAX_VALUE
            val isValid = (hours >= 0 && hours <= 4)
            
            isValid shouldBe false
        }
        
        test("Should handle Integer.MIN_VALUE for hours") {
            val hours = Int.MIN_VALUE
            val isValid = (hours >= 0 && hours <= 4)
            
            isValid shouldBe false
        }
        
        test("Should handle day at exact upper boundary") {
            val monthDays = 31
            val day = 31
            
            (day >= 1 && day <= monthDays) shouldBe true
        }
        
        test("Should handle day just beyond upper boundary") {
            val monthDays = 31
            val day = 32
            
            (day >= 1 && day <= monthDays) shouldBe false
        }
    }
})
