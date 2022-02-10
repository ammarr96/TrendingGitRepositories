package com.amar.gittrendingrepo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.amar.gittrendingrepo.R
import com.amar.gittrendingrepo.databinding.ActivityRepoListBinding
import com.amar.gittrendingrepo.model.Repository
import com.amar.gittrendingrepo.model.enums.SortOption
import com.amar.gittrendingrepo.ui.adapters.RepositoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RepoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoListBinding

    private lateinit var adapter: RepositoryListAdapter;
    private var list: List<Repository> = ArrayList()

    val viewModel: RepositoriesViewModel by viewModels()
    lateinit var popupMenu: PopupMenu
    var sortOption: SortOption = SortOption.DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RepositoryListAdapter(list, this)
        binding.recyclerView.adapter = adapter

        setSubscribers()
        setObservers()

        setSortMenu()
    }

    private fun setSortMenu() {
        popupMenu = PopupMenu(applicationContext, binding.sortImage)
        popupMenu.menuInflater.inflate(R.menu.sort_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_defauls -> {
                    sortOption = SortOption.DEFAULT
                }
                R.id.menu_stars -> {
                    sortOption = SortOption.BY_STARS
                }
                R.id.menu_forks -> {
                    sortOption = SortOption.BY_FORKS
                }
            }
            viewModel.changeSortOption(sortOption)
            true
        })

        binding.sortImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                popupMenu.show()
            }
        })
    }

    private fun setObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTrendingRepositories()
        }
    }

    private fun setSubscribers() {
        lifecycleScope.launchWhenCreated {
            viewModel.showProgressBarFlow.collect { showProgress ->
                if (showProgress) {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.listStateFlow.collect {
                list = it
                adapter.setItems(it)
                adapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.menuSelectedFlow.collect {
                val menu: Menu = popupMenu.getMenu()
                for (i in 0 until menu.size()) {
                    menu.getItem(i).setChecked(false)
                }
                popupMenu.menu.getItem(it.ordinal).setChecked(true)
            }
        }
    }

}