package com.ismynr.githubuserlist.view

import android.content.ContentValues
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.adapter.ViewPagerFollAdapter
import com.ismynr.githubuserlist.databinding.ActivityDetailBinding
import com.ismynr.githubuserlist.db.DatabaseContract
import com.ismynr.githubuserlist.model.Favorite
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.viewModel.FavoriteViewModel
import com.ismynr.githubuserlist.viewModel.UserViewModel
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.USERNAME
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.NAME
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.AVATAR
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.COMPANY
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.LOCATION
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.REPOSITORY
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWERS
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWING
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.FAVORITE

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var menu: Menu? = null
    private var isFavorite = false
    private lateinit var fromActivity: String
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var userViewModel: UserViewModel

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val FROM_ACTIVITY = "from_activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) supportActionBar?.title = "Detail User"

        fromActivity = intent.getStringExtra(FROM_ACTIVITY).toString()
        favViewModel = ViewModelProvider(this, FavoriteViewModel.VMFactory(applicationContext)).get(FavoriteViewModel::class.java)
        userViewModel = ViewModelProvider(this, UserViewModel.VMFactory(applicationContext)).get(UserViewModel::class.java)

        setDetailData()
        viewPagerConfig()
    }

    private fun setDetailData() {
        if(fromActivity == "FavoriteActivity"){
            val favorite = intent.getParcelableExtra<Favorite>(EXTRA_DETAIL)
            with(binding){
                Glide.with(this@DetailActivity)
                    .load(favorite!!.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(imgAvatar)
                tvName.text = favorite.name
                tvUsername.text = getString(R.string.github_username, favorite.username)
                tvCompany.text = favorite.company
                tvLocation.text = favorite.location
                tvFollowers.text = withText("Followers", favorite.followers)
                tvRepositories.text = withText("Followers", favorite.followers)
                tvFollowing.text = withText("Followers", favorite.followers)
            }
        } else {
            val user = intent.getParcelableExtra<User>(EXTRA_DETAIL)
            with(binding){
                Glide.with(this@DetailActivity)
                    .load(user!!.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(imgAvatar)
                tvName.text = user.name
                tvUsername.text = getString(R.string.github_username, user.username)
                tvCompany.text = user.company
                tvLocation.text = user.location
                tvFollowers.text = withText("Followers", user.followers)
                tvRepositories.text = withText(":", user.repository)
                tvFollowing.text = withText("Following", user.following)
            }
        }
    }

    private fun withText(text: String, obj: String?): String {
        return "$text $obj"
    }

    private fun viewPagerConfig() {
        val viewPagerDetail = ViewPagerFollAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = viewPagerDetail
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }

    private fun setStatusFavorite(status: Boolean, isFavorite: Boolean) {
        this.isFavorite = isFavorite
        if (status) menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_24_white)
        else        menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_border_24_white)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        if(fromActivity == "FavoriteActivity"){
            val favorite = intent.getParcelableExtra<Favorite>(EXTRA_DETAIL)
            favViewModel.checkDbById(favorite, object: FavoriteViewModel.RequestListener{
                override fun cursorMoveToNext() {
                    setStatusFavorite(status = true, isFavorite = true)
                }
            })
        } else {
            val user = intent.getParcelableExtra<User>(EXTRA_DETAIL)
            userViewModel.checkDbById(user, object: UserViewModel.RequestListener{
                override fun cursorMoveToNext() {
                    setStatusFavorite(status = true, isFavorite = true)
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(fromActivity == "FavoriteActivity"){
            val favorite = intent.getParcelableExtra<Favorite>(EXTRA_DETAIL)
            if(item.itemId == R.id.menu_fav){
                if (isFavorite) {
                    favViewModel.deleteDbById(favorite)
                    Toast.makeText(this, "Data Deleted from Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(status = false, isFavorite = false)
                } else {
                    val values = ContentValues()
                    values.put(USERNAME, favorite?.username)
                    values.put(NAME, favorite?.name)
                    values.put(AVATAR, favorite?.avatar)
                    values.put(COMPANY, favorite?.company)
                    values.put(LOCATION, favorite?.location)
                    values.put(REPOSITORY, favorite?.repository)
                    values.put(FOLLOWERS, favorite?.followers)
                    values.put(FOLLOWING, favorite?.following)
                    values.put(FAVORITE, "isFav")
                    contentResolver.insert(
                        DatabaseContract.UserFavoriteColumns.CONTENT_URI, values
                    )
                    Toast.makeText(this, "Data Added to Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(status = true, isFavorite = true)
                }
            }
        } else {
            val user = intent.getParcelableExtra<User>(EXTRA_DETAIL)
            if(item.itemId == R.id.menu_fav){
                if (isFavorite) {
                    userViewModel.deleteDbById(user)
                    Toast.makeText(this, "Data Deleted from Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(status = false, isFavorite = false)
                } else {
                    val values = ContentValues()
                    values.put(USERNAME, user?.username)
                    values.put(NAME, user?.name)
                    values.put(AVATAR, user?.avatar)
                    values.put(COMPANY, user?.company)
                    values.put(LOCATION, user?.location)
                    values.put(REPOSITORY, user?.repository)
                    values.put(FOLLOWERS, user?.followers)
                    values.put(FOLLOWING, user?.following)
                    values.put(FAVORITE, "isFav")
                    contentResolver.insert(
                        DatabaseContract.UserFavoriteColumns.CONTENT_URI, values
                    )
                    Toast.makeText(this, "Data Added to Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(status = true, isFavorite = true)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}