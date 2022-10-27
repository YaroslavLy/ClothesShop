package com.example.clothesshop.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesshop.data.ProductRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProductViewModel(private val path: String) : ViewModel() {

    private val _productForm = MutableLiveData<ProductView>()
    val productFormState: LiveData<ProductView> = _productForm

    fun getProducts(){
        val fbDB=ProductRepository.getInstance(path)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSn in dataSnapshot.children) {
                    var item = dataSn.getValue(ProductView::class.java)
                    if (item != null) {
                        _productForm.value=ProductView(item.image,item.name,item.price)
                        Log.i("tag1",item.code.toString())
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        fbDB.addValueEventListener(postListener)
    }

}