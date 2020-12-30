package com.ismynr.githubuserlist.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ismynr.githubuserlist.db.DatabaseContract
import com.ismynr.githubuserlist.db.UserFavHelper

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val FAV = 1
        private const val FAV_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userFavHelper: UserFavHelper

        init {
            sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.UserFavoriteColumns.TABLE_NAME, FAV)
            sUriMatcher.addURI(DatabaseContract.AUTHORITY, "${DatabaseContract.UserFavoriteColumns.TABLE_NAME}/#", FAV_ID)
        }
    }

    override fun onCreate(): Boolean {
        userFavHelper = UserFavHelper.getInstance(context as Context)
        userFavHelper.open()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAV -> userFavHelper.queryAll()
            FAV_ID -> userFavHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (FAV) {
            sUriMatcher.match(uri) -> userFavHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(DatabaseContract.UserFavoriteColumns.CONTENT_URI, null)
        return Uri.parse("${DatabaseContract.UserFavoriteColumns.CONTENT_URI}/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val deleted: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> userFavHelper.deleteBy(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(DatabaseContract.UserFavoriteColumns.CONTENT_URI, null)
        return deleted
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val updated: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> userFavHelper.update(uri.lastPathSegment.toString(),values!!)
            else -> 0
        }
        context?.contentResolver?.notifyChange(DatabaseContract.UserFavoriteColumns.CONTENT_URI, null)
        return updated
    }
}