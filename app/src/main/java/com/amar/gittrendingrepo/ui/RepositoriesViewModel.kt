package com.amar.gittrendingrepo.ui

import androidx.lifecycle.ViewModel
import com.amar.gittrendingrepo.model.Repository
import com.amar.gittrendingrepo.model.enums.SortOption
import com.amar.gittrendingrepo.repository.GitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: GitRepository
) : ViewModel(){

    private val _listStateFlow = MutableStateFlow(listOf<Repository>())
    val listStateFlow = _listStateFlow.asStateFlow()
    var sortOption: SortOption = SortOption.DEFAULT
    lateinit var repositoryList: List<Repository>

    private val _showProgressBarFlow = MutableStateFlow(false)
    val showProgressBarFlow = _showProgressBarFlow.asStateFlow()

    private val _menuSelectedFlow = MutableStateFlow(SortOption.DEFAULT)
    val menuSelectedFlow = _menuSelectedFlow.asStateFlow()

    suspend fun getTrendingRepositories()  {
        _showProgressBarFlow.value = true
        repositoryList = repository.getTrendingRepositories()
        sortRepositoryList()
        _showProgressBarFlow.value = false
    }

    fun changeSortOption(sortOption: SortOption) {
        if (this.sortOption != sortOption) {
            this.sortOption = sortOption
            _menuSelectedFlow.value = sortOption
            sortRepositoryList()
        }
    }

    private fun sortRepositoryList() {

        if (sortOption == SortOption.DEFAULT) {
            System.out.println("LUBA defaylt")
            Collections.sort(repositoryList,
                Comparator<Repository> { o1, o2 -> o1?.rank?.compareTo(o2.rank) ?: 0 })
        }
        else if (sortOption == SortOption.BY_STARS) {
            System.out.println("LUBA starts")
            Collections.sort(repositoryList,
                Comparator<Repository> { o1, o2 -> o2?.startsCount?.compareTo(o1.startsCount) ?: 0 })

        }
        else if (sortOption == SortOption.BY_FORKS) {
            System.out.println("LUBA forks")
            Collections.sort(repositoryList,
                Comparator<Repository> { o1, o2 -> o2?.forkCount?.compareTo(o1.forkCount) ?: 0 })
        }

        _listStateFlow.value = repositoryList.dropLast(1)
    }

}