package com.example.clothesshop.model

import java.util.Date

data class Order(
    val address: String,
    val sumPrice: String,
    val products: List<ProductBasket>,
    val ifPaid: Boolean= false,
    val dateOrder: Date
)

data class OrderSave(
    val address: String,
    val products: List<Product>,
    val sumPrice: String,
    val ifPaid: Boolean= false,
    val dateOrder: Date

)