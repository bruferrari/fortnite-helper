package com.bferrari.fortnitehelper.di

import androidx.room.Room
import com.bferrari.features.shop.ShopService
import com.bferrari.fortnitehelper.core.data.room.AppDatabase
import com.bferrari.fortnitehelper.core.network.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataModules {

    private val apiModules
        get() = module {
            single { HttpClient.apiService<ShopService>() }
        }

    private val dbModules
        get() = module {
            single {
                Room.databaseBuilder(
                    androidContext(),
                    AppDatabase::class.java, "fortnite-companion"
                ).build()
            }
        }

    private val daoModules
        get() = module {
            single { get<AppDatabase>().shopEntryDao() }
        }

    val modules = arrayOf(
        apiModules,
        dbModules,
        daoModules
    )
}