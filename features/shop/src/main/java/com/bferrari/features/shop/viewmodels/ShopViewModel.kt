package com.bferrari.features.shop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bferrari.features.shop.data.ShopDataSource
import com.bferrari.features.shop.data.remote.ShopResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ShopViewModel(
   private val shopRepository: ShopDataSource
) : ViewModel() {

   private val _shopState = MutableStateFlow<ShopUiState>(ShopUiState.Loading)
   val shopState: StateFlow<ShopUiState> get() = _shopState

   private val _isRefreshing = MutableStateFlow(false)
   val isRefreshing get() = _isRefreshing.asStateFlow()

   init { fetchShopItems() }

   fun fetchShopItems() {
      viewModelScope.launch {
         shopRepository.getShopItems()
            .onStart {
               _isRefreshing.emit(true)
            }
            .catch { cause ->
               _isRefreshing.emit(false)
               Timber.e(cause)
               _shopState.value = ShopUiState.Error(cause)
            }
            .collect { response ->
               _isRefreshing.emit(false)
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