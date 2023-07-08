package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var shoppingList: RecyclerView
private var ingredientArray = listOf<String>()
private lateinit var intentString: String


class ShoppingListActivity : ComponentActivity(){
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
            listItemClicked(selectedItem, ingredientList)
        }
    }

    private fun listItemClicked(ingredient : String, ingredientList : List<String>){
        var mutableIngredientList = ingredientList.toMutableList()
        mutableIngredientList.remove(ingredient)
        initRecyclerView(mutableIngredientList)
    }

    private fun prepareTheArray(ingredientString: String): List<String>{
        var newIngredientString = ingredientString.dropLast(1)
        var modifiedArray = newIngredientString.split("-")
        Log.i("mytag", modifiedArray.toString())
        var mutableModifiedArray = modifiedArray.toMutableList()
        mutableModifiedArray.sort()
        modifiedArray = mutableModifiedArray
        var tempArray = mutableListOf<String>()
        var x = 1
        for (i in modifiedArray.indices){
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
        tempArray = tempArray.filter {it.isNotEmpty()} as MutableList<String>
        return tempArray
    }
}