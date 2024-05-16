package app.comics.ui.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.comics.data.cache.ComicsCache
import app.comics.domain.usecase.GetComicsUseCase
import app.comics.ui.mapper.DisplayComicMapper
import app.comics.ui.model.ComicListUiState
import app.comics.ui.model.DisplayComic
import app.comics.ui.model.ErrorState
import app.comics.ui.model.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
    private val displayComicMapper: DisplayComicMapper,
    private val comicsCache: ComicsCache
) : ViewModel() {

    private val _uiState: MutableState<ComicListUiState> = mutableStateOf(ComicListUiState.make())
    val uiState: State<ComicListUiState> = _uiState

    private val searchFilterFlow = MutableStateFlow(FilterQuery(null))

    init {
        observeSearchQuery()
    }

    fun dispatchUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            UiEvent.Refresh -> dispatchRefreshEvent()
            is UiEvent.Filter -> dispatchFilterEvent(uiEvent)
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
        replaceFavorite(uiEvent.comic, !uiEvent.comic.isFavorite)
    }

    private fun dispatchFilterEvent(uiEvent: UiEvent.Filter) {
        viewModelScope.launch {
            searchFilterFlow.emit(FilterQuery(uiEvent.query))
        }
    }

    private fun dispatchRefreshEvent() {
        viewModelScope.launch {
            searchFilterFlow.emit(FilterQuery(_uiState.value.searchQuery))
        }
    }

    private fun performSearch(query: String?) {
        _uiState.value = _uiState.value.copy(
            loadingState = LoadingState.Refreshing,
            errorState = ErrorState.None,
        )

        viewModelScope.launch {
            getComicsUseCase(query)
                .map { wrapper ->
                    wrapper.data.results.map { info ->
                        displayComicMapper.map(info)
                    }
                }
                .fold(
                    ifLeft = {
                        _uiState.value = _uiState.value.copy(
                            loadingState = LoadingState.None,
                            errorState = ErrorState.Error(it.toString())
                        )
                    },
                    ifRight = {
                        _uiState.value = _uiState.value.copy(
                            comics = it.toImmutableList(),
                            loadingState = LoadingState.None
                        )
                    }
                )
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchFilterFlow
                .onEach { _uiState.value = _uiState.value.copy(searchQuery = it.query) }
                .debounce {
                    if (it.query.isNullOrBlank()) 0 else 300
                }
                .onEach { performSearch(it.query) }
                .collect()
        }
    }

    // TODO: Refactor
    private fun replaceFavorite(comic: DisplayComic, isFavorite: Boolean) {
        val comics = _uiState.value.comics.toMutableList()
        val index = comics.indexOf(comic)
        comics[index] = comics[index].copy(isFavorite = isFavorite)
        _uiState.value = _uiState.value.copy(comics = comics.toImmutableList())
    }

    sealed interface UiEvent {
        data object Refresh : UiEvent
        data class Filter(val query: String) : UiEvent
        class AddToWishlist(val comic: DisplayComic) : UiEvent
    }

    class FilterQuery(val query: String?)
}
