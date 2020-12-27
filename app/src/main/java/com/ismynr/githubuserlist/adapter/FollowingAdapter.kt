package com.ismynr.githubuserlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.databinding.ItemUserBinding
import com.ismynr.githubuserlist.model.Following

class FollowingAdapter(private val listUser: ArrayList<Following>) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    fun setData(items: ArrayList<Following>) {
        listUser.clear()
        notifyDataSetChanged()
        listUser.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)   {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(user: Following) {
            with(binding){
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(72, 72))
                    .into(imgAvatar)
                tvName.text = user.name
                tvUsername.text = itemView.context.getString(R.string.github_username, user.username)
                tvRepositories.text = itemView.context.getString(R.string.repositories_100, user.repository)
                tvFollowers.text = itemView.context.getString(R.string.followers_1000, user.followers)
                tvFollowing.text = itemView.context.getString(R.string.following_100, user.following)
            }
        }
    }
}