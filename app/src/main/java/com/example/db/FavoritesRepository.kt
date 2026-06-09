package com.example.db

import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val favoriteDao: FavoriteDao) {
    val allFavorites: Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()

    suspend fun insert(cardId: String) {
        favoriteDao.insertFavorite(FavoriteEntity(cardId))
    }

    suspend fun delete(cardId: String) {
        favoriteDao.deleteFavoriteById(cardId)
    }
}
