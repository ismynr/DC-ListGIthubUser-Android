package com.ismynr.githubuserlist.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.adapter.ViewPagerFollAdapter
import com.ismynr.githubuserlist.model.User
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewpager.layoutParams.height = resources.getDimension(R.dimen.height).toInt()
        } else {
            viewpager.layoutParams.height = resources.getDimension(R.dimen.height1).toInt()
        }

        if (supportActionBar != null) {
            supportActionBar?.title = "Detail User"
        }
        setDetailData()
        viewPagerConfig()
    }

    private fun setDetailData() {
        val user = intent.getParcelableExtra<User>(EXTRA_DETAIL) as User
        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions().override(100, 100))
            .into(img_avatar)

        tv_name.text = user.name
        tv_username.text = getString(R.string.github_username, user.username)
        tv_company.text = user.company
        tv_location.text = user.location
        tv_followers.text = user.followers + " Followers"
        tv_repositories.text = ": " + user.repository
        tv_following.text = user.following + " Following"
    }

    private fun viewPagerConfig() {
        val viewPagerDetail = ViewPagerFollAdapter(this, supportFragmentManager)
        viewpager.adapter = viewPagerDetail
        tabs.setupWithViewPager(viewpager)
        supportActionBar?.elevation = 0f
    }
}