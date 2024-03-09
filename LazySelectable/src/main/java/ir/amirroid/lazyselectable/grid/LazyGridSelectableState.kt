package ir.amirroid.lazyselectable.grid

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import ir.amirroid.lazyselectable.utils.LazyItemInfo
import ir.amirroid.lazyselectable.utils.toItemInfo


@Stable
class LazyGridSelectableState(
    val lazyGridState: LazyGridState
) {
    var selected by mutableStateOf<Set<LazyItemInfo>>(emptySet())

    var dragIsProgress by mutableStateOf(false)


    fun unselectAll() {
        selected = emptySet()
    }

    fun selectByIndex(index: Int) {
        if (index in lazyGridState.layoutInfo.visibleItemsInfo.map { it.index }) {
            selected =
                selected.plus(lazyGridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                    .toItemInfo())
        } else {
            throw IllegalArgumentException(
                "Index is not displayed"
            )
        }
    }


    fun selectByKey(key: Any) {
        if (key in lazyGridState.layoutInfo.visibleItemsInfo.map { it.key }) {
            selected =
                selected.plus(lazyGridState.layoutInfo.visibleItemsInfo.first { it.key == key }
                    .toItemInfo())
        } else {
            throw java.lang.IllegalArgumentException(
                "Index is not displayed"
            )
        }
    }

    fun unSelectByIndex(index: Int) {
        selected.firstOrNull { it.index == index }?.let {
            selected = selected.minus(it)
        }
    }

    
    fun unSelectByKey(key: Any) {
        selected.firstOrNull { it.key == key }?.let {
            selected = selected.minus(it)
        }
    }


    companion object {
        fun saver(lazyListState: LazyGridState): Saver<LazyGridSelectableState, List<Map<String, *>>> =
            Saver(save = { state ->
                state.selected.map {
                    mapOf(
                        "index" to it.index,
                        "offset" to it.offset,
                        "size" to it.size,
                        "contentType" to it.contentType,
                        "key" to it.key,
                    )
                }
            }, restore = {
                LazyGridSelectableState(lazyListState).apply {
                    selected = it.map {
                        LazyItemInfo(
                            it["index"] as Int,
                            it["key"] as Any,
                            it["offset"] as Offset,
                            it["size"] as Size,
                            it["contentType"],
                        )
                    }.toSet()
                }
            })

    }
}


@Composable
fun rememberLazyGridSelectableState(lazyGridState: LazyGridState): LazyGridSelectableState {
    return rememberSaveable(saver = LazyGridSelectableState.saver(lazyGridState)) {
        LazyGridSelectableState(lazyGridState)
    }
}