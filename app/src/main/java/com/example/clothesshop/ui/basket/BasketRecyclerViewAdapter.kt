package com.example.clothesshop.ui.basket

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.GlideApp
import com.example.clothesshop.R
import com.example.clothesshop.model.ProductBasket

interface ProductBasketActionListener {

    fun onProductDelete(productBasket: ProductBasket)

    fun onUserDetails(productBasket: ProductBasket)

}

class ProductBasketDiffCallback(
    private val oldList: List<ProductBasket>,
    private val newList: List<ProductBasket>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProductBasket = oldList[oldItemPosition]
        val newProductBasket = newList[newItemPosition]
        return oldProductBasket.id == newProductBasket.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProductBasket = oldList[oldItemPosition]
        val newProductBasket = newList[newItemPosition]
        return oldProductBasket == newProductBasket
    }

}

class BasketRecyclerViewAdapter(private val actionListener: ProductBasketActionListener) :
    RecyclerView.Adapter<BasketRecyclerViewAdapter.ViewHolder>() {

    var productsBasket: List<ProductBasket> = emptyList()
        set(newValue) {
            val diffCallback = ProductBasketDiffCallback(field, newValue)
            Log.i("TaG32",field.size.toString() +" "+ newValue.size.toString())
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_product_basket, parent, false)
        return BasketRecyclerViewAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = productsBasket[position]

        holder.textName.text=ItemsViewModel.name
        holder.textPrice.text=ItemsViewModel.price

        GlideApp.with(holder.imageView.context)
            .load(ItemsViewModel.image)
            .error(R.drawable.ic_baseline_autorenew_24)
            .into(holder.imageView)

        holder.buttonBasket.setOnClickListener {
            val productBasket = ItemsViewModel
            actionListener.onProductDelete(productBasket)
        }

        holder.linearLayout.setOnClickListener {
            val productBasket = ItemsViewModel
            actionListener.onUserDetails(productBasket)
        }

    }

    override fun getItemCount(): Int {
        return productsBasket.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)

        val textName: TextView = itemView.findViewById(R.id.textName)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        val buttonBasket: ImageView = itemView.findViewById(R.id.button_basket_delete)

    }

}

