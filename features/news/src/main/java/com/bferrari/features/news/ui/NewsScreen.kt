package com.bferrari.features.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import com.bferrari.common.utils.rememberFlowWithLifecycle
import com.bferrari.features.news.data.remote.News
import com.bferrari.features.news.viewmodels.NewsUiState
import com.bferrari.features.news.viewmodels.NewsViewModel
import com.bferrari.fortnitehelper.resources.components.AppBar
import com.bferrari.fortnitehelper.resources.components.ErrorView
import com.bferrari.fortnitehelper.resources.components.LoadingView
import timber.log.Timber

@Composable
fun News(viewModel: NewsViewModel) {
    val state by rememberFlowWithLifecycle(viewModel.newsState)
        .collectAsState(
            initial = NewsUiState.Loading
        )

    when(state) {
        is NewsUiState.Success -> NewsList((state as NewsUiState.Success).news)
        is NewsUiState.Error -> ErrorView()
        is NewsUiState.Loading -> LoadingView()
    }

    AppBar(
        title = "News",
        titleAlignment = TextAlign.Center
    )
}

@Composable
fun NewsList(news: News) {
    Timber.d(news.br?.motds.toString())
}