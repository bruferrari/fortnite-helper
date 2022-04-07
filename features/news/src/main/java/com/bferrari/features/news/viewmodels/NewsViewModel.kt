package com.bferrari.features.news.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bferrari.features.news.data.NewsRepository
import com.bferrari.features.news.data.remote.News
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val newsState: StateFlow<NewsUiState> get() = _newsState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    init { fetchNewsUnits() }

    private fun fetchNewsUnits() {
        viewModelScope.launch {
            newsRepository.fetchNews().map {
                News(br = it.news.br, stw = it.news.stw, creative = it.news.creative)
            }
            .onStart {
                _isRefreshing.emit(true)
            }
            .catch { cause ->
                _isRefreshing.emit(false)
                Timber.e(cause)
                _newsState.update { NewsUiState.Error(cause) }
            }
            .collect { news ->
                _isRefreshing.emit(false)
                _newsState.update { NewsUiState.Success(news) }
            }
        }
    }
}

sealed class NewsUiState {
    data class Success(val news: News) : NewsUiState()
    data class Error(val exception: Throwable) : NewsUiState()
    object Loading : NewsUiState()
}