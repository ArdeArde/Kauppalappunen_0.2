package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
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
    private lateinit var cardViewNewRecipe: CardView
    private lateinit var newRecipeNameEditText: EditText
    private lateinit var newRecipeNameButton: Button
    private lateinit var cardText: TextView
    private lateinit var cardExitButton: Button
    private lateinit var cardConfirmButton: Button
    private lateinit var editTextNewIngredient: EditText
    private lateinit var buttonNewIngredient: Button
    private lateinit var scope: CoroutineScope
    private lateinit var dao: RecipeDao
    private lateinit var factory: RecipeViewModelFactory
    private lateinit var exitButton: Button
    private lateinit var newRecipeButton: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        recipeMenu = findViewById<RecyclerView>(R.id.rvRecipeMenu)
        recipeClickedMenu = findViewById(R.id.rvRecipeMenuItemClicked)
        exitButton = findViewById<Button>(R.id.btnExit)
        newRecipeButton = findViewById<Button>(R.id.btnAddNewRecipe)

        dao = RecipeDatabase.getInstance(application).RecipeDao()
        factory = RecipeViewModelFactory(dao)
        viewModel = ViewModelProvider(this,factory)[RecipeViewModel::class.java]
        scope = CoroutineScope(Dispatchers.Main)

        //Cardview Things

        cardView = findViewById<CardView>(R.id.cvWindow)
        cardText = findViewById<TextView>(R.id.tvCardText)
        cardExitButton = findViewById<Button>(R.id.btnCardNegative)
        cardConfirmButton = findViewById<Button>(R.id.btnCardPositive)
        buttonNewIngredient = findViewById<Button>(R.id.btnAddNewIngredientToRecipe)
        editTextNewIngredient = findViewById<EditText>(R.id.etAddNewIngredientToRecipe)

        cardViewNewRecipe = findViewById(R.id.cvGiveNewItemName)
        newRecipeNameEditText = findViewById(R.id.etNameNewRecipe)
        newRecipeNameButton = findViewById(R.id.btnAddNewRecipeName)

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
            cardViewNewRecipe.visibility = VISIBLE
        }

        newRecipeNameButton.setOnClickListener{
            val name = newRecipeNameEditText.text.toString()
            var newRecipe = Recipe(0,name, "" )
            scope.launch{
                dao.insertRecipe(newRecipe)
            }
            newRecipeNameEditText.text.clear()
            cardViewNewRecipe.visibility = INVISIBLE
            scope.launch{
                recipeList = dao.getAllRecipes()
                initRecyclerView(recipeList)
            }
            listItemClicked(newRecipe)
        }
    }

    private fun initRecyclerView(recipeList : List<Recipe>){
        recipeMenu.layoutManager = LinearLayoutManager(this)
        recipeMenu.adapter = RecipeRecyclerViewAdapter(recipeList){selectedItem: Recipe ->
            listItemClicked(selectedItem)
        }
    }

    private fun initRecyclerViewIngredients(ingredientList : List<String>, recipe : Recipe){
        recipeClickedMenu.layoutManager = LinearLayoutManager(this)
        recipeClickedMenu.adapter = IngredientRecyclerViewAdapter(ingredientList){selectedItem: String ->
            ingredientItemClicked(selectedItem, recipe)
        }
    }
    private fun listItemClicked(recipe : Recipe){
        var ingredientList = recipe.ingredients.split("-")
        if(ingredientList.isNotEmpty() && ingredientList[0] == ""){
            ingredientList = ingredientList.drop(1)
        }
        initRecyclerViewIngredients(ingredientList, recipe)
        cardView.visibility = VISIBLE
        cardText.text = recipe.name

        buttonNewIngredient.setOnClickListener{
            if(editTextNewIngredient.text.equals("")){
                Toast.makeText(this, "Cannot add an empty ingredient", Toast.LENGTH_SHORT).show()
            }else{
                var modifiedIngredients = recipe.ingredients + "-" + editTextNewIngredient.text
                editTextNewIngredient.text.clear()
                var modifiedRecipe = recipe
                modifiedIngredients = modifiedIngredients.replace("--","-")
                if (modifiedIngredients == "-"){
                    modifiedIngredients = ""
                }
                modifiedRecipe.ingredients = modifiedIngredients
                Log.i("mytag", modifiedRecipe.toString())
                scope.launch{
                    dao.updateRecipe(modifiedRecipe)
                }
                var ingredientList = modifiedRecipe.ingredients.split("-")
                if(ingredientList.isNotEmpty() && ingredientList[0] == ""){
                    ingredientList = ingredientList.drop(1)
                }
                initRecyclerViewIngredients(ingredientList, modifiedRecipe)
            }
        }

        cardExitButton.setOnClickListener{
            cardView.visibility = INVISIBLE
            exitButton.visibility = VISIBLE
            newRecipeButton.visibility = VISIBLE
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

    private fun ingredientItemClicked(ingredient : String, recipe : Recipe){
        var modifiedRecipe = recipe
        var modifiedIngredients = recipe.ingredients
        modifiedIngredients = modifiedIngredients.replace(ingredient, "")
        modifiedIngredients = modifiedIngredients.replace("--","-")
        if (modifiedIngredients == "-"){
            modifiedIngredients = ""
        }
        modifiedRecipe.ingredients = modifiedIngredients
        Log.i("mytag", modifiedRecipe.toString())
        scope.launch{
            dao.updateRecipe(modifiedRecipe)
        }
        var ingredientList = modifiedRecipe.ingredients.split("-")
        ingredientList = ingredientList.filter{it.isNotEmpty()}
        if(ingredientList.isNotEmpty() && ingredientList[0] == ""){
            ingredientList = ingredientList.drop(1)
        }
        initRecyclerViewIngredients(ingredientList, recipe)
    }
}