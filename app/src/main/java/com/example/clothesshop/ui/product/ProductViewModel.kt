package com.example.clothesshop.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesshop.data.ProductRepository
import com.example.clothesshop.data.Resource
import com.example.clothesshop.data.Result
import com.example.clothesshop.model.Product
import com.example.clothesshop.model.ProductBasket
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class ProductViewModel(val productRepository: ProductRepository) : ViewModel() {

    private val _productForm = MutableLiveData<Product>()
    val productFormState: LiveData<Product> = _productForm

//    private val _productFormD = MutableLiveData<Product>()
//    val productFormStateD: LiveData<Product> = _productFormD

    // todo separate get ONE Product an MORE ProductS

    fun getProduct()
    {

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

    private val _productBasketForm = MutableLiveData<ProductBasket>()
    val productBasketFormState: LiveData<ProductBasket> = _productBasketForm

    init {
//        productRepository.getProducts()
//            .onEach { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        _productForm.value = resource.data!!
//                    }
//                    is Resource.Error -> {
//                        //Log.w(TAG, resource.error!!)
//                    }
//                }
//            }
//            .launchIn(viewModelScope)
    }

    fun getBasketProducts()
    {
        viewModelScope.launch {
            productRepository.getProductsBasket().collect{ resource ->
                when (resource) {
                    is Result.Success -> {
                        _productBasketForm.value = resource.data as ProductBasket
                    }
                    is Result.Error -> {
                        //Log.w(TAG, resource.error!!)
                    }
                }
            }
        }
//            productRepository.getProducts()
//            .onEach { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        _productForm.value = resource.data!!
//                    }
//                    is Resource.Error -> {
//                        //Log.w(TAG, resource.error!!)
//                    }
//                }
//            }
//            .launchIn(viewModelScope)

    }
}