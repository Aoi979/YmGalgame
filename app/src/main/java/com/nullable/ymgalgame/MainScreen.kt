package com.nullable.ymgalgame

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nullable.ymgalgame.ui.feature.archives.ArchiveScreen
import com.nullable.ymgalgame.ui.feature.foryou.ForYouScreen
import com.nullable.ymgalgame.ui.feature.foryou.ForYouViewModel
import com.nullable.ymgalgame.ui.feature.picset.PicsetScreen
import com.nullable.ymgalgame.ui.theme.Duration_Medium2


@Composable
fun MainScreen() {
    val forYouViewModel: ForYouViewModel = hiltViewModel()
    val navController = rememberNavController()
    MainContent(
        navController
    ) {


        composable(
            route = "Archives",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = Duration_Medium2
                    ),
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = Duration_Medium2
                    ),
                )
            },
        ) {
            ArchiveScreen()
        }


        composable(
            route = "Picset",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = Duration_Medium2
                    ),
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = Duration_Medium2
                    ),
                )
            },
        ) {
            PicsetScreen()
        }
//        navigation(
//            route = "For_You",
//            startDestination ="For You"
//        ){
        composable(
            route = "For You",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = Duration_Medium2
                    ),
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = Duration_Medium2
                    ),
                )
            },
        ) {
            ForYouScreen(navController, forYouViewModel)
        }
//        }
    }


}


val navItems = listOf("For You", "Archives", "Picset")
val iconItems by lazy { listOf(Icons.Filled.Home, Icons.Filled.MailOutline, Icons.Filled.Star) }

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
                    onClick = {
                        selectedItem = index
                        navController.navigate(item)
                    }
                )
            }
        }
    ) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    builder = navGraphBuilder
                )




    }

}


@Preview
@Composable
fun PreviewMainScreen() {
}