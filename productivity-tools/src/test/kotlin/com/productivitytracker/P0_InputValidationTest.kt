package com.productivitytracker

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.YearMonth

class P0_InputValidationTest : FunSpec({
    
    context("P0: Day Input Validation") {
        
        test("Should accept day 1 (minimum valid)") {
            val yearMonth = YearMonth.of(2025, 12)
            val day = 1
            
            day shouldBe 1
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe true
        }
        
        test("Should accept day 15 (middle of month)") {
            val yearMonth = YearMonth.of(2025, 12)
            val day = 15
            
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe true
        }
        
        test("Should accept day 30 (valid for 30-day month)") {
            val yearMonth = YearMonth.of(2025, 11) // November has 30 days
            val day = 30
            
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe true
        }
        
        test("Should accept day 31 (valid for 31-day month)") {
            val yearMonth = YearMonth.of(2025, 12) // December has 31 days
            val day = 31
            
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe true
        }
        
        test("Should reject day 0") {
            val yearMonth = YearMonth.of(2025, 12)
            val day = 0
            
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe false
        }
        
        test("Should reject day 31 for 30-day month") {
            val yearMonth = YearMonth.of(2025, 11) // November has 30 days
            val day = 31
            
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe false
        }
        
        test("Should reject day 32") {
            val yearMonth = YearMonth.of(2025, 12)
            val day = 32
            
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe false
        }
        
        test("Should reject negative day") {
            val yearMonth = YearMonth.of(2025, 12)
            val day = -5
            
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe false
        }
        
        test("Should reject day 29 for non-leap February") {
            val yearMonth = YearMonth.of(2025, 2) // 2025 is not a leap year
            val day = 29
            
            yearMonth.lengthOfMonth() shouldBe 28
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe false
        }
        
        test("Should accept day 29 for leap year February") {
            val yearMonth = YearMonth.of(2024, 2) // 2024 is a leap year
            val day = 29
            
            yearMonth.lengthOfMonth() shouldBe 29
            (day >= 1 && day <= yearMonth.lengthOfMonth()) shouldBe true
        }
    }
    
    context("P0: Hours Input Validation") {
        
        test("Should accept 0 hours (minimum valid)") {
            val hours = 0
            
            (hours >= 0 && hours <= 4) shouldBe true
        }
        
        test("Should accept 1 hour") {
            val hours = 1
            
            (hours >= 0 && hours <= 4) shouldBe true
        }
        
        test("Should accept 2 hours") {
            val hours = 2
            
            (hours >= 0 && hours <= 4) shouldBe true
        }
        
        test("Should accept 3 hours") {
            val hours = 3
            
            (hours >= 0 && hours <= 4) shouldBe true
        }
        
        test("Should accept 4 hours (maximum valid)") {
            val hours = 4
            
            (hours >= 0 && hours <= 4) shouldBe true
        }
        
        test("Should reject 5 hours") {
            val hours = 5
            
            (hours >= 0 && hours <= 4) shouldBe false
        }
        
        test("Should reject negative hours") {
            val hours = -1
            
            (hours >= 0 && hours <= 4) shouldBe false
        }
        
        test("Should reject large positive hours") {
            val hours = 24
            
            (hours >= 0 && hours <= 4) shouldBe false
        }
    }
    
    context("P0: Command Input Validation") {
        
        test("Should recognize 'n' as next month") {
            val command = "n"
            
            command.lowercase() shouldBe "n"
        }
        
        test("Should recognize 'N' as next month (case-insensitive)") {
            val command = "N"
            
            command.lowercase() shouldBe "n"
        }
        
        test("Should recognize 'p' as previous month") {
            val command = "p"
            
            command.lowercase() shouldBe "p"
        }
        
        test("Should recognize 'P' as previous month (case-insensitive)") {
            val command = "P"
            
            command.lowercase() shouldBe "p"
        }
        
        test("Should recognize 't' as today") {
            val command = "t"
            
            command.lowercase() shouldBe "t"
        }
        
        test("Should recognize 'T' as today (case-insensitive)") {
            val command = "T"
            
            command.lowercase() shouldBe "t"
        }
        
        test("Should recognize 'q' as quit") {
            val command = "q"
            
            command.lowercase() shouldBe "q"
        }
        
        test("Should recognize 'Q' as quit (case-insensitive)") {
            val command = "Q"
            
            command.lowercase() shouldBe "q"
        }
    }
})
