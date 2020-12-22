package com.ismynr.githubuserlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.model.Follower
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserFollowersAdapter(private val listUser: ArrayList<Follower>) : RecyclerView.Adapter<ListUserFollowersAdapter.ListViewHolder>(){

    fun setData(items: ArrayList<Follower>) {
        listUser.clear()
        notifyDataSetChanged()
        listUser.addAll(items)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        fun bind(user: Follower) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(72, 72))
                    .into(img_avatar)

                tv_name.text = user.name
                tv_username.text = itemView.context.getString(R.string.github_username, user.username)
                tv_repositories.text = itemView.context.getString(R.string.repositories_100, user.repository)
                tv_followers.text = itemView.context.getString(R.string.followers_1000, user.followers)
                tv_following.text = itemView.context.getString(R.string.following_100, user.following)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}