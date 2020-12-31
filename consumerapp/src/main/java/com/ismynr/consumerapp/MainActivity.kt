package com.ismynr.consumerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ismynr.consumerapp.adapter.FavoriteAdapter
import com.ismynr.consumerapp.databinding.ActivityMainBinding
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.CONTENT_URI
import com.ismynr.consumerapp.helper.MappingHelper
import com.ismynr.consumerapp.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) supportActionBar?.title = "Consumer Favorite List"

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
                Log.d("TAG", fav.toString())
                favAdapter.listFav = fav
            } else {
                favAdapter.listFav = ArrayList()
                binding.imgNoData.visibility = View.VISIBLE
                showSnackBarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackBarMessage(msg: String){
        Snackbar.make(binding.rvFavorite, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favAdapter.listFav)
    }

    private fun saveInstance(si: Bundle?){
        if (si == null) loadAsync()
        else si.getParcelableArrayList<Favorite>(EXTRA_STATE)?.also { favAdapter.listFav = it }
    }

    private fun mainRecycleView(){
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = favAdapter
        binding.rvFavorite.setHasFixedSize(true)
    }

}