package app.comics.ui.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ComicListUiState(
    val comics: ImmutableList<DisplayComic>,
    val searchQuery: String?,
    val loadingState: LoadingState,
    val errorState: ErrorState
) {
    companion object Factory {
        fun make(
            comics: List<DisplayComic> = emptyList(),
            searchQuery: String? = null,
            loadingState: LoadingState = LoadingState.None,
            errorState: ErrorState = ErrorState.None
        ): ComicListUiState {
            return ComicListUiState(
                comics = comics.toImmutableList(),
                searchQuery = searchQuery,
                loadingState = loadingState,
                errorState = errorState
            )
        }
    }
}
