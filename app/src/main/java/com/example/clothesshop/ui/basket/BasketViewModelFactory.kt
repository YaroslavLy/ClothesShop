package com.example.clothesshop.ui.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clothesshop.data.BasketRepository
import com.example.clothesshop.data.ProductRepository
import com.example.clothesshop.ui.product.ProductViewModel

class BasketViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BasketViewModel(basketRepository = BasketRepository()) as T
    }
}