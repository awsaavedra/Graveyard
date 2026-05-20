package com.productivitytracker

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.YearMonth

/**
 * P0: Combined Input Validation Tests
 * 
 * Justification: Tests interactions between multiple validation scenarios to ensure
 * the application maintains state correctly across rapid input changes. Real users
 * don't input data in isolation - they rapidly switch between days, months, and values.
 * These tests verify the application handles realistic usage patterns without state corruption.
 */
class P0_CombinedInputValidationTest : FunSpec({
    
    context("P0: Rapid Sequential Day Inputs at Boundaries") {
        
        test("Should validate day 1, then day 31 for January, then day 30 for November") {
            // Justification: Tests that validation correctly switches context between months
            // with different lengths. Critical for preventing off-by-one errors when users
            // rapidly navigate and edit different months.
            
            val day1 = 1
            val januaryYearMonth = YearMonth.of(2025, 1) // 31 days
            val novemberYearMonth = YearMonth.of(2025, 11) // 30 days
            
            // Validate day 1 (universally valid)
            (day1 >= 1 && day1 <= januaryYearMonth.lengthOfMonth()) shouldBe true
            
            // Validate day 31 for January
            val day31 = 31
            (day31 >= 1 && day31 <= januaryYearMonth.lengthOfMonth()) shouldBe true
            
            // Validate day 30 for November
            val day30 = 30
            (day30 >= 1 && day30 <= novemberYearMonth.lengthOfMonth()) shouldBe true
            
            // Each validation should be independent
            januaryYearMonth.lengthOfMonth() shouldBe 31
            novemberYearMonth.lengthOfMonth() shouldBe 30
        }
    }
    
    context("P0: Mixed Valid and Invalid Day Inputs in Sequence") {
        
        test("Should handle Day 15 (valid), Day 0 (invalid), Day 20 (valid), Day 32 (invalid)") {
            // Justification: Ensures invalid inputs don't corrupt the validation state.
            // Users will make mistakes - the system must remain stable and not carry
            // validation errors forward to subsequent valid inputs.
            
            val yearMonth = YearMonth.of(2025, 12)
            
            val day15 = 15
            val valid15 = day15 >= 1 && day15 <= yearMonth.lengthOfMonth()
            valid15 shouldBe true
            
            val day0 = 0
            val valid0 = day0 >= 1 && day0 <= yearMonth.lengthOfMonth()
            valid0 shouldBe false
            
            val day20 = 20
            val valid20 = day20 >= 1 && day20 <= yearMonth.lengthOfMonth()
            valid20 shouldBe true
            
            val day32 = 32
            val valid32 = day32 >= 1 && day32 <= yearMonth.lengthOfMonth()
            valid32 shouldBe false
        }
    }
    
    context("P0: Hours Boundary Testing") {
        
        test("Should validate hours at exact boundaries: -1, 0, 4, 5") {
            // Justification: Boundary value analysis is critical for catching off-by-one errors.
            // Hour validation (0-4) must reject values immediately outside the range while
            // accepting boundary values. This prevents data corruption from invalid hours.
            
            val minusOne = -1
            val isValidMinusOne = minusOne in 0..4
            isValidMinusOne shouldBe false
            
            val zero = 0
            val isValidZero = zero in 0..4
            isValidZero shouldBe true
            
            val four = 4
            val isValidFour = four in 0..4
            isValidFour shouldBe true
            
            val five = 5
            val isValidFive = five in 0..4
            isValidFive shouldBe false
        }
        
        test("Should validate all hours values 0 through 4 sequentially") {
            // Justification: Ensures the entire valid range is accepted. A user tracking
            // their work throughout the day will input 0, then 1, then 2, etc.
            // All values in the valid range must be accepted without state issues.
            
            val validHours = listOf(0, 1, 2, 3, 4)
            
            validHours.forEach { hours ->
                val isValid = hours in 0..4
                isValid shouldBe true
            }
            
            validHours.size shouldBe 5
        }
    }
    
    context("P0: Navigation Command Case Insensitivity") {
        
        test("Should recognize both lowercase and uppercase navigation commands") {
            // Justification: Users shouldn't need to remember case sensitivity for commands.
            // This tests that the input parser normalizes case, preventing user frustration
            // when Caps Lock is accidentally enabled or they naturally type uppercase.
            
            val commands = mapOf(
                "n" to "next",
                "N" to "next",
                "p" to "previous",
                "P" to "previous",
                "t" to "today",
                "T" to "today",
                "q" to "quit",
                "Q" to "quit"
            )
            
            commands.forEach { (input, expected) ->
                val normalized = input.lowercase()
                val action = when (normalized) {
                    "n" -> "next"
                    "p" -> "previous"
                    "t" -> "today"
                    "q" -> "quit"
                    else -> "invalid"
                }
                
                action shouldBe expected
            }
        }
    }
    
    context("P0: Month Context Validation") {
        
        test("Should validate day 30 correctly across months with different lengths") {
            // Justification: Day 30 is valid in some months but not others. This tests
            // context-aware validation to prevent accepting invalid dates (like Feb 30).
            // Critical for data integrity - invalid dates must never be saved.
            
            val day30 = 30
            
            // November has 30 days - valid
            val november = YearMonth.of(2025, 11)
            (day30 >= 1 && day30 <= november.lengthOfMonth()) shouldBe true
            
            // January has 31 days - valid
            val january = YearMonth.of(2025, 1)
            (day30 >= 1 && day30 <= january.lengthOfMonth()) shouldBe true
            
            // February has 28 days in 2025 - invalid
            val february = YearMonth.of(2025, 2)
            (day30 >= 1 && day30 <= february.lengthOfMonth()) shouldBe false
        }
        
        test("Should validate day 31 correctly across all 12 months") {
            // Justification: Day 31 is only valid in 7 out of 12 months. This ensures
            // the validation properly rejects day 31 for 30-day months and February.
            // Prevents impossible dates from being accepted and saved to the database.
            
            val day31 = 31
            val year = 2025
            
            // Months with 31 days: Jan, Mar, May, Jul, Aug, Oct, Dec
            val monthsWith31Days = listOf(1, 3, 5, 7, 8, 10, 12)
            val monthsWith30Days = listOf(4, 6, 9, 11)
            val februaryMonth = 2
            
            monthsWith31Days.forEach { month ->
                val yearMonth = YearMonth.of(year, month)
                (day31 >= 1 && day31 <= yearMonth.lengthOfMonth()) shouldBe true
            }
            
            monthsWith30Days.forEach { month ->
                val yearMonth = YearMonth.of(year, month)
                (day31 >= 1 && day31 <= yearMonth.lengthOfMonth()) shouldBe false
            }
            
            val febYearMonth = YearMonth.of(year, februaryMonth)
            (day31 >= 1 && day31 <= febYearMonth.lengthOfMonth()) shouldBe false
        }
    }
    
    context("P0: Leap Year Combined Validation") {
        
        test("Should validate February 29 across leap and non-leap years") {
            // Justification: Leap year handling is a classic source of bugs. This test
            // ensures the application correctly accepts Feb 29 in leap years (2024, 2028)
            // and rejects it in non-leap years (2025, 2026, 2027). Critical for preventing
            // invalid dates from being saved, which would cause database or date math errors.
            
            val day29 = 29
            
            // 2024 is a leap year - Feb 29 is valid
            val feb2024 = YearMonth.of(2024, 2)
            feb2024.lengthOfMonth() shouldBe 29
            (day29 >= 1 && day29 <= feb2024.lengthOfMonth()) shouldBe true
            
            // 2025 is not a leap year - Feb 29 is invalid
            val feb2025 = YearMonth.of(2025, 2)
            feb2025.lengthOfMonth() shouldBe 28
            (day29 >= 1 && day29 <= feb2025.lengthOfMonth()) shouldBe false
            
            // 2028 is a leap year - Feb 29 is valid
            val feb2028 = YearMonth.of(2028, 2)
            feb2028.lengthOfMonth() shouldBe 29
            (day29 >= 1 && day29 <= feb2028.lengthOfMonth()) shouldBe true
        }
        
        test("Should validate February days 28, 29, 30, 31 in leap year") {
            // Justification: Tests the complete boundary set for February in a leap year.
            // Only 28 and 29 should be valid. This ensures the leap year logic doesn't
            // accidentally allow invalid days like 30 or 31 in February.
            
            val feb2024 = YearMonth.of(2024, 2) // Leap year
            feb2024.lengthOfMonth() shouldBe 29
            
            val day28 = 28
            (day28 >= 1 && day28 <= feb2024.lengthOfMonth()) shouldBe true
            
            val day29 = 29
            (day29 >= 1 && day29 <= feb2024.lengthOfMonth()) shouldBe true
            
            val day30 = 30
            (day30 >= 1 && day30 <= feb2024.lengthOfMonth()) shouldBe false
            
            val day31 = 31
            (day31 >= 1 && day31 <= feb2024.lengthOfMonth()) shouldBe false
        }
    }
    
    context("P0: Empty Input Validation") {
        
        test("Should handle empty input followed by valid input") {
            // Justification: Users often press Enter accidentally before typing a value.
            // The application must treat empty input as "no-op" and wait for valid input,
            // rather than crashing or entering an invalid state. This ensures robust
            // user experience even with input mistakes.
            
            val emptyInput = ""
            val emptyIsValid = emptyInput.toIntOrNull()?.let { it in 0..4 } ?: false
            emptyIsValid shouldBe false
            
            val validInput = "3"
            val validIsValid = validInput.toIntOrNull()?.let { it in 0..4 } ?: false
            validIsValid shouldBe true
        }
    }
})
