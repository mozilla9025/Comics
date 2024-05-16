package app.comics.ui.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.comics.data.cache.ComicsCache
import app.comics.ui.model.DisplayComic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val comicsCache: ComicsCache
) : ViewModel() {

    private val _uiState: MutableState<ImmutableList<DisplayComic>> =
        mutableStateOf(emptyList<DisplayComic>().toImmutableList())
    val uiState: State<ImmutableList<DisplayComic>> = _uiState

    init {
        observeFavorites()
    }

    fun dispatchUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.AddToWishlist -> dispatchAddToWishlist(uiEvent)
        }
    }

    private fun dispatchAddToWishlist(uiEvent: UiEvent.AddToWishlist) {
        if (uiEvent.comic.isFavorite) {
            comicsCache.remove(uiEvent.comic)
        } else {
            val updated = uiEvent.comic.copy(isFavorite = !uiEvent.comic.isFavorite)
            comicsCache.add(updated)
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            comicsCache.data.collect {
                _uiState.value = it.toImmutableList()
            }
        }
    }

    sealed interface UiEvent {
        class AddToWishlist(val comic: DisplayComic) : UiEvent
    }
}
