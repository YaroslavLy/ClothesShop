package com.example.clothesshop.ui.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesshop.data.BasketRepository
import kotlinx.coroutines.launch

class TabsViewModel(basketRepository: BasketRepository):ViewModel() {

    private val _tabsForm = MutableLiveData<Int>()
    val tabsFormState: LiveData<Int> = _tabsForm

    init {
        viewModelScope.launch {
            basketRepository.getCountProductsInBasket().collect{ count ->
                _tabsForm.value = count
            }
        }

    }
}