package com.ismynr.githubuserlist.viewModel

import android.content.Context
import android.database.Cursor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ismynr.githubuserlist.db.UserFavHelper
import com.ismynr.githubuserlist.model.Favorite

class FavoriteViewModel(context: Context): ViewModel() {

    private var userFavHelper: UserFavHelper = UserFavHelper.getInstance(context)
    private lateinit var cursor: Cursor

    init{
        userFavHelper.open()
    }

    fun checkDbById(favorite: Favorite?, listener: RequestListener){
        cursor = userFavHelper.queryById(favorite?.username.toString())
        if (cursor.moveToNext()) {
            listener.cursorMoveToNext()
        }
    }

    fun deleteDbById(favorite: Favorite?){
        userFavHelper.deleteBy(favorite?.username.toString())
    }

    interface RequestListener{
        fun cursorMoveToNext()
    }

    // VIEW MODEL WITH ARGUMENT
    @Suppress("UNCHECKED_CAST")
    class VMFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {
        override fun <T: ViewModel> create(modelClass:Class<T>): T {
            return FavoriteViewModel(context) as T
        }
    }
}