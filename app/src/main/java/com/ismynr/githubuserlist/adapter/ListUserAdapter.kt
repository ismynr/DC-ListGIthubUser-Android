package com.ismynr.githubuserlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    fun setData(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
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

        val data = listUser[position]
        holder.itemView.setOnClickListener {
            val dataUserIntent = User(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following.toString()
            )
//            val mIntent = Intent(it.context, DetailActivity::class.java)
//            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, dataUserIntent)
//            it.context.startActivity(mIntent)
        }
    }

}