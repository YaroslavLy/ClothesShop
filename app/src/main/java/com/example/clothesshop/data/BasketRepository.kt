package com.example.clothesshop.data

import android.util.Log
import com.example.clothesshop.model.Product
import com.example.clothesshop.model.ProductBasket
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BasketRepository(val path: String="") {

    fun getProducts() : Flow<Result<ProductBasket>> = callbackFlow  {
        val fbDB = FirebaseDatabase.getInstance().getReference("Basket/def")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSn in dataSnapshot.children) {
                    var item = dataSn.getValue(Product::class.java)
                    if (item != null) {
                        val product = ProductBasket(
                            id = dataSn.key,
                            image = item.image,
                            name = item.name,
                            price = item.price,
                            code = item.code,//get key
                            in_bascked = item.in_bascked,
                            type = item.type,
                            description = item.description
                        )
                        trySend(Result.Success<ProductBasket>(product)).isSuccess
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                trySend(Result.Error(databaseError.toException())).isFailure
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        fbDB.addValueEventListener(postListener)

        awaitClose {
            close()
        }
    }

    fun getCountProductsInBasket():Flow<Int> = callbackFlow {
        val fbDB = FirebaseDatabase.getInstance().getReference("Basket/def")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count = dataSnapshot.childrenCount

                trySend(count.toInt()).isSuccess
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //trySend(Result.Error(databaseError.toException())).isFailure
                //Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }

        }
        fbDB.addValueEventListener(postListener)
        awaitClose {
            close()
        }
    }

    fun removeProductInBasket(productBasket: ProductBasket){
        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Basket/def")
        productBasket.id?.let { firebaseDatabase.child(it).removeValue() }
    }


}