package com.example.clothesshop.data

import android.util.Log
import com.example.clothesshop.model.Product
import com.example.clothesshop.model.ProductBasket
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


//todo replace get user uid
class BasketRepository(val path: String="") {
    private lateinit var auth: FirebaseAuth

    fun getProducts() : Flow<Result<ProductBasket>> = callbackFlow  {
        auth = Firebase.auth
        val user= auth.currentUser
        val userId = user?.uid
        val fbDB = FirebaseDatabase.getInstance().getReference("Basket/$userId")
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
        auth = Firebase.auth
        val user= auth.currentUser
        val userId = user?.uid
        val fbDB = FirebaseDatabase.getInstance().getReference("Basket/$userId")
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
        auth = Firebase.auth
        val user= auth.currentUser
        val userId = user?.uid
        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Basket/$userId")
        productBasket.id?.let { firebaseDatabase.child(it).removeValue() }
    }


}