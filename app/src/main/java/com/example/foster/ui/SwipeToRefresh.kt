package com.example.foster.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private val RefreshDistance = 180.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToRefreshLayout(
    refreshingState: Boolean,
    onRefresh: () -> Unit,
    refreshIndicator: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val refreshDistance = with(LocalDensity.current) {
        RefreshDistance.toPx()
    }
    val state = rememberSwipeableState(refreshingState) { newValue ->
        if (newValue && !refreshingState) onRefresh()
        true
    }

    Box(
        modifier = Modifier
            .nestedScroll(state.PreUpPostDownNestedScrollConnection)
            .swipeable(
                state = state,
                anchors = mapOf(
                    -refreshDistance to false,
                    refreshDistance to true
                ),
                thresholds = { _, _ ->
                    FractionalThreshold(0.5f)
                },
                orientation = Orientation.Vertical
            )
    ) {

        content()
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .offset {
                    IntOffset(0, state.offset.value.roundToInt())
                }
        ) {
            if (state.offset.value != -refreshDistance) {
                refreshIndicator()
            }
        }
        LaunchedEffect(refreshingState) {
            state.animateTo(refreshingState)
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
private val <T> SwipeableState<T>.PreUpPostDownNestedScrollConnection: NestedScrollConnection
    get() = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            return if (delta < 0 && source == NestedScrollSource.Drag) {
                Offset(0f, performDrag(delta = delta))
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            val delta = available.y
            return if (source == NestedScrollSource.Drag) {
                Offset(0f, performDrag(delta = delta))
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            return super.onPreFling(available)
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            return super.onPostFling(consumed, available)
        }
    }