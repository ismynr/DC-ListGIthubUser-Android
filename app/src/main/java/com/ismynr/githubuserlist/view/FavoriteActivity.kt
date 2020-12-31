package com.ismynr.githubuserlist.view

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ismynr.githubuserlist.adapter.FavoriteAdapter
import com.ismynr.githubuserlist.databinding.ActivityFavoriteBinding
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.CONTENT_URI
import com.ismynr.githubuserlist.db.UserFavHelper
import com.ismynr.githubuserlist.helper.MappingHelper
import com.ismynr.githubuserlist.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var dbHelper: UserFavHelper
    private lateinit var binding: ActivityFavoriteBinding

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar?.title = "Favorite List"
        }

        dbHelper = UserFavHelper.getInstance(applicationContext)
        dbHelper.open()

        favAdapter = FavoriteAdapter()
        mainRecycleView()

        val handleThread = HandlerThread("DataObserver")
        handleThread.start()
        val handler = Handler(handleThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        saveInstance(savedInstanceState)
    }

    private fun loadAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.loadingProgressFavorite.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val fav = deferredNotes.await()
            binding.loadingProgressFavorite.visibility = View.INVISIBLE

            if (fav.size > 0) {
                favAdapter.listFav = fav
            } else {
                favAdapter.listFav = ArrayList()
                binding.imgNoData.visibility = View.VISIBLE
                Snackbar.make(binding.rvFavorite, "Data Does Not Exist", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun mainRecycleView(){
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = favAdapter
        binding.rvFavorite.setHasFixedSize(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favAdapter.listFav)
    }

    private fun saveInstance(si: Bundle?){
        if (si == null) loadAsync()
        else si.getParcelableArrayList<Favorite>(EXTRA_STATE)?.also { favAdapter.listFav = it }
    }

}