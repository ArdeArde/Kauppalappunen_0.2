package com.example.kauppalappunen_02

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AbsListView.RecyclerListener
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kauppalappunen_02.db.RecipeDatabase
import com.example.kauppalappunen_02.viewmodel.RecipeRecyclerViewAdapter
import com.example.kauppalappunen_02.viewmodel.RecipeViewModel
import com.example.kauppalappunen_02.viewmodel.RecipeViewModelFactory

class RecipesActivity : ComponentActivity(){

    private lateinit var recipeMenu: RecyclerView
    private lateinit var viewModel: RecipeViewModel
    private lateinit var adapter: RecipeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        recipeMenu = findViewById<RecyclerView>(R.id.rvRecipeMenu)
        val exitButton = findViewById<Button>(R.id.btnExit)
        val newRecipeButton = findViewById<Button>(R.id.btnAddNewRecipe)

        val recipeDao = RecipeDatabase.getInstance(application).recipeDao()
        val factory = RecipeViewModelFactory(recipeDao)
        viewModel = ViewModelProvider(this,factory).get(RecipeViewModel::class.java)

        initRecyclerView()

        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        newRecipeButton.setOnClickListener{
            val intent = Intent(this, CreateRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(){
        recipeMenu.layoutManager = LinearLayoutManager(this)
        adapter = RecipeRecyclerViewAdapter()
        recipeMenu.adapter = adapter
        displayRecipeList()
    }

    private fun displayRecipeList(){
        viewModel.allRecipes.observe(this){
            Log.i("mytag", "got here! it is: $it")
                adapter.setList(it)
                adapter.notifyDataSetChanged()
        }
    }
}