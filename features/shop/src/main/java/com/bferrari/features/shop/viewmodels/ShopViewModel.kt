package com.bferrari.features.shop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bferrari.common.utils.concat
import com.bferrari.features.shop.data.ShopDataSource
import com.bferrari.features.shop.data.mappers.toShopEntryList
import com.bferrari.features.shop.data.remote.ShopResponse
import com.bferrari.features.shop.models.ShopEntry
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
            }.collect {
               _isRefreshing.emit(false)
               val result = getShopEntries(it)
               _shopState.value = ShopUiState.Success(result)
            }
      }
   }

   private fun getShopEntries(shopResponse: ShopResponse): List<ShopEntry> {
      val featured = shopResponse.data?.featured?.entries?.toShopEntryList() ?: emptyList()
      val daily = shopResponse.data?.daily?.entries?.toShopEntryList() ?: emptyList()
      val special = shopResponse.data?.specialFeatured?.entries?.toShopEntryList() ?: emptyList()

      return featured.concat(daily, special)
   }
}

sealed class ShopUiState {
   data class Success(val entries: List<ShopEntry>) : ShopUiState()
   data class Error(val exception: Throwable) : ShopUiState()
   object Loading : ShopUiState()
}