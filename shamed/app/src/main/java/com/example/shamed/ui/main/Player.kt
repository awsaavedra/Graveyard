package com.example.shamed.ui.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "player_table")
data class Player( @PrimaryKey @ColumnInfo(name = "player") val username:String, val height: Int,
                   val weight: Int, val gems: Int)
