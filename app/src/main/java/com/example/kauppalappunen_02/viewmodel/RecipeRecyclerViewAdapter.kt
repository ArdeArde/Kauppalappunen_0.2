package com.example.kauppalappunen_02.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kauppalappunen_02.R
import com.example.kauppalappunen_02.db.Recipe

class RecipeRecyclerViewAdapter(): RecyclerView.Adapter<RecipeViewHolder>() {

    private val recipeList = ArrayList<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recipe_list_item,parent,false)
        return RecipeViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    fun setList(recipes:List<Recipe>){
        recipeList.clear()
        recipeList.addAll(recipes)
    }

}

class RecipeViewHolder(private val view : View):RecyclerView.ViewHolder(view){

    fun bind(recipe: Recipe){
        val recipeNameTextView = view.findViewById<TextView>(R.id.tvRecipeName)
        recipeNameTextView.text = recipe.name
    }
}