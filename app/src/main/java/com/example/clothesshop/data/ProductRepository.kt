package com.example.clothesshop.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProductRepository() {

    companion object {
        fun getInstance(path: String): DatabaseReference {
            return FirebaseDatabase.getInstance().getReference(path)
        }
    }
}