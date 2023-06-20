package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var exitButton: Button
private lateinit var shoppingList: RecyclerView
private var ingredientArray = listOf<String>()
private lateinit var intentString: String


class ShoppingListAcitivty : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        shoppingList = findViewById<RecyclerView>(R.id.rvShoppingList)
        exitButton = findViewById<Button>(R.id.btnExit)
        intentString = intent.getStringExtra("SHOPPINGLIST").toString()
        ingredientArray = intentString.split("-")



        initRecyclerView(ingredientArray)


        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(ingredientList : List<String>){
        shoppingList.layoutManager = LinearLayoutManager(this)
        shoppingList.adapter = IngredientRecyclerViewAdapter(ingredientList){selectedItem: String ->
            listItemClicked(selectedItem)
        }
    }

    private fun listItemClicked(ingredient : String){
        Toast.makeText(this, "bruh", Toast.LENGTH_SHORT).show()
    }

    private fun parseStringIntoList(intentString: String): List<String>{
        return ingredientArray
    }
}