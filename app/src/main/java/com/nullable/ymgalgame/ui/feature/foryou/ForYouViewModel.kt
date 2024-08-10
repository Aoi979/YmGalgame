package com.nullable.ymgalgame.ui.feature.foryou

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullable.ymgalgame.ui.feature.foryou.model.DataCategory
import com.nullable.ymgalgame.ui.feature.foryou.model.Topic
import com.nullable.ymgalgame.ui.feature.foryou.model.TopicCategory
import com.nullable.ymgalgame.ui.feature.foryou.repository.ForYouRepository
import com.nullable.ymgalgame.ui.feature.foryou.repository.ForYouRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.future.future
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val repository: ForYouRepository,

    ) : ViewModel(), ForYouRepository by repository {

    init {
        viewModelScope.launch {
            val a = async { collectTopics(1, DataCategory.NEWS) }
            val b = async { collectTopics(1, DataCategory.COLUMN) }
            joinAll(a, b)
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
        }
    }

    val sources = listOf("New", "Impressions")

    val lazyVerticalGridStateL = LazyGridState()

    val lazyVerticalGridStateR = LazyGridState()

    private val _state: MutableStateFlow<ForYouState> = MutableStateFlow(ForYouState())

    val state: StateFlow<ForYouState> = _state.asStateFlow()

    suspend fun collectTopics(page: Int, dataCategory: DataCategory) {
        withContext(Dispatchers.IO) {
            getTopics(page, dataCategory).fold(
                ifLeft = { updateMessage("Check your network") },
                ifRight = { it ->
                    it.collect { it1 ->
                        it1.fold(
                            ifLeft = { updateMessage("The server-side data is abnormal") },
                            ifRight = { updateTopics(dataCategory, it.topics) }
                        )
                    }
                }
            )
        }
    }

    private suspend fun updateMessage(message: String) {
        withContext(Dispatchers.Main) {
            _state.update {
                it.copy(message = message)
            }
        }
    }

    private suspend fun updateTopics(dataCategory: DataCategory, topics: List<Topic>) {
        withContext(Dispatchers.Main) {
            _state.update {
                when (dataCategory) {
                    DataCategory.NEWS -> it.copy(isLoading = true, newsTopics = topics)
                    DataCategory.COLUMN -> it.copy(
                        impressionsTopics = topics
                    )
                }
            }
        }
    }
}
