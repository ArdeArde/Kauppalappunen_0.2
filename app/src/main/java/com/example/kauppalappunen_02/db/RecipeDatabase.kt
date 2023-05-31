package com.example.kauppalappunen_02.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase(){

    abstract fun recipeDao():RecipeDao

    companion object{
        @Volatile
        private var INSTANCE : RecipeDatabase? = null
        fun getInstance(context: Context):RecipeDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "recipe_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}