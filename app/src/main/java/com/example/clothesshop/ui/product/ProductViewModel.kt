package com.example.clothesshop.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesshop.data.ProductRepository
import com.example.clothesshop.data.Resource
import com.example.clothesshop.model.Product
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ProductViewModel(productRepository: ProductRepository) : ViewModel() {

    private val _productForm = MutableLiveData<Product>()
    val productFormState: LiveData<Product> = _productForm

    init {
        productRepository.getProducts()
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _productForm.value = resource.data!!
                    }
                    is Resource.Error -> {
                        //Log.w(TAG, resource.error!!)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}