package com.ismynr.githubuserlist.view

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.adapter.ViewPagerFollAdapter
import com.ismynr.githubuserlist.databinding.ActivityDetailBinding
import com.ismynr.githubuserlist.model.User

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.viewPager.layoutParams.height = resources.getDimension(R.dimen.height).toInt()
        } else {
            binding.viewPager.layoutParams.height = resources.getDimension(R.dimen.height1).toInt()
        }

        if (supportActionBar != null) {
            supportActionBar?.title = "Detail User"
        }
        setDetailData()
        viewPagerConfig()
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailData() {
        val user = intent.getParcelableExtra<User>(EXTRA_DETAIL) as User
        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions().override(100, 100))
            .into(binding.imgAvatar)

        binding.tvName.text = user.name
        binding.tvUsername.text = getString(R.string.github_username, user.username)
        binding.tvCompany.text = user.company
        binding.tvLocation.text = user.location
        binding.tvFollowers.text = user.followers + " Followers"
        binding.tvRepositories.text = ": " + user.repository
        binding.tvFollowing.text = user.following + " Following"
    }

    private fun viewPagerConfig() {
        val viewPagerDetail = ViewPagerFollAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = viewPagerDetail
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }
}