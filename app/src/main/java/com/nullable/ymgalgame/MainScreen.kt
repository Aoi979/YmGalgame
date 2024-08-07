package com.nullable.ymgalgame

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.nullable.ymgalgame.ui.feature.archives.ArchiveScreen
import com.nullable.ymgalgame.ui.feature.foryou.ForYouScreen
import com.nullable.ymgalgame.ui.feature.picset.PicsetScreen
import com.nullable.ymgalgame.ui.theme.Duration_Long2
import com.nullable.ymgalgame.ui.theme.FadingAlpha
import com.nullable.ymgalgame.ui.theme.Scaling


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    MainContent(
        navController
    ) {
        composable(
            route = "For You",
            enterTransition = {
                scaleIn(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    initialScale = Scaling,
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    initialAlpha = FadingAlpha,
                )
            },
            exitTransition = {
                scaleOut(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    targetScale = Scaling,
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    targetAlpha = FadingAlpha,
                )
            },
        ) {
            ForYouScreen()
        }
        
        composable(
            route = "Archives",
            enterTransition = {
                scaleIn(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    initialScale = Scaling,
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    initialAlpha = FadingAlpha,
                )
            },
            exitTransition = {
                scaleOut(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    targetScale = Scaling,
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    targetAlpha = FadingAlpha,
                )
            },
        ){
            ArchiveScreen()
        }


        composable(
            route = "Picset",
            enterTransition = {
                scaleIn(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    initialScale = Scaling,
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    initialAlpha = FadingAlpha,
                )
            },
            exitTransition = {
                scaleOut(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    targetScale = Scaling,
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = Duration_Long2
                    ),
                    targetAlpha = FadingAlpha,
                )
            },
        ){
            PicsetScreen()
        }}
}


val navItems = listOf("For You", "Archives", "Picset")
val iconItems by lazy { listOf(Icons.Filled.Home, Icons.Filled.MailOutline, Icons.Filled.Star) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavHostController,
    startDestination: String = "For You",
    navGraphBuilder: NavGraphBuilder.() -> Unit,
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            navItems.forEachIndexed { index, item ->
                item(
                    icon = { Icon(iconItems[index], contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index
                        navController.navigate(item)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                MediumTopAppBar(
                    title = { Text("YmGalagme") }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    builder = navGraphBuilder
                )
            }


        }


    }

}


@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}