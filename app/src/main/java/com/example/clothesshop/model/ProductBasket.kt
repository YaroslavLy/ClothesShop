package com.example.clothesshop.model

data class ProductBasket(
    val id: String? = "",
    val image: String? = "",
    val name: String? = "",
    val price: String? = "",
    val code: String? = "",
    val in_bascked: Boolean? = false,
    val type: String? = "",
    val description: String? = ""
) {
    override fun equals(other: Any?): Boolean {
        return if (other is ProductBasket)
            this.code == other.code
        else
            false
    }
}