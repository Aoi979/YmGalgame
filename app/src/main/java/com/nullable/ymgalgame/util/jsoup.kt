package com.nullable.ymgalgame.util

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode


 fun AnnotatedString.Builder.appendElement(element: Element) {
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