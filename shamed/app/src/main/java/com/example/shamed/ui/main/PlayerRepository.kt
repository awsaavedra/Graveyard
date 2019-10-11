package com.example.shamed.ui.main

import androidx.lifecycle.MutableLiveData

// What is Dao? What is a data access object do?
class PlayerRepository(
    private val playerDao: PlayerDao
) {
    // TODO: create all fields and functions
    // All CRUD methods in here

    // Why are we doing it this way? Testability, architecture, etc.
    val weight: MutableLiveData<Int> = playerDao.getWeight()
    val height: MutableLiveData<Int> = playerDao.getHeight()
    val userName: MutableLiveData<String> = playerDao.userName()

    // Why are we using suspend function here?
    suspend fun insert(player: Player){
        playerDao.insert(player)
    }


}
