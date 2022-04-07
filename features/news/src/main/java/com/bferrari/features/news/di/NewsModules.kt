package com.bferrari.features.news.di

import com.bferrari.features.news.data.FortniteNewsDataSource
import com.bferrari.features.news.data.NewsDataSource
import com.bferrari.features.news.data.NewsRepository
import com.bferrari.features.news.viewmodels.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object NewsModules {

    private val viewModelModules
        get() = module {
            viewModel { (_: CoroutineScope) ->
                NewsViewModel(get())
            }
        }

    private val repositoryModules
        get() = module {
            factory { NewsRepository(get()) }
        }

    private val dataSourceModules
        get() = module {
            factory<NewsDataSource> { FortniteNewsDataSource(get()) }
        }

    val modules = arrayOf(
        dataSourceModules,
        repositoryModules,
        viewModelModules
    )
}