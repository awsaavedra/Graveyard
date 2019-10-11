package com.example.shamed.ui.main

import androidx.room.RoomDatabase

abstract class PlayerRoomDatabase: RoomDatabase() {

    abstract fun getDatabase(): PlayerDao
}
