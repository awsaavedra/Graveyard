package com.productivitytracker

import com.productivitytracker.repository.EntryRepository
import com.productivitytracker.ui.DeepWorkTracker

fun main() {
    DeepWorkTracker(EntryRepository()).start()
}
