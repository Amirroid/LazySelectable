package ir.amirroid.lazyselectable.utils

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemInfo
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize

@Immutable
data class LazyItemInfo(
    val index: Int,
    val key: Any,
    val offset: Offset,
    val size: Size,
    val contentType: Any? = null
)

fun LazyListItemInfo.toItemInfo(
    isRow: Boolean
): LazyItemInfo {
    return LazyItemInfo(
        index,
        key,
        if (isRow) Offset(offset.toFloat(), 0f) else Offset(0f, offset.toFloat()),
        if (isRow) Size(size.toFloat(), 0f) else Size(0f, size.toFloat()),
        contentType
    )
}

fun LazyGridItemInfo.toItemInfo(): LazyItemInfo {
    return LazyItemInfo(
        index,
        key,
        offset.toOffset(),
        size.toSize(),
        contentType
    )
}


fun LazyStaggeredGridItemInfo.toItemInfo(): LazyItemInfo {
    return LazyItemInfo(
        index,
        key,
        offset.toOffset(),
        size.toSize(),
        contentType
    )
}

fun List<LazyListItemInfo>.toItemInfo(
    isRow: Boolean
): Set<LazyItemInfo> {
    return map { it.toItemInfo(isRow) }.toSet()
}

fun List<LazyGridItemInfo>.toItemInfo(): Set<LazyItemInfo> {
    return map { it.toItemInfo() }.toSet()
}


fun List<LazyStaggeredGridItemInfo>.staggeredToItemInfo(): Set<LazyItemInfo> {
    return map { it.toItemInfo() }.toSet()
}