package com.nullable.ymgalgame.ui.feature.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullable.ymgalgame.ui.feature.foryou.model.Topic
import com.nullable.ymgalgame.ui.feature.foryou.model.TopicCategory
import com.nullable.ymgalgame.ui.feature.foryou.repository.ForYouRepository
import com.nullable.ymgalgame.ui.feature.foryou.repository.ForYouRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val repository: ForYouRepository,

) : ViewModel(), ForYouRepository by repository {
    override fun onCleared() {
        println("老子他妈被清除了 为什么啊啊啊啊")
        super.onCleared()
    }

    init {
        println("你爹正在初始化啊啊啊啊啊啊")
        viewModelScope.launch {
                collectTopics(2, TopicCategory.New)

        }
    }

    private val _state: MutableStateFlow<ForYouState> = MutableStateFlow(ForYouState())

    val state: StateFlow<ForYouState> = _state.asStateFlow()

    suspend fun collectTopics(page: Int, topicCategory: TopicCategory) {
        withContext(Dispatchers.IO) {
            getTopics(page, topicCategory).fold(
                ifLeft = { updateMessage("Check your network") },
                ifRight = { it ->
                    it.collect { it1 ->
                        it1.fold(
                            ifLeft = { updateMessage("The server-side data is abnormal") },
                            ifRight = { updateTopics(topicCategory,it.topics) }
                        )
                    }
                }
            )
        }
    }

    private suspend fun updateMessage(message: String) {
        withContext(Dispatchers.Main){
            _state.update {
                it.copy(message = message)
            }
        }
    }

    private suspend fun updateTopics(topicCategory: TopicCategory, topics: List<Topic>) {
        withContext(Dispatchers.Main){
            _state.update {
                when (topicCategory) {
                    TopicCategory.New -> it.copy(isLoading = true, newsTopics = topics)
                    TopicCategory.Impressions -> it.copy(isLoading = true, impressionsTopics = topics)
                }
            }
        }
    }
}
