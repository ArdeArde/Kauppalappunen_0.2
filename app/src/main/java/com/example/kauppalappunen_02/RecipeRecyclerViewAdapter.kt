package com.example.kauppalappunen_02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kauppalappunen_02.db.Recipe

class RecipeRecyclerViewAdapter(private val recipeList:List<Recipe>, private val clickListener: (Recipe) -> Unit): RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recipe_list_item,parent,false)
        return RecipeViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.bind(recipe,clickListener)
    }

}

class RecipeViewHolder(private val view : View):RecyclerView.ViewHolder(view){

    fun bind(recipe: Recipe, clickListener: (Recipe) -> Unit){
        val recipeNameTextView = view.findViewById<TextView>(R.id.tvRecipeName)
        recipeNameTextView.text = recipe.name

        view.setOnClickListener{
            clickListener(recipe)
        }
    }
}