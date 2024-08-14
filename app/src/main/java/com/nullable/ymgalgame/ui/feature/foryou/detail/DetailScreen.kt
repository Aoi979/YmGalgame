package com.nullable.ymgalgame.ui.feature.foryou.detail

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullable.ymgalgame.designsystem.Image
import com.nullable.ymgalgame.ui.theme.Avatar_48
import com.nullable.ymgalgame.ui.theme.Sizing_40
import com.nullable.ymgalgame.ui.theme.Sizing_56
import com.nullable.ymgalgame.ui.theme.Spacing_16
import com.nullable.ymgalgame.ui.theme.Spacing_4
import com.nullable.ymgalgame.ui.theme.Spacing_8
import com.nullable.ymgalgame.util.DynamicText
import com.nullable.ymgalgame.util.extractImageLinksFromHtml
import com.nullable.ymgalgame.util.htmlToAnnotatedString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
//    navController: NavController,
//    viewModel: DetailViewModel = hiltViewModel()
) {
    val html = "<article topicid=\"610953488221863936\">\n" +
            "                    \n" +
            " \n" +
            " \n" +
            "  <p>这画师画风很幼，主页一眼看过去不是萝莉就是大胸萝莉，在国内虽然没什么名气，但推特粉丝还是不少的。这次整的这同人游戏看起来有点东西。</p>\n" +
            "  <p><img src=\"https://store.ymgal.games/topic/content/c9/c94d650c07694c7695cf8b7c9b70f089.jpg\">画师P站：</p>\n" +
            "  <p><img src=\"https://store.ymgal.games/topic/content/34/34ca0c2e07474973a082651d790b7400.jpg\"></p>\n" +
            "  <p><br></p>\n" +
            "  <p>之所以说有点东西呢，是因为我看了下游戏简介，真有活的，不愧是同人作呀，确实有那商业作没有的创意。</p>\n" +
            "  <p><img src=\"https://store.ymgal.games/topic/content/fe/fe45ba6eddff446e95f8d3bea9ed6b0f.jpg\"></p>\n" +
            "  <p>简单来说就是颓废社畜男偶然遇到天降美少女，临时约会了一天，结果美少女途中居然说她杀了自己爹妈？！总之在迷之氛围中主角还是把美少女带回了家，共度一夜后，第二天，美少女自杀了。</p>\n" +
            "  <p>然而，接下来，主角却发现自己回到了与美少女相遇后不久的时间、地点……</p>\n" +
            "  <p>……</p>\n" +
            "  <p>你别说，还真有点意思。轮回系！ 还有秋刀鱼配音，可以说是一部有期待价值的游戏。</p>\n" +
            "  <p><img src=\"https://store.ymgal.games/topic/content/a8/a87d9d2a38e14784afde0de1e84ee511.jpg\"></p>\n" +
            "  <p><br></p>\n" +
            "  <p>等12月份吧，这游戏在冬comic发售，现在官网内容还是很齐全的，人设、配音试听、CG都有，有兴趣的可以自行去看。</p>\n" +
            "  <p>游戏官网：</p>\n" +
            "  <p><a href=\"/linkfilter?url=https%3A%2F%2Fpokapokausagi18.wixsite.com%2Fboukyakunoedicius\" target=\"_blank\" rel=\"nofollow\">https://pokapokausagi18.wixsite.com/boukyakunoedicius</a><br></p>\n" +
            "  <p><br></p>\n" +
            "  <p><br></p>\n" +
            " \n" +
            "\n" +
            "                </article>"
//viewmodel处理
    val annotatedString = htmlToAnnotatedString(html)
    val a = extractImageLinksFromHtml(html)


    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()
    val medium = MaterialTheme.typography.headlineMedium
    val small = MaterialTheme.typography.bodyLarge
    val bottomSheetState = rememberModalBottomSheetState()
    var titleStyle by remember { mutableStateOf(medium) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    LaunchedEffect(scrollBehavior.state.heightOffset) {
        if (scrollBehavior.state.heightOffset <= -130.0f && titleStyle == medium) {
            titleStyle = small
        } else if (scrollBehavior.state.heightOffset >= -130.0f && titleStyle == small) {
            titleStyle = medium
        }
    }


    Scaffold(
        topBar = {
            LargeTopAppBar(
                actions = {
                    IconButton({}) {
                        Icon(
                            Icons.Rounded.Menu, "button"
                        )
                    }
                },
                navigationIcon = {
                    IconButton({}) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, "back")
                    }

                },
                title = {
                    Text(
                        text = "对《素晴日》第二章（间宫卓司）的考查（四）",
                        fontFamily = FontFamily.Serif,
                        style = titleStyle,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar {
                FloatingActionButton(onClick = {
                    scope.launch {
                        bottomSheetState.partialExpand()
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Rounded.Send,
                        "send"
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = Spacing_16)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(scrollState)
        ) {
            ProfileBar()
            Spacer(modifier = Modifier.height(16.dp))
            DynamicText(annotatedString, a)
//            ModalBottomSheet(
//                onDismissRequest = {
//                    scope.launch {
//                        bottomSheetState.hide()
//                    }
//                },
//                sheetState = bottomSheetState
//            ) {
//
//            }

        }

    }


}


@Composable
fun ProfileBar() {
    Row(
        modifier = Modifier.padding(
            vertical = 8.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing_8)
    ) {
        Image("https://store.ymgal.games/avatar/c7/c787fd97e4784b059e0cd8389c0de5d6.webp",
            shimmerSize = Avatar_48,
            modifier = Modifier.size(Avatar_48))
        SelectionContainer {
            Text(
                text = "义无反顾",
                modifier = Modifier.clickable {
                }
            )
        }
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Rounded.DateRange,
            contentDescription = "Date",
            modifier = Modifier.size(16.dp)
        )
        Text(text = "2天前",
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.labelMedium)


        Icon(
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = "Viewer Number",
            modifier = Modifier.size(16.dp)
        )
        Text(text = "2279",
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.labelMedium)


    }

}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen()
}