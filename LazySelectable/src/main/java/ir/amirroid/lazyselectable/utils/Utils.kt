package ir.amirroid.lazyselectable.utils

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemInfo
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntRect
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize

fun LazyListState.getItemInfoByOffset(
    offset: Offset,
    isRow: Boolean
): LazyListItemInfo? {
    return layoutInfo.visibleItemsInfo.firstOrNull {
        if (isRow) {
            offset.x.toInt() in (it.offset..it.offset.plus(it.size))
        } else {
            offset.y.toInt() in (it.offset..it.offset.plus(it.size))
        }
    }
}


fun LazyGridState.getItemInfoByOffset(
    offset: Offset
): LazyGridItemInfo? {
    return layoutInfo.visibleItemsInfo.firstOrNull {
        it.size.toSize().toRect().contains(offset - it.offset.toOffset())
    }
}


fun LazyStaggeredGridState.getItemInfoByOffset(
    offset: Offset
): LazyStaggeredGridItemInfo? {
    return layoutInfo.visibleItemsInfo.firstOrNull {
        it.size.toSize().toRect().contains(offset - it.offset.toOffset())
    }
}


val DEFAULT_THRESHOLDS_SCROLL = 30.dp