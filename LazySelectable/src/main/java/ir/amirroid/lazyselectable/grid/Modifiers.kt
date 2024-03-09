package ir.amirroid.lazyselectable.grid

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.amirroid.lazyselectable.list.LazyListSelectableState
import ir.amirroid.lazyselectable.utils.DEFAULT_THRESHOLDS_SCROLL
import ir.amirroid.lazyselectable.utils.getItemInfoByOffset
import ir.amirroid.lazyselectable.utils.toItemInfo
import kotlinx.coroutines.delay


fun Modifier.verticalSelectableHandler(
    state: LazyGridSelectableState,
    thresholdsScroll: Dp = DEFAULT_THRESHOLDS_SCROLL,
    consumeChanged: Boolean = true,
) = selectableHandler(state, thresholdsScroll, consumeChanged, false)

fun Modifier.horizontalSelectableHandler(
    state: LazyGridSelectableState,
    thresholdsScroll: Dp = DEFAULT_THRESHOLDS_SCROLL,
    consumeChanged: Boolean = true,
) = selectableHandler(state, thresholdsScroll, consumeChanged, true)


@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.selectableHandler(
    state: LazyGridSelectableState,
    thresholdsScroll: Dp = DEFAULT_THRESHOLDS_SCROLL,
    consumeChanged: Boolean = true,
    isRow: Boolean = false
): Modifier = composed {
    var dragThresholds by remember {
        mutableFloatStateOf(0f)
    }
    LaunchedEffect(key1 = dragThresholds) {
        if (dragThresholds != 0f) {
            while (true) {
                state.lazyGridState.scrollBy(dragThresholds)
                delay(10)
            }
        }
    }
    val lazyListState = state.lazyGridState
    var fromItemInfo: LazyGridItemInfo? = null
    var lastItemInfo: LazyGridItemInfo? = null
    pointerInput(Unit) {
        val thresholdsScrollPx = thresholdsScroll.toPx()
        detectDragGesturesAfterLongPress(
            onDragStart = {
                val info = lazyListState.getItemInfoByOffset(it)
                if (info != fromItemInfo) {
                    state.dragIsProgress = true
                    fromItemInfo = info
                    lastItemInfo = info
                }
            },
            onDragEnd = {
                fromItemInfo = null
                state.dragIsProgress = false
                dragThresholds = 0f
            },
            onDragCancel = {
                fromItemInfo = null
                state.dragIsProgress = false
                dragThresholds = 0f
            },
            onDrag = { change, _ ->
                state.dragIsProgress = true
                if (fromItemInfo != null) {
                    lazyListState.getItemInfoByOffset(change.position)?.let { itemInfo ->
                        val lastIndex = lastItemInfo?.index ?: fromItemInfo!!.index
                        val toIndex = itemInfo.index
                        val fromIndex = fromItemInfo!!.index
                        val removeRangedFilter = state.selected.filter {
                            it.index in fromIndex..lastIndex
                        }.toSet()
                        val removeReverseRangedFilter = state.selected.filter {
                            it.index in lastIndex..fromIndex
                        }.toSet()
                        val rangedFilter = lazyListState.layoutInfo.visibleItemsInfo.filter {
                            it.index in fromIndex..toIndex
                        }.toItemInfo()
                        val reverseRangedFilter = lazyListState.layoutInfo.visibleItemsInfo.filter {
                            it.index in toIndex..fromIndex
                        }.toItemInfo()
                        state.selected = state.selected.minus(
                            removeRangedFilter
                        ).minus(
                            removeReverseRangedFilter
                        ).plus(
                            rangedFilter
                        ).plus(
                            reverseRangedFilter
                        )
                        lastItemInfo = itemInfo
                    }
                    val bottomThreshold =
                        if (isRow) change.position.x - size.width.minus(thresholdsScrollPx) else change.position.y - size.height.minus(
                            thresholdsScrollPx
                        )
                    val topThreshold =
                        if (isRow) thresholdsScrollPx - change.position.x else thresholdsScrollPx - change.position.y
                    dragThresholds = when {
                        bottomThreshold > 0 -> bottomThreshold
                        topThreshold > 0 -> -topThreshold
                        else -> {
                            0f
                        }
                    }
                    if (consumeChanged) {
                        change.consume()
                    }
                }
            }
        )
    }
}