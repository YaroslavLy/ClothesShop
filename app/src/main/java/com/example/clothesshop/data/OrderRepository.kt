package com.example.clothesshop.data

import com.example.clothesshop.model.Order
import com.google.firebase.database.FirebaseDatabase

class OrderRepository {

    fun save(order: Order){
        val v = FirebaseDatabase.getInstance().getReference("Orders/")
        v.push().setValue(order)
        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Basket/def")
        for (productBasket in order.products)
        {
            productBasket.id?.let { firebaseDatabase.child(it).removeValue() }
        }

    }


}