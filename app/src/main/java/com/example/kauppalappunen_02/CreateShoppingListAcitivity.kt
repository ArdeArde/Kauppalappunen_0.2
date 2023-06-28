package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

private lateinit var recipeMenu: RecyclerView
private var recipeList = listOf<Recipe>()
private lateinit var viewModel: RecipeViewModel
private lateinit var scope: CoroutineScope
private lateinit var dao: RecipeDao
private lateinit var factory: RecipeViewModelFactory
private lateinit var shoppingListUnparsed: String
private var tempList = listOf<Recipe>()

class CreateShoppingListActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shopping_list)

        recipeMenu = findViewById(R.id.rvRecipeMenu)
        val exitButton = findViewById<Button>(R.id.btnExit)
        val confirmListButton = findViewById<Button>(R.id.btnConfirmShoppingList)
        val extraItemTextView = findViewById<EditText>(R.id.tvExtraItemName)
        val extraItemButton = findViewById<Button>(R.id.btnExtraItemConfirm)

        dao = RecipeDatabase.getInstance(application).RecipeDao()
        factory = RecipeViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]
        scope = CoroutineScope(Dispatchers.Main)
        shoppingListUnparsed = ""

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
                val intent = Intent(this, ShoppingListActivity::class.java)
                intent.putExtra("SHOPPINGLIST", shoppingListUnparsed)
                startActivity(intent)
            }
        }

        extraItemButton.setOnClickListener{
            var extraItemString = extraItemTextView.text.toString()
            shoppingListUnparsed = "$shoppingListUnparsed$extraItemString-"
            extraItemTextView.text.clear()
            Toast.makeText(this, "Unique item added to list", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView(recipeList : List<Recipe>){
        recipeMenu.layoutManager = LinearLayoutManager(this)
        recipeMenu.adapter = RecipeRecyclerViewAdapter(recipeList){selectedItem: Recipe ->
            listItemClicked(selectedItem)
        }
    }

    private fun listItemClicked(recipe : Recipe){
        val nameString = recipe.ingredients
        shoppingListUnparsed = "$shoppingListUnparsed$nameString-"
        Toast.makeText(this, "Item added to shopping list", Toast.LENGTH_SHORT).show()
    }
}