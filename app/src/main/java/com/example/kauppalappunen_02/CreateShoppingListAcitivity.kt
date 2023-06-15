package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
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

private lateinit var exitButton: Button
private lateinit var confirmListButton: Button
private lateinit var recipeMenu: RecyclerView
private var recipeList = listOf<Recipe>()
private lateinit var viewModel: RecipeViewModel
private lateinit var scope: CoroutineScope
private lateinit var dao: RecipeDao
private lateinit var factory: RecipeViewModelFactory
private var shoppingListUnparsed= listOf<String>()
private var tempList = listOf<Recipe>()
private lateinit var tempRecipe: Recipe
private var shoppingList = listOf<String>()
private var parsingList = listOf<String>()
private lateinit var tempString: String
class CreateShoppingListAcitivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shopping_list)

        recipeMenu = findViewById<RecyclerView>(R.id.rvRecipeMenu)
        exitButton = findViewById<Button>(R.id.btnExit)
        confirmListButton = findViewById<Button>(R.id.btnConfirmShoppingList)

        dao = RecipeDatabase.getInstance(application).RecipeDao()
        factory = RecipeViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(RecipeViewModel::class.java)
        scope = CoroutineScope(Dispatchers.Main)

        scope.launch{
            recipeList = dao.getAllRecipes()
            tempList = dao.getAllRecipes()
            initRecyclerView(recipeList)
        }

        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        confirmListButton.setOnClickListener{
            if (shoppingListUnparsed.isEmpty()){
                Toast.makeText(this, "shopping list is empty", Toast.LENGTH_SHORT).show()
            }else{
                var x = 0
                while (x < shoppingListUnparsed.size){
                    tempString = shoppingListUnparsed[x].toString()
                    parsingList = tempString.split("-")
                    parsingList.forEach{it ->
                        shoppingList = shoppingList.plus(it)
                    }
                    x++
                }
                val intent = Intent(this, ShoppingListAcitivty::class.java)
                //intent.putExtra("SHOPPINGLIST", shoppingList)
                startActivity(intent)
            }
        }
    }

    private fun initRecyclerView(recipeList : List<Recipe>){
        recipeMenu.layoutManager = LinearLayoutManager(this)
        recipeMenu.adapter = RecipeRecyclerViewAdapter(recipeList){selectedItem: Recipe ->
            listItemClicked(selectedItem)
        }
    }

    private fun listItemClicked(recipe : Recipe){
        val nameString = recipe.name
        tempRecipe = tempList.find { recipe ->
            recipe.name == nameString
        }!!
        shoppingListUnparsed = shoppingListUnparsed.plus(tempRecipe.ingredients)
    }
}