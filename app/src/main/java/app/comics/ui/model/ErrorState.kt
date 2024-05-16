package app.comics.ui.model

sealed interface ErrorState {
    class Error(val description: String) : ErrorState
    data object None : ErrorState
}
