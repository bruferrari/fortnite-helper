package com.bferrari.features.shop.di
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import com.bferrari.features.shop.ShopStore
import com.bferrari.features.shop.data.ShopDataSource
import com.bferrari.features.shop.data.ShopFetcher
import com.bferrari.features.shop.data.ShopRepository
import com.bferrari.features.shop.viewmodels.ShopViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

object ShopModules {

    private val repositoryModules
        get() = module {
            factory<ShopDataSource> { (scope: CoroutineScope, dataStore: DataStore<Preferences>) ->
                ShopRepository(
                    get(),
                    get { parametersOf(dataStore) },
                    scope
                )
            }
        }

    private val fetcherModules
        get() = module {
            factory { ShopFetcher(get()) }
        }

    private val storeModules
        get() = module {
            factory { (dataStore: DataStore<Preferences>) ->
                ShopStore(get(), dataStore = dataStore)
            }
        }

    private val viewModelModules
        get() = module {
            viewModel { (scope: CoroutineScope, dataStore: DataStore<Preferences>) ->
                ShopViewModel(
                    get { parametersOf(scope, dataStore) }
                )
            }
        }

    val modules = arrayOf(
        fetcherModules,
        storeModules,
        repositoryModules,
        viewModelModules
    )
}