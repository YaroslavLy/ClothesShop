package com.example.clothesshop.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesshop.data.BasketRepository
import com.example.clothesshop.data.Resource
import com.example.clothesshop.data.Result
import com.example.clothesshop.model.ProductBasket
import kotlinx.coroutines.launch


class BasketViewModel(val basketRepository: BasketRepository): ViewModel() {


    // todo #2 create base View Model
    private val _tabsForm = MutableLiveData<Int>()
    val tabsFormState: LiveData<Int> = _tabsForm

    init {
        viewModelScope.launch {
            basketRepository.getCountProductsInBasket().collect { count ->
                _tabsForm.value = count
            }
        }
    }

    private val _basketProductForm = MutableLiveData<ProductBasket>()
    val basketProductFormState: LiveData<ProductBasket> = _basketProductForm

    fun getProductsFromBasket(){
        viewModelScope.launch {
            basketRepository.getProducts().collect{ resource ->
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

    fun removeProductFromBasket(productBasket: ProductBasket){
        basketRepository.removeProductInBasket(productBasket)
    }

}