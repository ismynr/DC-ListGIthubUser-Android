package com.ismynr.githubuserlist.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.databinding.ItemUserBinding
import com.ismynr.githubuserlist.model.Favorite
import com.ismynr.githubuserlist.view.DetailActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFav = ArrayList<Favorite>()
        set(listFavourite) {
            if (listFavourite.size > 0) {
                this.listFav.clear()
            }
            this.listFav.addAll(listFavourite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFav[position])

        val data = listFav[position]
        holder.itemView.setOnClickListener {
            val mIntent = Intent(it.context, DetailActivity::class.java)
//            Log.d("START", it.context.javaClass.simpleName)
            mIntent.putExtra(DetailActivity.FROM_ACTIVITY, it.context.javaClass.simpleName)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, data)
            it.context.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int = listFav.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(fav: Favorite) {
            with(binding){
                Glide.with(itemView.context)
                    .load(fav.avatar)
                    .apply(RequestOptions().override(72, 72))
                    .error(R.drawable.ic_tag_faces_24_black)
                    .into(imgAvatar)
                tvName.text = fav.name
                tvUsername.text = itemView.context.getString(R.string.github_username, fav.username)
                tvRepositories.text = itemView.context.getString(R.string.repositories_100, fav.repository)
                tvFollowers.text = itemView.context.getString(R.string.followers_1000, fav.followers)
                tvFollowing.text = itemView.context.getString(R.string.following_100, fav.following)
            }
        }
    }
}