package com.nullable.ymgalgame.ui.feature.foryou

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.nullable.ymgalgame.designsystem.YmCard
import com.nullable.ymgalgame.ui.theme.Spacing_8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForYouScreen(
    navController: NavController,
    viewModel: ForYouViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sources = listOf("New", "Impressions")
    val state by viewModel.state.collectAsStateWithLifecycle()
    var tabIndexState by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("YmGalagme") },
                scrollBehavior = scrollBehavior
            )
        }
    ){ PaddingValues->
        Column {
            PrimaryTabRow(selectedTabIndex = tabIndexState) {
                sources.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndexState == index,
                        onClick = { tabIndexState = index },
                        text = { Text(text = title) }
                    )
                }
            }

            if (state.isLoading) {
                LazyVerticalGrid(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues
                ) {
                    items(state.newsTopics) {
                        YmCard(
                            modifier = Modifier.padding(Spacing_8),
                            imageURL = it.mainImg,
                            headline = it.title,
                            subhead = it.createAt
                        )
                    }
                }
            } else {
                CircularProgressIndicator()
            }
    }




//        when(tabIndexState){
//
//        }
//


    }

}

@Preview
@Composable
fun Preview() {
}