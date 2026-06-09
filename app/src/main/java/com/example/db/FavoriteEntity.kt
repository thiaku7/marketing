package com.example.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cards")
data class FavoriteEntity(
    @PrimaryKey val cardId: String,
    val timestamp: Long = System.currentTimeMillis()
)
