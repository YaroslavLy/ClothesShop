package com.example.clothesshop

import android.util.Log
import com.example.clothesshop.ui.product.ProductView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ProvideData {
    companion object{
        fun getData():List<ProductView>{
            var listProductViewModel = mutableListOf<ProductView>()
            listProductViewModel.add(
                ProductView(
                    image ="https://content.rozetka.com.ua/goods/images/big/228881249.jpg",
                    name = "Kurtka damska MERIDA beżowa Dstreet",
                    code = "TY2961",
                    price = "189,99 zł",
                    in_bascked = true,
                    type = "W"
                )
            )
            listProductViewModel.add(
                ProductView(
                    image ="https://content1.rozetka.com.ua/goods/images/big/232372516.jpg",
                    name = "Kurtka męska skórzana czarna Dstreet",
                    code = "TX4246",
                    price = "159,99 zł",
                    in_bascked = false,
                    type = "M"

                )
            )
            listProductViewModel.add(
                ProductView(
                    image ="https://content.rozetka.com.ua/goods/images/big/275367047.jpg",
                    name = "Kurtka męska granatowa Dstreet",
                    code = "TX4119",
                    price = "159,99 zł",
                    in_bascked = true,
                    type = "M"
                )
            )
            listProductViewModel.add(
                ProductView(
                    image ="https://content1.rozetka.com.ua/goods/images/big/277245043.jpg",
                    name = "Kurtka męska skórzana czarna Dstreet",
                    code = "TX3464",
                    price = "89,99 zł",
                    in_bascked = false,
                    type = "M"
                )
            )




            return listProductViewModel
        }

        fun getDataBD(mDataBase: DatabaseReference):List<ProductView>
        {
            var listProduсts= mutableListOf<ProductView>()
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    //val post = dataSnapshot.getValue<P>()
                    for (dataSn in dataSnapshot.children)
                    {
                        //dataSn.getValue(ProductViewModel::class.java)?.let { listProduсts.add(it) }
                        var t = dataSn.getValue<ProductView>(ProductView::class.java)

                        if (t != null) {
                            listProduсts.add(t)
                            t.name?.let { Log.i("tag1", it) }
                        }
                      //val po=
                    }
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                }
            }
            mDataBase.addValueEventListener(postListener)

            // mDataBase
            return listProduсts
        }
    }
}