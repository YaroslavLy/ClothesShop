package com.example.clothesshop.ui.splash


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * SplashViewModel checks whether user is signed-in or not.
 */
class SplashViewModel(
    //private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _launchMainScreenEvent = MutableLiveData<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent

    init {
        viewModelScope.launch {
            delay(1000)
            //_categoryForm.value = resource.data!!
            // todo use repo check login
            _launchMainScreenEvent.value=true
        }
    }
}