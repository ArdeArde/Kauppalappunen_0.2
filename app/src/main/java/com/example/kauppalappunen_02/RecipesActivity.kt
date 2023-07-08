package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kauppalappunen_02.db.Recipe
import com.example.kauppalappunen_02.db.RecipeDao
import com.example.kauppalappunen_02.db.RecipeDatabase
import com.example.kauppalappunen_02.vm.RecipeViewModel
import com.example.kauppalappunen_02.vm.RecipeViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipesActivity : ComponentActivity(){

    private lateinit var recipeMenu: RecyclerView
    private lateinit var recipeClickedMenu: RecyclerView
    private var recipeList = listOf<Recipe>(
        Recipe(0, "Väärin", "Meni")
    )
    private lateinit var viewModel: RecipeViewModel
    private lateinit var cardView: CardView
    private lateinit var cardText: TextView
    private lateinit var cardExitButton: Button
    private lateinit var cardConfirmButton: Button
    private lateinit var scope: CoroutineScope
    private lateinit var dao: RecipeDao
    private lateinit var factory: RecipeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        recipeMenu = findViewById<RecyclerView>(R.id.rvRecipeMenu)
        recipeClickedMenu = findViewById(R.id.rvRecipeMenuItemClicked)
        val exitButton = findViewById<Button>(R.id.btnExit)
        val newRecipeButton = findViewById<Button>(R.id.btnAddNewRecipe)

        dao = RecipeDatabase.getInstance(application).RecipeDao()
        factory = RecipeViewModelFactory(dao)
        viewModel = ViewModelProvider(this,factory).get(RecipeViewModel::class.java)
        scope = CoroutineScope(Dispatchers.Main)

        //Cardview Things

        cardView = findViewById<CardView>(R.id.cvWindow)
        cardText = findViewById<TextView>(R.id.tvCardText)
        cardExitButton = findViewById<Button>(R.id.btnCardNegative)
        cardConfirmButton = findViewById<Button>(R.id.btnCardPositive)

        //Cardview

        scope.launch{
            recipeList = dao.getAllRecipes()
            initRecyclerView(recipeList)
        }

        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        newRecipeButton.setOnClickListener{
            val intent = Intent(this, CreateRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(recipeList : List<Recipe>){
        recipeMenu.layoutManager = LinearLayoutManager(this)
        recipeMenu.adapter = RecipeRecyclerViewAdapter(recipeList){selectedItem: Recipe ->
            listItemClicked(selectedItem)
        }
    }

    private fun initRecyclerViewIngredients(ingredientList : List<String>){
        recipeClickedMenu.layoutManager = LinearLayoutManager(this)
        recipeClickedMenu.adapter = IngredientRecyclerViewAdapter(ingredientList){selectedItem: String ->
        }
    }
    private fun listItemClicked(recipe : Recipe){
        var ingredientList = recipe.ingredients.split("-")
        initRecyclerViewIngredients(ingredientList)
        cardView.visibility = VISIBLE
        cardText.text = "Haluatko poistaa reseptin"

        cardExitButton.setOnClickListener{
            cardView.visibility = INVISIBLE
        }

        cardConfirmButton.setOnClickListener{
            scope.launch{
                dao.deleteRecipe(recipe)
                recipeList = dao.getAllRecipes()
                cardView.visibility = INVISIBLE
                initRecyclerView(recipeList)
            }
        }
    }
}