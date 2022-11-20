package com.example.clothesshop.ui.basket

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.GlideApp
import com.example.clothesshop.R
import com.example.clothesshop.model.ProductBasket
import com.example.clothesshop.model.Type


class BasketRecyclerViewAdapter(private val mList: List<ProductBasket>, private val view: View) :
    RecyclerView.Adapter<BasketRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_product_basket, parent, false)

        return BasketRecyclerViewAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.textName.text=ItemsViewModel.name
        //holder.textCode.text=ItemsViewModel.code
        holder.textPrice.text=ItemsViewModel.price

        GlideApp.with(holder.imageView.context)
            .load(ItemsViewModel.image)
            .error(R.drawable.ic_baseline_autorenew_24)
            .into(holder.imageView)
//        holder.linearLayout.setOnClickListener{
//            Navigation.findNavController(view)
//                .navigate(R.id.action_typeFragment_to_productFragment)
//        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


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

