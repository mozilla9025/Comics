@file:OptIn(ExperimentalMaterialApi::class)

package app.comics.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import app.comics.ui.model.DisplayComic
import app.comics.ui.screen.item.ComicsItem
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FavoriteComicsScreen(viewModel: FavoritesViewModel = viewModel()) {
    val favorites by viewModel.uiState

    FavoritesScreenContent(
        favorites = favorites,
        onAddToWishlist = { viewModel.dispatchUiEvent(FavoritesViewModel.UiEvent.AddToWishlist(it)) }
    )
}

@Composable
private fun FavoritesScreenContent(
    favorites: ImmutableList<DisplayComic>,
    onAddToWishlist: (item: DisplayComic) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Box(
            contentAlignment = Alignment.TopStart
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                favorites.forEach {
                    item {
                        ComicsItem(it) { onAddToWishlist(it) }
                    }
                }
            }
        }
    }
}

