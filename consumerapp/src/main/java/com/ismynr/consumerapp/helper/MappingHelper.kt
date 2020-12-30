package com.ismynr.consumerapp.helper

import android.database.Cursor
import com.ismynr.consumerapp.model.Favorite
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.USERNAME
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.NAME
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.AVATAR
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.COMPANY
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.LOCATION
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.REPOSITORY
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWERS
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWING
import com.ismynr.consumerapp.db.DatabaseContract.UserFavoriteColumns.Companion.FAVORITE

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favorite> {
        val favList = ArrayList<Favorite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(NAME))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val followers = getString(getColumnIndexOrThrow(FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(FOLLOWING))
                val location = getString(getColumnIndexOrThrow(LOCATION))
                val repository = getString(getColumnIndexOrThrow(REPOSITORY))
                val company = getString(getColumnIndexOrThrow(COMPANY))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val favorite = getString(getColumnIndexOrThrow(FAVORITE))
                favList.add(Favorite(name, username, followers, following, location, repository, company, avatar, favorite))
            }
        }
        return favList
    }
}