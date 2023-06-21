package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init UI-elements

        val settingsButton = findViewById<Button>(R.id.btnSettings)
        val newShoppingListButton = findViewById<Button>(R.id.btnCreateNewList)
        val recipesMenuButton = findViewById<Button>(R.id.btnRecipes)
        //Init buttons

        recipesMenuButton.setOnClickListener{
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsAcitivity::class.java)
            startActivity(intent)
        }

        newShoppingListButton.setOnClickListener{
            val intent = Intent(this, CreateShoppingListActivity::class.java)
            startActivity(intent)
        }
    }
}