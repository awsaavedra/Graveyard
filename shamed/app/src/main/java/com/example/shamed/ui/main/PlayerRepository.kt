package com.example.shamed.ui.main

import androidx.lifecycle.LiveData

// What is Dao? What does a data access object do?
// Ans: We only need access to the DAO instead of the database. See:
// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#7
class PlayerRepository(private val playerDao: PlayerDao) {

    val player: LiveData<Player> = playerDao.getPlayer()

    // Why are we using suspend function here?
    suspend fun insert(player: Player){
        playerDao.insert(player)
    }


}
