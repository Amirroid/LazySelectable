package ir.amirroid.lazyselectable.list

import androidx.compose.foundation.lazy.LazyListState
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
import java.lang.IllegalArgumentException


@Stable
class LazyListSelectableState(
    val lazyListState: LazyListState
) {
    var selected by mutableStateOf<Set<LazyItemInfo>>(emptySet())

    var dragIsProgress by mutableStateOf(false)

    var isRow by mutableStateOf(false)


    fun unselectAll() {
        selected = emptySet()
    }

    fun selectByIndex(index: Int) {
        if (index in lazyListState.layoutInfo.visibleItemsInfo.map { it.index }) {
            selected =
                selected.plus(lazyListState.layoutInfo.visibleItemsInfo.first { it.index == index }
                    .toItemInfo(isRow)
                )
        } else {
            throw IllegalArgumentException(
                "Index is not displayed"
            )
        }
    }

    fun selectByKey(key: Any) {
        if (key in lazyListState.layoutInfo.visibleItemsInfo.map { it.key }) {
            selected = selected.plus(lazyListState.layoutInfo.visibleItemsInfo.first { it.key == key }
                    .toItemInfo(isRow)
                )
        } else {
            throw IllegalArgumentException(
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
        fun saver(lazyListState: LazyListState): Saver<LazyListSelectableState, List<Map<String, *>>> =
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
                LazyListSelectableState(lazyListState).apply {
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
fun rememberLazyListSelectableState(lazyListState: LazyListState): LazyListSelectableState {
    return rememberSaveable(saver = LazyListSelectableState.saver(lazyListState)) {
        LazyListSelectableState(lazyListState)
    }
}