package com.example.whattoeat2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class Name(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val type:String

)


