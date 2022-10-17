package com.example.clothesshop

import android.graphics.Bitmap

data class ProductViewModelTrue(
    val image: Bitmap,
    val name: String? = "",
    val price: String? = "",
    val code: String? = "",
    val in_bascked: Boolean? = true,
    val type: String? = ""
) {
}