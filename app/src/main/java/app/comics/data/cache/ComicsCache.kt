package app.comics.data.cache

import app.comics.ui.model.DisplayComic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

// TODO: Implement caching using the database instead
@Singleton
class ComicsCache @Inject constructor() {

    private val _data = MutableStateFlow<Set<DisplayComic>>(emptySet())
    val data: StateFlow<Set<DisplayComic>> = _data

    fun add(comic: DisplayComic) {
        _data.value = _data.value.mutate { add(comic) }
    }

    fun remove(comic: DisplayComic) {
        _data.value = _data.value.mutate { remove(comic) }
    }
}


private fun <T> Set<T>.mutate(transformation: MutableSet<T>.() -> Unit): Set<T> {
    return this.toMutableSet().apply(transformation)
}
