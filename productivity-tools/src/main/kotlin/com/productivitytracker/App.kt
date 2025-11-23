package com.productivitytracker

import com.productivitytracker.repository.DatabaseInitializer
import com.productivitytracker.repository.EntryRepository
import com.productivitytracker.ui.DeepWorkTracker

fun main() {
    val dbInitializer = DatabaseInitializer()
    dbInitializer.initialize()
    
    val repository = EntryRepository(dbInitializer.getConnection())
    val tracker = DeepWorkTracker(repository)
    
    tracker.start()
}
