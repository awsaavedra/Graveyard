package com.productivitytracker.repository

import com.productivitytracker.models.DailyEntry
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDate

class EntryRepository(customDbPath: String? = null) {
    private val dbPath = customDbPath ?: "~/.productivity-tracker/deep-work.db".replace("~", System.getProperty("user.home"))
    private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:$dbPath").apply {
        val dbFile = java.io.File(dbPath)
        dbFile.parentFile?.mkdirs()
        createStatement().use {
            it.execute("CREATE TABLE IF NOT EXISTS daily_entries (date TEXT PRIMARY KEY, hours_logged INTEGER)")
        }
    }

    fun getEntry(date: LocalDate): DailyEntry? {
        val query = "SELECT * FROM daily_entries WHERE date = ?"
        return connection.prepareStatement(query).use { stmt ->
            stmt.setString(1, date.toString())
            stmt.executeQuery().use { rs ->
                if (rs.next()) DailyEntry(rs.getString("date"), rs.getInt("hours_logged")) else null
            }
        }
    }

    fun updateEntry(entry: DailyEntry) {
        val query = "INSERT OR REPLACE INTO daily_entries (date, hours_logged) VALUES (?, ?)"
        connection.prepareStatement(query).use { stmt ->
            stmt.setString(1, entry.date)
            stmt.setInt(2, entry.hoursLogged)
            stmt.executeUpdate()
        }
    }
    
    fun close() {
        connection.close()
    }
    
    fun deleteEntry(date: LocalDate) {
        val query = "DELETE FROM daily_entries WHERE date = ?"
        connection.prepareStatement(query).use { stmt ->
            stmt.setString(1, date.toString())
            stmt.executeUpdate()
        }
    }
}
