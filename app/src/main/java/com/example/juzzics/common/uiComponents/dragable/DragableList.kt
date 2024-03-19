package com.example.juzzics.common.uiComponents.dragable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.juzzics.features.musics.ui.components.MusicListItem
import com.example.juzzics.features.musics.ui.model.MusicFileUi
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> ReorderableList(
    list: SnapshotStateList<T>,
    onClick: (T) -> Unit,
    scrollThreshold: Dp = 32.dp,
    onDragEnd: (SnapshotStateList<T>) -> Unit
) {
    val lazyListState = rememberLazyListState()

    var initiallyDraggedElement by remember { mutableStateOf<LazyListItemInfo?>(null) }

    var currentIndexOfDraggedItem by remember { mutableStateOf<Int?>(null) }

    var calculatedOffset by remember { mutableStateOf(0f) }

    var draggedDistance by remember { mutableStateOf(0f) }

    val moveIndex = { fromIndex: Int, toIndex: Int -> list.moveAt(fromIndex, toIndex) }

    val scope = rememberCoroutineScope()

    fun resetDragInfo() {
        currentIndexOfDraggedItem = null
        initiallyDraggedElement = null
        draggedDistance = 0f
        calculatedOffset = 0f
    }
//    Column() {
    LazyColumn(
        modifier = Modifier.pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDrag = { change, offset ->
                    change.consume()
                    draggedDistance += offset.y
                    calculatedOffset += offset.y
                    initiallyDraggedElement?.let { initialElement ->
                        val startOffset = initialElement.offset + draggedDistance
                        val endOffset =
                            initialElement.offset + initialElement.size + draggedDistance

                        // Implement autoscroll logic
                        val listHeight = lazyListState.layoutInfo.viewportEndOffset
                        val firstVisibleItem =
                            lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()
                        val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
                        scope.launch {
                            if (startOffset < scrollThreshold.toPx() && firstVisibleItem?.index != 0) {
                                // Scroll up
                                val delta = endOffset - (listHeight - scrollThreshold.toPx())
                                lazyListState.animateScrollBy(delta.coerceAtMost((listHeight - lastVisibleItem!!.offset - lastVisibleItem.size).toFloat()))
                            } else if (endOffset > listHeight - scrollThreshold.toPx() && lastVisibleItem?.index != list.lastIndex) {
                                // Scroll down
                                val delta = scrollThreshold.toPx() - startOffset
                                lazyListState.animateScrollBy(
                                    -delta.coerceAtMost(
                                        firstVisibleItem?.offset?.toFloat() ?: 0f
                                    )
                                )
                            }
                        }
                        println(draggedDistance)
                        initialElement?.let { hovered ->
                            lazyListState.layoutInfo.visibleItemsInfo
                                .filterNot { comparedItem ->
                                    comparedItem.offset + comparedItem.size < startOffset
                                            || comparedItem.offset > endOffset
                                }
                                .firstOrNull { comparedItem ->
                                    if (comparedItem.index == currentIndexOfDraggedItem) return@firstOrNull false
                                    when {
                                        draggedDistance > 0 ->
                                            endOffset > comparedItem.offset + comparedItem.size

                                        else -> startOffset < comparedItem.offset
                                    }
                                }?.let { comparedItem ->
                                    currentIndexOfDraggedItem?.let {
                                        moveIndex.invoke(it, comparedItem.index)
                                        currentIndexOfDraggedItem = comparedItem.index
                                        calculatedOffset = 0f
                                    }
                                }
                        }
                    }
                },
                onDragStart = { offset ->
                    draggedDistance = 0f
                    calculatedOffset = 0f
                    lazyListState.layoutInfo.visibleItemsInfo
                        .firstOrNull { item -> offset.y.toInt() in item.offset..item.offset + item.size }
                        ?.let {
                            currentIndexOfDraggedItem = it.index
                            initiallyDraggedElement = it
                        }
                },
                onDragEnd = {
                    resetDragInfo()
                    onDragEnd.invoke(list)
                },
                onDragCancel = {
                    resetDragInfo()
                }
            )
        },
        state = lazyListState,
    ) {
        itemsIndexed(list, key = { index, item -> item }) { index, item ->
            Column(
                modifier = Modifier
                    .graphicsLayer {
                        translationY =
                            calculatedOffset.takeIf { index == currentIndexOfDraggedItem } ?: 0f
                    }
                    .let {
                        if (index == currentIndexOfDraggedItem) it else it.animateItemPlacement()
                    }) {
                if (item is MusicFileUi) {
                    (item as MusicFileUi).let {
                        MusicListItem(
                            it,
                            onItemClick = onClick as (MusicFileUi) -> Unit
                        )
                    }
                }
            }
        }

    }
//    }
}

fun <T> MutableList<T>.moveAt(oldIndex: Int, newIndex: Int) {
    val item = this[oldIndex]
    removeAt(oldIndex)
    add(newIndex, item)
}

