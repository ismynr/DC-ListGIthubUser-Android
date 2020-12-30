package com.ismynr.githubuserlist.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.adapter.ViewPagerFollAdapter
import com.ismynr.githubuserlist.databinding.ActivityDetailBinding
import com.ismynr.githubuserlist.db.DatabaseContract
import com.ismynr.githubuserlist.db.UserFavHelper
import com.ismynr.githubuserlist.model.Favorite
import com.ismynr.githubuserlist.model.User


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var dbHelper: UserFavHelper
    private var menu: Menu? = null
    private var isFavorite = false
    lateinit var fromAct: String

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val FROM_ACTIVITY = "from_activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) supportActionBar?.title = "Detail User"
        fromAct = intent.getStringExtra(FROM_ACTIVITY).toString()
        dbHelper = UserFavHelper.getInstance(applicationContext)
        dbHelper.open()

        setDetailData()
        viewPagerConfig()
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailData() {
        if(fromAct == "FavoriteActivity"){
            val user = intent.getParcelableExtra<Favorite>(EXTRA_DETAIL)
            with(binding){
                Glide.with(this@DetailActivity)
                    .load(user!!.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(imgAvatar)

                tvName.text = user.name
                tvUsername.text = getString(R.string.github_username, user.username)
                tvCompany.text = user.company
                tvLocation.text = user.location
                tvFollowers.text = user.followers + " Followers"
                tvRepositories.text = ": " + user.repository
                tvFollowing.text = user.following + " Following"
            }
        }else{
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
                tvFollowers.text = user.followers + " Followers"
                tvRepositories.text = ": " + user.repository
                tvFollowing.text = user.following + " Following"
            }
        }
    }

    private fun viewPagerConfig() {
        val viewPagerDetail = ViewPagerFollAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = viewPagerDetail
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }

    private fun setStatusFavorite(status: Boolean) {
        if    (status) menu!!.getItem(0).setIcon(R.drawable.ic_favorite_24_white)
        else menu!!.getItem(0).setIcon(R.drawable.ic_favorite_border_24_white)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        if(fromAct == "FavoriteActivity"){
            val usernameVal = intent.getParcelableExtra<Favorite>(EXTRA_DETAIL)
            val cursor: Cursor = dbHelper.queryById(usernameVal?.username.toString())
            if (cursor.moveToNext()) {
                isFavorite = true
                setStatusFavorite(true)
            }
        }else{
            val usernameVal = intent.getParcelableExtra<User>(EXTRA_DETAIL)
            val cursor: Cursor = dbHelper.queryById(usernameVal?.username.toString())
            if (cursor.moveToNext()) {
                isFavorite = true
                setStatusFavorite(true)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(fromAct == "FavoriteActivity"){
            val data = intent.getParcelableExtra<Favorite>(EXTRA_DETAIL)
            if(item.itemId == R.id.menu_fav){
                if (isFavorite) {
                    val username = data!!.username.toString()
                    dbHelper.deleteBy(username)
                    Toast.makeText(this, "Data Deleted from Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(false)
                    isFavorite = false
                } else {
                    val values = ContentValues()
                    values.put(DatabaseContract.UserFavoriteColumns.USERNAME, data!!.username)
                    values.put(DatabaseContract.UserFavoriteColumns.NAME, data.name)
                    values.put(DatabaseContract.UserFavoriteColumns.AVATAR, data.avatar)
                    values.put(DatabaseContract.UserFavoriteColumns.COMPANY, data.company)
                    values.put(DatabaseContract.UserFavoriteColumns.LOCATION, data.location)
                    values.put(DatabaseContract.UserFavoriteColumns.REPOSITORY, data.repository)
                    values.put(DatabaseContract.UserFavoriteColumns.FOLLOWERS, data.followers)
                    values.put(DatabaseContract.UserFavoriteColumns.FOLLOWING, data.following)
                    values.put(DatabaseContract.UserFavoriteColumns.FAVORITE, "isFav")
                    contentResolver.insert(
                        DatabaseContract.UserFavoriteColumns.Companion.CONTENT_URI,
                        values
                    )
                    Toast.makeText(this, "Data Added to Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(true)
                    isFavorite = true
                }
            }
        }else{
            val data = intent.getParcelableExtra<User>(EXTRA_DETAIL)
            if(item.itemId == R.id.menu_fav){
                if (isFavorite) {
                    val username = data!!.username.toString()
                    dbHelper.deleteBy(username)
                    Toast.makeText(this, "Data Deleted from Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(false)
                    isFavorite = false
                } else {
                    val values = ContentValues()
                    values.put(DatabaseContract.UserFavoriteColumns.USERNAME, data!!.username)
                    values.put(DatabaseContract.UserFavoriteColumns.NAME, data.name)
                    values.put(DatabaseContract.UserFavoriteColumns.AVATAR, data.avatar)
                    values.put(DatabaseContract.UserFavoriteColumns.COMPANY, data.company)
                    values.put(DatabaseContract.UserFavoriteColumns.LOCATION, data.location)
                    values.put(DatabaseContract.UserFavoriteColumns.REPOSITORY, data.repository)
                    values.put(DatabaseContract.UserFavoriteColumns.FOLLOWERS, data.followers)
                    values.put(DatabaseContract.UserFavoriteColumns.FOLLOWING, data.following)
                    values.put(DatabaseContract.UserFavoriteColumns.FAVORITE, "isFav")
                    contentResolver.insert(
                        DatabaseContract.UserFavoriteColumns.Companion.CONTENT_URI,
                        values
                    )
                    Toast.makeText(this, "Data Added to Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(true)
                    isFavorite = true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}