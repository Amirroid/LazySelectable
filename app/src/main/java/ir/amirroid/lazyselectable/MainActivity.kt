package ir.amirroid.lazyselectable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ir.amirroid.lazyselectable.grid.horizontalSelectableHandler
import ir.amirroid.lazyselectable.grid.rememberLazyGridSelectableState
import ir.amirroid.lazyselectable.grid.selectableHandler
import ir.amirroid.lazyselectable.list.horizontalSelectableHandler
import ir.amirroid.lazyselectable.list.rememberLazyListSelectableState
import ir.amirroid.lazyselectable.list.selectableHandler
import ir.amirroid.lazyselectable.list.verticalSelectableHandler
import ir.amirroid.lazyselectable.staggered.rememberLazyGridStaggeredSelectableState
import ir.amirroid.lazyselectable.staggered.verticalSelectableHandler
import ir.amirroid.lazyselectable.ui.theme.LazySelectableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazySelectableTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val listState = rememberLazyStaggeredGridState()
                    val state = rememberLazyGridStaggeredSelectableState(listState)
                    LazyVerticalStaggeredGrid(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalSelectableHandler(state),
                        columns = StaggeredGridCells.Fixed(3)
                    ) {
                        items(20) {
                            val plusHeight = it * 3
                            Box(modifier = Modifier
                                .padding(12.dp)
                                .height(200.dp + 1.dp * plusHeight)
                                .width(120.dp)
                                .background(
                                    if (state.selected.any { d -> d.index == it }) Color.Red else Color.Blue
                                )
                                .clickable {
                                    state.selectByIndex(it)
                                })
                        }
                    }
                }
            }
        }
    }
}