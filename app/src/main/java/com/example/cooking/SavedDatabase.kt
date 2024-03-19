package com.example.cooking

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedDao {

    @Query("SELECT * FROM food_category")
    suspend fun getSaveMealData(): List<SavedData?>

    @Query("SELECT * FROM food_category WHERE idCategory IN (:mealId)")
    suspend fun getSaveMealDescriptionData(mealId:String?): SavedData?
    @Insert
    suspend fun insert(entity: SavedData?)

    @Query("DELETE FROM food_category WHERE idCategory IN (:mealId)")
    suspend fun delete(mealId: String?)

}