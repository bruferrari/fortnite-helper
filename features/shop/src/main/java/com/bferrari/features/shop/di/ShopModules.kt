package com.bferrari.features.shop.di
import com.bferrari.features.shop.ShopStore
import com.bferrari.features.shop.data.ShopDataSource
import com.bferrari.features.shop.data.ShopFetcher
import com.bferrari.features.shop.data.ShopRepository
import com.bferrari.features.shop.viewmodels.ShopViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ShopModules {

    private val repositoryModules
        get() = module {
            factory<ShopDataSource> { ShopRepository(get(), get()) }
        }

    private val fetcherModules
        get() = module {
            factory { ShopFetcher(get()) }
        }

    private val storeModules
        get() = module {
            factory { ShopStore(get()) }
        }

    private val viewModelModules
        get() = module {
            viewModel { ShopViewModel(get()) }
        }

    val modules = arrayOf(
        fetcherModules,
        storeModules,
        repositoryModules,
        viewModelModules
    )
}