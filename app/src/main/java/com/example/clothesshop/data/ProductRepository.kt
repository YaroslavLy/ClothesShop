package com.example.clothesshop.data

import android.util.Log
import com.example.clothesshop.Constants
import com.example.clothesshop.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProductRepository(val path: String="") {

    fun getProducts() : Flow<Resource<Product>> = callbackFlow  {
        val fbDB = FirebaseDatabase.getInstance().getReference(Constants.COLLECTION_PRODUCTS)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSn in dataSnapshot.children) {
                    var item = dataSn.getValue(Product::class.java)
                    if (item != null) {
                        val product = Product(
                            image = item.image,
                            name = item.name,
                            price = item.price,
                            code = item.code,
                            in_bascked = item.in_bascked,
                            type = item.type
                        )
                        trySend(Resource.Success<Product>(product)).isSuccess
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                trySend(Resource.Error(databaseError.toException().toString())).isFailure
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        fbDB.addValueEventListener(postListener)

        awaitClose {
            close()
        }
    }
}