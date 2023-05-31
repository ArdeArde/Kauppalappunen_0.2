package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class CreateShoppingListAcitivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shopping_list)

        val exitButton = findViewById<Button>(R.id.btnExit)
        val confirmListButton = findViewById<Button>(R.id.btnConfirmShoppingList)

        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        confirmListButton.setOnClickListener{
            val intent = Intent(this, ShoppingListAcitivty::class.java)
            startActivity(intent)
        }
    }
}