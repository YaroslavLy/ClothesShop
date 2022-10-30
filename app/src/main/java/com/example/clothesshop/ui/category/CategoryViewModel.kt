package com.example.clothesshop.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clothesshop.data.ProductRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class CategoryViewModel(private val path: String): ViewModel() {
    private val _categoryForm = MutableLiveData<CategoryView>()
    val categoryFormState: LiveData<CategoryView> = _categoryForm

    fun getCategories(){
        val fbDB= ProductRepository.getInstance(path)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSn in dataSnapshot.children) {
                    var item = dataSn.getValue(CategoryView::class.java)
                    if (item != null) {
                        _categoryForm.value= CategoryView(item.url_image,item.name_folder,item.name_pl)
                        //Log.i("tag1",item.code.toString())
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