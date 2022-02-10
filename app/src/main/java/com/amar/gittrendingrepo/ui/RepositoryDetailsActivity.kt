package com.amar.gittrendingrepo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amar.gittrendingrepo.databinding.ActivityRepositoryDetailsBinding
import com.amar.gittrendingrepo.model.Repository
import com.amar.gittrendingrepo.util.Constants.REPO_EXTRA
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepositoryDetailsBinding
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (intent.getSerializableExtra(REPO_EXTRA) as Repository).let {
            repository = it
            showData()
        }

        binding.backIcon.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })
    }

    fun showData() {
        Glide.with(applicationContext).load(repository.users.get(0).avatarUrl).into(binding.image)
        binding.repositoryNameTV.text = repository.repositoryName
        binding.ownerNameTV.text = repository.username
        binding.descTV.text = repository.description
        binding.languageTV.text = repository.language
    }

}