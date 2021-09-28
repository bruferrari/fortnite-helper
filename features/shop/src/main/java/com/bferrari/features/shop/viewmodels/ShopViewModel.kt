package com.bferrari.features.shop.viewmodels

import androidx.lifecycle.ViewModel
import com.bferrari.features.shop.data.ShopDataSource

class ShopViewModel(
   private val shopRepository: ShopDataSource
) : ViewModel() {

}
