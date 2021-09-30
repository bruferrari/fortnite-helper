package com.bferrari.features.shop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bferrari.features.shop.data.ShopDataSource
import com.bferrari.features.shop.data.remote.ShopResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ShopViewModel(
   private val shopRepository: ShopDataSource
) : ViewModel() {

   private val _shopState = MutableStateFlow<ShopUiState>(ShopUiState.Loading)
   val shopState: StateFlow<ShopUiState> get() = _shopState

   init {
       viewModelScope.launch {
          shopRepository.getShopItems()
             .catch { cause ->
                Timber.e(cause)
                _shopState.value = ShopUiState.Error(cause)
             }
             .collect { response ->
                _shopState.value = ShopUiState.Success(response)
             }
       }
   }
}

sealed class ShopUiState {
   data class Success(val shopResponse: ShopResponse) : ShopUiState()
   data class Error(val exception: Throwable) : ShopUiState()
   object Loading : ShopUiState()
}