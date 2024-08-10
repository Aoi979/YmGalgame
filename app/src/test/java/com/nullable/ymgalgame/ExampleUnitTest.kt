package com.nullable.ymgalgame

import com.nullable.ymgalgame.ui.feature.foryou.model.Topic
import com.nullable.ymgalgame.ui.feature.foryou.model.TopicCategory
import com.nullable.ymgalgame.ui.feature.foryou.model.TopicsResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ResponseTest {
    @Test
    fun Serializer() {
        val response = TopicsResponse(
            isSuccess = true, code = 0, listOf(
                Topic(
                    topicId = 493873579922817024,
                    author = 6844,
                    mainImg = "https://store.ymgal.games/topic/main/0b/0b24c365239c4692a3477b78c1686781.webp",
                    title = "来了，鼎鼎有名的艹猫开发商NEKO WORKs新的剧情短篇steam商店页上线了。",
                    introduction = "游戏是《赘之匣庭》，会上steam，自带中文，发行商是Sekai Project。其实21年的时候 NEKO WORKs 就有过制作预告和宣发，过了这么久可算是出来了，预计正式发售时间是2024春。 值得一提的是这游戏是 NEKO WORKs 这边新开了一个品牌做的，和艹猫不共用一个品牌。姊妹社团是叫 Chatte Noire。    现在steam的商店页",
                    views = 9009,
                    replyNum = 0,
                    likesNum = 0,
                    favoritesNum = 0,
                    publishTime = "2023-09-24 20:11:40",
                    createAt = "新月酱",
                    topicCategory = TopicCategory.New,
                    publishTimeText = "10"
                ), Topic(
                    topicId = 493873579922817024,
                    author = 6844,
                    mainImg = "https://store.ymgal.games/topic/main/0b/0b24c365239c4692a3477b78c1686781.webp",
                    title = "来了，鼎鼎有名的艹猫开发商NEKO WORKs新的剧情短篇steam商店页上线了。",
                    introduction = "游戏是《赘之匣庭》，会上steam，自带中文，发行商是Sekai Project。其实21年的时候 NEKO WORKs 就有过制作预告和宣发，过了这么久可算是出来了，预计正式发售时间是2024春。 值得一提的是这游戏是 NEKO WORKs 这边新开了一个品牌做的，和艹猫不共用一个品牌。姊妹社团是叫 Chatte Noire。    现在steam的商店页",
                    views = 9009,
                    replyNum = 0,
                    likesNum = 0,
                    favoritesNum = 0,
                    publishTime = "2023-09-24 20:11:40",
                    createAt = "新月酱",
                    topicCategory = TopicCategory.New,
                    publishTimeText = "10"
                )
            )
        )

        val a = Json.encodeToString(
            TopicsResponse.serializer(), response

        )
        val b = decodeFromString<TopicsResponse>(
            "{\"success\":true,\"code\":0,\"data\":[{\"author\":6844,\"createAt\":\"新月酱\",\"favoritesNum\":0,\"introduction\":\"游戏是《赘之匣庭》，会上steam，自带中文，发行商是Sekai Project。其实21年的时候 NEKO WORKs 就有过制作预告和宣发，过了这么久可算是出来了，预计正式发售时间是2024春。 值得一提的是这游戏是 NEKO WORKs 这边新开了一个品牌做的，和艹猫不共用一个品牌。姊妹社团是叫 Chatte Noire。    现在steam的商店页\",\"likesNum\":0,\"mainImg\":\"https://store.ymgal.games/topic/main/0b/0b24c365239c4692a3477b78c1686781.webp\",\"publishTime\":\"2023-09-24 20:11:40\",\"publishTimeText\":\"10\",\"replyNum\":0,\"title\":\"来了，鼎鼎有名的艹猫开发商NEKO WORKs新的剧情短篇steam商店页上线了。\",\"topicCategory\":\"资讯\",\"topicId\":493873579922817024,\"views\":9009},{\"author\":6844,\"createAt\":\"新月酱\",\"favoritesNum\":0,\"introduction\":\"游戏是《赘之匣庭》，会上steam，自带中文，发行商是Sekai Project。其实21年的时候 NEKO WORKs 就有过制作预告和宣发，过了这么久可算是出来了，预计正式发售时间是2024春。 值得一提的是这游戏是 NEKO WORKs 这边新开了一个品牌做的，和艹猫不共用一个品牌。姊妹社团是叫 Chatte Noire。    现在steam的商店页\",\"likesNum\":0,\"mainImg\":\"https://store.ymgal.games/topic/main/0b/0b24c365239c4692a3477b78c1686781.webp\",\"publishTime\":\"2023-09-24 20:11:40\",\"publishTimeText\":\"10\",\"replyNum\":0,\"title\":\"来了，鼎鼎有名的艹猫开发商NEKO WORKs新的剧情短篇steam商店页上线了。\",\"topicCategory\":\"资讯\",\"topicId\":493873579922817024,\"views\":9009}]}\n"
        )

        assertEquals(b, response)
    }
}

class NetworkTest {

}