package com.example.kauppalappunen_02

import android.app.Application
import androidx.room.Room
import com.example.kauppalappunen_02.db.RecipeDatabase

class MyApp : Application(){
    companion object{
        lateinit var database: RecipeDatabase
    }

    override fun onCreate(){
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe-database"
        ).build()
    }
}