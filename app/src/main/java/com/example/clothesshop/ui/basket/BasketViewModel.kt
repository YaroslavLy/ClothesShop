package com.example.clothesshop.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesshop.data.BasketRepository
import com.example.clothesshop.data.Resource
import com.example.clothesshop.data.TypeRepository
import com.example.clothesshop.model.ProductBasket
import com.example.clothesshop.model.Type
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class BasketViewModel(basketRepository: BasketRepository): ViewModel() {

    private val _basketProductForm = MutableLiveData<ProductBasket>()
    val basketProductFormState: LiveData<ProductBasket> = _basketProductForm

    init {
        basketRepository.getProducts()
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _basketProductForm.value = resource.data!!
                    }
                    is Resource.Error -> {
                        //Log.w(TAG, resource.error!!)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}