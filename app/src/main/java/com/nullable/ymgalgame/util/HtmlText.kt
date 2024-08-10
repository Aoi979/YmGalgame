package com.nullable.ymgalgame.util

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

fun preloadImage(context: Context, imageUrl: String, onImageLoaded: (IntSize) -> Unit) {
    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .allowHardware(false)
        .listener(onSuccess = { _, result ->
            val size = IntSize(result.drawable.intrinsicWidth, result.drawable.intrinsicHeight)
            onImageLoaded(size)
        })
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        val imageLoader = ImageLoader(context)
        imageLoader.execute(imageRequest)
    }
}


@Composable
fun DynamicText(annotatedString: AnnotatedString, imageList: List<String>) {
    val map = remember { mutableStateMapOf<String, InlineTextContent>() }
    val maxScreenWidthSp = maxScreenWidthSp()
    val content = LocalContext.current

    LaunchedEffect(imageList) {
        imageList.forEach { image ->
            preloadImage(content, image) { imageSize ->
                val ratio = imageSize.width.toFloat() / imageSize.height.toFloat()
                val placeholderHeightSp = (maxScreenWidthSp.value / ratio).sp

                val placeholder = Placeholder(
                    width = maxScreenWidthSp,
                    height = placeholderHeightSp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )

                map[image] = InlineTextContent(
                    placeholder = placeholder
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = "Image",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

    Text(
        text = annotatedString,
        inlineContent = map
    )
}

@Composable
fun maxScreenWidthSp(): TextUnit {

    val configuration = LocalConfiguration.current

    val screenWidthDp = configuration.screenWidthDp.dp

    return with(LocalDensity.current) { screenWidthDp.toSp() }
}

@Composable
fun htmlToAnnotatedString(html: String): AnnotatedString {
    val document = Jsoup.parse(html)
    val body = document.body()

    return buildAnnotatedString {
        appendElement(body)
    }
}

private fun AnnotatedString.Builder.appendElement(element: Element) {
    for (node in element.childNodes()) {
        when (node) {
            is TextNode -> append(node.text())
            is Element -> {
                when (node.tagName()) {
                    "b", "strong" -> withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        appendElement(node)
                    }

                    "i", "em" -> withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                        appendElement(node)
                    }

                    "u" -> withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        appendElement(node)
                    }

                    "a" -> {
                        val url = node.attr("href")
                        pushStringAnnotation(tag = "URL", annotation = url)
                        withStyle(
                            SpanStyle(
                                color = androidx.compose.ui.graphics.Color.Blue,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            appendElement(node)
                        }
                        pop()
                    }

                    "p" -> {
                        appendElement(node)
                        append("\n\n")
                    }

                    "br" -> append("\n")
                    "img" -> {
                        append("\n")
                        appendInlineContent(node.absUrl("src"))

                    }

                    else -> appendElement(node)
                }
            }
        }
    }
}


fun extractImageLinksFromHtml(html: String): List<String> {
    val imgLinks = mutableListOf<String>()
    val doc: Document = Jsoup.parse(html)

    val imgElements = doc.select("img")

    for (img: Element in imgElements) {
        val imgSrc = img.absUrl("src")
        imgLinks.add(imgSrc)
    }
    return imgLinks
}

@Preview
@Composable
fun Previewtext() {
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
    val annotatedString = htmlToAnnotatedString(html)

    val a = extractImageLinksFromHtml(html)
    DynamicText(annotatedString, a)

}