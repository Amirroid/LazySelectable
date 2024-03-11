package ir.amirroid.lazyselectable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.lazy.grid.items
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
import ir.amirroid.lazyselectable.grid.verticalSelectableHandler
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
                    val lazyGridState = rememberLazyGridState()
                    val lazyGridSelectableState =
                        rememberLazyGridSelectableState(lazyGridState = lazyGridState)

                    LazyVerticalGrid(
                        state = lazyGridState,
                        modifier = Modifier.verticalSelectableHandler(lazyGridSelectableState),
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(100) { index ->
                            val selected =
                                lazyGridSelectableState.selected.any { info -> info.index == index }
                            val color = if (selected) Color.Red else Color.Blue
                            Box(
                                modifier = Modifier
                                    .background(color)
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}