package com.example.clothesshop

data class ProductViewModel(
    val image: String? = "",
    val name: String? = "",
    val price: String? = "",
    val code: String? = "",
    val in_bascked: Boolean? = true,
    val type: String? = ""
) {
}