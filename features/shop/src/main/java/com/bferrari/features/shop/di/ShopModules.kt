package com.bferrari.features.shop.di
import com.bferrari.features.shop.data.ShopDataSource
import com.bferrari.features.shop.data.ShopRepository
import com.bferrari.features.shop.viewmodels.ShopViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ShopModules {

    val repositoryModules
        get() = module {
            factory<ShopDataSource> { ShopRepository(get()) }
        }

    val viewModelModules
        get() = module {
            viewModel { ShopViewModel(get()) }
        }
}