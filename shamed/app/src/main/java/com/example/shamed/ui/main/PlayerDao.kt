package com.example.shamed.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.room.Query

interface PlayerDao {
    @Query("SELECT player FROM player_table ORDER BY player ASC")
    fun getAllPlayers(): MutableLiveData<String>


    fun getWeight(): MutableLiveData<Int>
    fun getHeight(): MutableLiveData<Int>
    fun userName(): MutableLiveData<String>
    fun insert(player: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: queries for getting height, weight, and everything from that player we pulled


}
