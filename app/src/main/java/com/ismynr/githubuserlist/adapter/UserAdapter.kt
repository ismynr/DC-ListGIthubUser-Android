package com.ismynr.githubuserlist.adapter

import android.content.Intent
import com.ismynr.githubuserlist.databinding.ItemUserBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.view.DetailActivity

class UserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(items: ArrayList<User>) {
        listUser.clear()
        notifyDataSetChanged()
        listUser.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])

        val data = listUser[position]
        holder.itemView.setOnClickListener {
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, data)
            it.context.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(user: User) {
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