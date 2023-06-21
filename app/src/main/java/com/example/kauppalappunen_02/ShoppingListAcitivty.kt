package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

//private lateinit var exitButton: Button
private lateinit var shoppingList: RecyclerView
private var ingredientArray = listOf<String>()
private lateinit var intentString: String


class ShoppingListAcitivty : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        shoppingList = findViewById(R.id.rvShoppingList)
        val exitButton = findViewById<Button>(R.id.btnExit)
        intentString = intent.getStringExtra("SHOPPINGLIST").toString()

        ingredientArray = prepareTheArray(intentString)

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

    private fun prepareTheArray(ingredientString: String): List<String>{
        var modifiedArray = ingredientString.split("-")
        var mutableModifiedArray = modifiedArray.toMutableList()
        mutableModifiedArray.sort()
        modifiedArray = mutableModifiedArray
        var tempArray = mutableListOf<String>()
        Log.i("mytag","1 " + tempArray.toString())
        var x = 1
        for (i in modifiedArray.indices){
            Log.i("mytag",tempArray.toString())
            if (tempArray.contains(modifiedArray[i])){
                x += 1
            }else{
                if(x > 1){
                    tempArray[tempArray.lastIndex] = tempArray[tempArray.lastIndex] + " x" + x
                    x = 1
                }
                tempArray.add(modifiedArray[i])
            }
        }
        Log.i("mytag",tempArray.toString())
        return tempArray
    }
}