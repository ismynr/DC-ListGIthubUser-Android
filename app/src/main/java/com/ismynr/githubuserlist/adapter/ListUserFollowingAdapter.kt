package com.ismynr.githubuserlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.databinding.ItemUserBinding
import com.ismynr.githubuserlist.model.Following

class ListUserFollowingAdapter(private val listUser: ArrayList<Following>) : RecyclerView.Adapter<ListUserFollowingAdapter.ListViewHolder>() {

    fun setData(items: ArrayList<Following>) {
        listUser.clear()
        notifyDataSetChanged()
        listUser.addAll(items)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)   {
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