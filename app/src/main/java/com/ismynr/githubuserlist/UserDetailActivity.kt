package com.ismynr.githubuserlist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class UserDetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val imgAvatar: CircleImageView = findViewById(R.id.img_avatar)
        val tvName: TextView = findViewById(R.id.tv_name)
        val tvUsername: TextView = findViewById(R.id.tv_username)
        val tvFollowers: TextView = findViewById(R.id.tv_followers)
        val tvFollowing: TextView = findViewById(R.id.tv_following)
        val tvRepository: TextView = findViewById(R.id.tv_repositories)
        val tvCompany: TextView = findViewById(R.id.tv_company)
        val tvLocation: TextView = findViewById(R.id.tv_location)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        imgAvatar.setImageResource(user.avatar!!)
        tvName.text       = user.name.toString()
        tvUsername.text   = user.username.toString()
        tvFollowers.text  = "Followers("+user.followers.toString()+")"
        tvFollowing.text  = "Following("+user.following.toString()+")"
        tvRepository.text = "Repositories("+user.repository.toString()+")"
        tvCompany.text    = user.company.toString()
        tvLocation.text   = user.location.toString()
    }
}