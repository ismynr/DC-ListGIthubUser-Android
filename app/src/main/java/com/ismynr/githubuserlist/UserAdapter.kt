package com.ismynr.githubuserlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter internal constructor(private val context: Context) : BaseAdapter() {
    internal var users = arrayListOf<User>()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var itemView = view

        if(itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val user = getItem(position) as User
        viewHolder.bind(user)
        return itemView
    }

    override fun getItem(i: Int): Any {
        return users[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return users.size
    }


    private inner class ViewHolder internal constructor(view: View){
        private val tvName: TextView = view.findViewById(R.id.tv_name)
        private val tvUsername: TextView = view.findViewById(R.id.tv_username)
        private val tvFoll: TextView = view.findViewById(R.id.tv_foll)
        private val imgAvatar: CircleImageView = view.findViewById(R.id.img_avatar)

        internal fun bind(user: User){
            val follText = "Followers:"+ user.followers +" Following: "+ user.following

            tvName.text = user.name
            tvUsername.text = user.username
            tvFoll.text = follText
            imgAvatar.setImageResource(user.avatar!!)
        }
    }
}