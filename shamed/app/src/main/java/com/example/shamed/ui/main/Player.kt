package com.example.shamed.ui.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "player_table")
class Player( @PrimaryKey @ColumnInfo(name = "player") val word:String ) {
}
