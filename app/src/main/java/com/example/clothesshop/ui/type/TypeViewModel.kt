package com.example.clothesshop.ui.type

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesshop.data.ProductRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TypeViewModel(private val path: String): ViewModel() {

    private val _typeForm = MutableLiveData<TypeView>()
    val typeFormState: LiveData<TypeView> = _typeForm

    fun getTypes(){
        val fbDB= ProductRepository.getInstance(path)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSn in dataSnapshot.children) {
                    var item = dataSn.getValue(TypeView::class.java)
                    if (item != null) {
                        _typeForm.value= TypeView(item.url_image,item.name)
                        Log.i("tag101",item.url_image.toString())
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