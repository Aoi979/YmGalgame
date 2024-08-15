package com.nullable.ymgalgame.ui.feature.foryou

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.nullable.ymgalgame.designsystem.YmCard
import com.nullable.ymgalgame.ui.feature.foryou.model.DataCategory
import com.nullable.ymgalgame.ui.theme.Spacing_8
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ForYouScreen(
    navController: NavController,
    viewModel: ForYouViewModel
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val lazyVerticalGridStateL = remember { viewModel.lazyVerticalGridStateL }
    val lazyVerticalGridStateR = remember { viewModel.lazyVerticalGridStateR }
    val pagerState = rememberPagerState { 2 }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var tabIndexState by remember { mutableIntStateOf(0) }
    val primaryContainerColor = TabRowDefaults.primaryContainerColor
    val mediumTopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors().scrolledContainerColor
    var tabState by remember { mutableStateOf(primaryContainerColor) }
    var columns by remember { mutableIntStateOf(1) }
    LaunchedEffect(scale) {
        if (scale > 2) {
            columns = 2
        } else if (scale < 1) {
            columns = 1
        }
    }
    LaunchedEffect(scrollBehavior.state.heightOffset) {
        if (scrollBehavior.state.heightOffset < 0) {
            tabState = mediumTopAppBarColors
        } else {
            tabState = primaryContainerColor
        }
    }
    Scaffold(
        topBar = {
            MediumTopAppBar(
                actions = {
                    IconButton(onClick = {
                        if (columns == 1) {
                            columns = 2
                        } else {
                            columns = 1
                        }

                    }) {
                        Icon(Icons.Rounded.MoreVert, "more")
                    }
                },
                title = { Text("YmGalagme") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { PaddingValues ->
        Column(Modifier.padding(PaddingValues)) {
            PrimaryTabRow(
                selectedTabIndex = tabIndexState,
                containerColor = tabState
            ) {
                viewModel.sources.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndexState == pagerState.currentPage,
                        onClick = {
                            tabIndexState = index
                            coroutineScope.launch(Dispatchers.Main) {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = title) }
                    )
                }
            }
            if (state.isLoading) {
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) { page ->
                    if (page == 0) {
                        PullToRefreshBox(
                            isRefreshing = isRefreshing,
                            onRefresh = {
                                viewModel.viewModelScope.launch {
                                    isRefreshing = true
                                    viewModel.updateTopics(dataCategory = DataCategory.NEWS)
                                    isRefreshing = false
                                }
                            }
                        ) {
                            LazyVerticalGrid(
                                state = lazyVerticalGridStateL,
                                modifier = Modifier
                                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                                    .pointerInput(Unit) {
                                        detectTransformGestures { centroid, pan, zoom, rotation ->
                                            scale *= zoom
                                        }
                                    },
                                columns = GridCells.Fixed(columns),

                                ) {
                                items(state.newsTopics) {
                                    AnimatedContent(
                                        it, label = "topicCard",
                                        transitionSpec = {
                                            (fadeIn(
                                                animationSpec = tween(
                                                    220,
                                                    delayMillis = 90
                                                )
                                            ) + expandVertically()).togetherWith(
                                                fadeOut(
                                                    animationSpec = tween(90)
                                                )
                                            )


                                        },
                                        modifier = Modifier.animateItem()
                                    ) {
                                        YmCard(
                                            modifier = Modifier
                                                .padding(Spacing_8),
                                            imageURL = it.mainImg,
                                            headline = it.title,
                                            subhead = it.createAt
                                        )

                                    }

                                }
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            state = lazyVerticalGridStateR,
                            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                            columns = GridCells.Fixed(1),

                            ) {
                            items(state.impressionsTopics) {
                                YmCard(
                                    modifier = Modifier.padding(Spacing_8),
                                    imageURL = it.mainImg,
                                    headline = it.title,
                                    subhead = it.createAt
                                )
                            }
                        }
                    }
                }

            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }
        }
    }

}

@Preview
@Composable
fun Preview() {

}