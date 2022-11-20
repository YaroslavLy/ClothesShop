package com.example.clothesshop.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clothesshop.data.BasketRepository
import com.example.clothesshop.data.CategoryRepository
import com.example.clothesshop.data.ProductRepository
import com.example.clothesshop.ui.category.CategoryViewModel

class ProductViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(
            productRepository = ProductRepository("path"),
            basketRepository = BasketRepository("")
        ) as T
    }
}