package com.example.clothesshop.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesshop.data.BasketRepository
import com.example.clothesshop.data.OrderRepository
import com.example.clothesshop.data.Result
import com.example.clothesshop.model.Order
import com.example.clothesshop.model.ProductBasket
import kotlinx.coroutines.launch

class OrderViewModel(
    private var basketRepository: BasketRepository,
    private var orderRepository: OrderRepository
) : ViewModel() {

    private val _basketProductForm = MutableLiveData<ProductBasket>()
    val basketProductFormState: LiveData<ProductBasket> = _basketProductForm


    fun saveOrder(order: Order) {
        orderRepository.save(order)
    }


    fun getProductsFromBasket() {
        viewModelScope.launch {
            basketRepository.getProducts().collect { resource ->
                when (resource) {
                    is Result.Success -> {
                        _basketProductForm.value = resource.data!!
                    }
                    is Result.Error -> {
                        //Log.w(TAG, resource.error!!)
                    }
                }
            }
        }

    }

}