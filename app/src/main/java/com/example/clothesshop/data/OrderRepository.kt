package com.example.clothesshop.data

import android.util.Log
import com.example.clothesshop.model.Order
import com.example.clothesshop.model.OrderView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OrderRepository {

    fun save(order: Order) {
        val v = FirebaseDatabase.getInstance().getReference("Orders/")
        v.push().setValue(order)
        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Basket/def")
        for (productBasket in order.products!!) {
            productBasket.id?.let { firebaseDatabase.child(it).removeValue() }
        }

    }

    fun getOrders(path: String): Flow<Result<OrderView>> = callbackFlow {
        val fbDB = FirebaseDatabase.getInstance().getReference("Orders")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSn in dataSnapshot.children) {
                    var item = dataSn.getValue(Order::class.java)
                    if (item != null) {
                        val product = item.products?.let {
                            item.ifPaid?.let { it1 ->
                                item.dateOrder?.let { it2 ->
                                    OrderView(
                                        id = dataSn.key,
                                        address = item.address,
                                        products = it,
                                        sumPrice = item.sumPrice,
                                        ifPaid = it1,
                                        dateOrder = it2
                                    )
                                }
                            }
                        }
                        product?.let { Result.Success<OrderView>(it) }
                            ?.let { trySend(it).isSuccess }
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


}