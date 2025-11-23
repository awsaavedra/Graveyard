package com.productivitytracker.repository

import com.productivitytracker.models.DailyEntry
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDate

class DatabaseInitializer(private val dbPath: String = "~/.productivity-tracker/deep-work.db".replace("~", System.getProperty("user.home"))) {
    private lateinit var connection: Connection

    fun initialize() {
        val dbFile = java.io.File(dbPath)
        dbFile.parentFile?.mkdirs()
        connection = DriverManager.getConnection("jdbc:sqlite:$dbPath")
        createTables()
    }

    private fun createTables() {
        connection.createStatement().use { statement ->
            statement.execute("""
                CREATE TABLE IF NOT EXISTS daily_entries (
                    date TEXT PRIMARY KEY,
                    hours_logged INTEGER NOT NULL
                )
            """.trimIndent())
        }
    }

    fun getConnection(): Connection = connection
}

class EntryRepository(private val connection: Connection) {
    
    fun getEntry(date: LocalDate): DailyEntry? {
        val query = "SELECT * FROM daily_entries WHERE date = ?"
        connection.prepareStatement(query).use { statement ->
            statement.setString(1, date.toString())
            statement.executeQuery().use { rs ->
                return if (rs.next()) {
                    DailyEntry(
                        date = rs.getString("date"),
                        hoursLogged = rs.getInt("hours_logged")
                    )
                } else {
                    null
                }
            }
        }
    }

    fun updateEntry(entry: DailyEntry) {
        val existingEntry = getEntry(LocalDate.parse(entry.date))
        
        if (existingEntry != null) {
            val query = "UPDATE daily_entries SET hours_logged = ? WHERE date = ?"
            connection.prepareStatement(query).use { statement ->
                statement.setInt(1, entry.hoursLogged)
                statement.setString(2, entry.date)
                statement.executeUpdate()
            }
        } else {
            val query = "INSERT INTO daily_entries (date, hours_logged) VALUES (?, ?)"
            connection.prepareStatement(query).use { statement ->
                statement.setString(1, entry.date)
                statement.setInt(2, entry.hoursLogged)
                statement.executeUpdate()
            }
        }
    }

    fun getAllEntries(): List<DailyEntry> {
        val entries = mutableListOf<DailyEntry>()
        val query = "SELECT * FROM daily_entries ORDER BY date DESC"
        connection.createStatement().use { statement ->
            statement.executeQuery(query).use { rs ->
                while (rs.next()) {
                    entries.add(
                        DailyEntry(
                            date = rs.getString("date"),
                            hoursLogged = rs.getInt("hours_logged")
                        )
                    )
                }
            }
        }
        return entries
    }
}
