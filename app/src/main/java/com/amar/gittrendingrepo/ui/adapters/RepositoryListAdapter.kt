package com.amar.gittrendingrepo.ui.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.amar.gittrendingrepo.R
import com.amar.gittrendingrepo.model.Repository
import com.amar.gittrendingrepo.ui.RepositoryDetailsActivity
import com.amar.gittrendingrepo.util.Constants.REPO_EXTRA
import com.bumptech.glide.Glide

class RepositoryListAdapter(private var dataSet: List<Repository>, private var context: Context) : RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.repositoy_listview_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val repository = dataSet.get(position)

        viewHolder.repositoryNameTV.text = repository.repositoryName
        viewHolder.usernameTV.text = repository.username

        viewHolder.rankTV.text = String.format("%d.", repository.rank)
        viewHolder.forksCountTV.text = String.format("%s: %d", context.getString(R.string.forks), repository.forkCount)
        viewHolder.starsCountTV.text =
            String.format("%s: %d", context.getString(R.string.stars), repository.startsCount)

        Glide.with(viewHolder.userImageView).load(repository.users.get(0).avatarUrl).into(viewHolder.userImageView)

        viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, RepositoryDetailsActivity::class.java)
                intent.putExtra(REPO_EXTRA, dataSet.get(viewHolder.adapterPosition))
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context as Activity,
                    viewHolder.userImageView,
                    "profile"
                )
                context.startActivity(intent, options.toBundle())
            }
        })
    }

    override fun getItemCount() = dataSet.size

    fun setItems(list: List<Repository>) {
        dataSet = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val repositoryNameTV: TextView
        val usernameTV: TextView
        val forksCountTV: TextView
        val starsCountTV: TextView
        val rankTV: TextView
        val userImageView: AppCompatImageView

        init {
            repositoryNameTV = view.findViewById(R.id.repositoryNameTV)
            usernameTV = view.findViewById(R.id.usernameTV)
            forksCountTV = view.findViewById(R.id.forksCountTV)
            starsCountTV = view.findViewById(R.id.starsCountTV)
            rankTV = view.findViewById(R.id.rankTV)
            userImageView = view.findViewById(R.id.image)
        }
    }

}