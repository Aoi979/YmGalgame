package com.nullable.ymgalgame.ui.feature.foryou

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForYouScreen(
//    viewModel: ForYouViewModel = hiltViewModel(),
){
    val sources = listOf("news","column")
    var tabIndexState by remember{ mutableStateOf(0) }
    Column {
        PrimaryTabRow(selectedTabIndex = tabIndexState) {
            sources.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndexState == index,
                    onClick = { tabIndexState = index},
                    text = { Text(text = title) }
                )
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
fun Preview(){
    ForYouScreen()
}