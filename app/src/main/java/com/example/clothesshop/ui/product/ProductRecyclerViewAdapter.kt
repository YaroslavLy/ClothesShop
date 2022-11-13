package com.example.clothesshop.ui.product


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.GlideApp

import com.example.clothesshop.R
import com.example.clothesshop.model.Product


class ProductRecyclerViewAdapter(private val mList: List<Product>) :
    RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {

    //val context: Context
    lateinit var view: View
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
         view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_product, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        

        holder.textName.text=ItemsViewModel.name
        //holder.textCode.text=ItemsViewModel.code
        holder.textPrice.text=ItemsViewModel.price

        GlideApp.with(holder.imageView.context)
            .load(ItemsViewModel.image)
            .error(R.drawable.ic_baseline_autorenew_24)
            .into(holder.imageView)
        holder.linearLayout.setOnClickListener{
            Log.i("tag","Hice")
        }
        holder.buttonBasket.setOnClickListener {
            holder.buttonBasket.setImageResource(R.drawable.ic_baseline_shopping_cart_24_2)
        }

    }



    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)

        //val textView: TextView = itemView.findViewById(R.id.textView)
        val textName: TextView = itemView.findViewById(R.id.textName)
       // val textCode: TextView = itemView.findViewById(R.id.textCode)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        val buttonBasket: ImageView = itemView.findViewById(R.id.button_basket)

    }
}
