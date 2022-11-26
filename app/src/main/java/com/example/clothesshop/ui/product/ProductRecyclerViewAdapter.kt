package com.example.clothesshop.ui.product


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
import com.example.clothesshop.model.Product
import com.example.clothesshop.model.ProductBasket

import com.google.firebase.database.FirebaseDatabase


class ProductRecyclerViewAdapter(
    private val mList: List<Product>,
    private val mBasketList: MutableList<ProductBasket>,
    private val view1: RecyclerView
) :
    RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {

    //val context: Context
    lateinit var view: View
     var idProducts = mutableListOf<String>()
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

            val action = ItemsViewModel.code?.let { it1 ->
                ProductFragmentDirections.actionProductFragment3ToProductDetailsFragment4(it1)
            }
            if (action != null) {
                Navigation.findNavController(view = view1).navigate(action)
            }

        }
        if(ifElementInBasket(ItemsViewModel))
        {
            holder.buttonBasket.setImageResource(R.drawable.ic_baseline_shopping_cart_24_2)
        }
        holder.buttonBasket.setOnClickListener {
            //TODO move to Repo
            val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Basket/def")


            if(!ifElementInBasket(ItemsViewModel)) {
                val t1 = ItemsViewModel.copy()
                firebaseDatabase.push().setValue(t1)
                holder.buttonBasket.setImageResource(R.drawable.ic_baseline_shopping_cart_24_2)
            }else
            {
                val t1 = ItemsViewModel.copy()
                // the best code
                t1.code?.let { it1 -> getItemByCode(it1).id?.let { it1 -> firebaseDatabase.child(it1).removeValue() } }
                //mBasketList.remove(t1.code?.let { it1 -> getItemByCode(it1) })
                //ProductFragment.basketData =
                ProductFragment.basketData.remove(getItemByCode(t1.code!!))
                holder.buttonBasket.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24)
            }

        }

    }

    private fun getItemByCode(code: String):ProductBasket{
        for (productBasket in mBasketList)
        {
            if(productBasket.code.equals(code))
                return productBasket
        }
        return ProductBasket()
    }

    private fun ifElementInBasket(product: Product):Boolean
    {
        for (productBasket in mBasketList) {
            Log.i("TAR", productBasket.code.toString())
        }
        Log.i("TAR","----------------------")
        for (productBasket in mBasketList)
        {
            //Log.i("TAR",mBasketList.toString())
            if(productBasket.code.equals(product.code))
                return true
        }
        return false
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
