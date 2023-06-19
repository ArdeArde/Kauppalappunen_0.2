package com.example.kauppalappunen_02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IngredientRecyclerViewAdapter(private val stringList:List<String>, private val clickListener:(String) -> Unit): RecyclerView.Adapter<StringViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recipe_list_item,parent,false)
        return StringViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val string = stringList[position]
        holder.bind(string,clickListener)
    }

}

class StringViewHolder(private val view: View):RecyclerView.ViewHolder(view){

    fun bind(string: String, clickListener: (String) -> Unit){
        val stringNameTextView = view.findViewById<TextView>(R.id.tvRecipeName)
        stringNameTextView.text = string

        view.setOnClickListener{
            clickListener(string)
        }
    }
}