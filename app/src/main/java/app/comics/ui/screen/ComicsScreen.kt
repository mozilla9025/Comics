@file:OptIn(ExperimentalMaterialApi::class)

package app.comics.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import app.comics.ui.composable.ErrorView
import app.comics.ui.model.ComicListUiState
import app.comics.ui.model.DisplayComic
import app.comics.ui.model.ErrorState
import app.comics.ui.model.LoadingState
import app.comics.ui.screen.item.ComicsItem

@Composable
fun ComicsScreen(viewModel: ComicsViewModel = viewModel()) {
    val uiState by viewModel.uiState
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = uiState.loadingState == LoadingState.Refreshing,
        onRefresh = { viewModel.dispatchUiEvent(ComicsViewModel.UiEvent.Refresh) }
    )

    LaunchedEffect(true) {
        viewModel.dispatchUiEvent(ComicsViewModel.UiEvent.Refresh)
    }

    ComicsScreenContent(
        pullToRefreshState = pullToRefreshState,
        uiState = uiState,
        onRetry = { viewModel.dispatchUiEvent(ComicsViewModel.UiEvent.Refresh) },
        onSearch = { viewModel.dispatchUiEvent(ComicsViewModel.UiEvent.Filter(it)) },
        onAddToWishlist = { viewModel.dispatchUiEvent(ComicsViewModel.UiEvent.AddToWishlist(it)) }
    )
}

@Composable
private fun ComicsScreenContent(
    pullToRefreshState: PullRefreshState,
    uiState: ComicListUiState,
    onRetry: () -> Unit,
    onSearch: (query: String) -> Unit,
    onAddToWishlist: (item: DisplayComic) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TextField(
            value = uiState.searchQuery ?: "",
            onValueChange = onSearch,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .pullRefresh(pullToRefreshState),
            contentAlignment = Alignment.TopStart
        ) {

            if (uiState.errorState is ErrorState.Error) {
                ErrorView(
                    text = uiState.errorState.description,
                    buttonActionText = "Retry",
                    buttonAction = onRetry
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    uiState.comics.forEach {
                        item {
                            ComicsItem(it) { onAddToWishlist(it) }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.loadingState == LoadingState.Refreshing,
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
