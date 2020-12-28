package com.ismynr.githubuserlist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.TABLE_NAME
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.NAME
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.USERNAME
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.AVATAR
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.COMPANY
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.FAVORITE
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWERS
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWING
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.LOCATION
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.REPOSITORY


internal class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbgithubuser"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " ($USERNAME TEXT NOT NULL," +
                " $NAME TEXT NOT NULL," +
                " $FOLLOWERS TEXT NOT NULL," +
                " $FOLLOWING TEXT NOT NULL," +
                " $AVATAR TEXT NOT NULL," +
                " $COMPANY TEXT NOT NULL," +
                " $LOCATION TEXT NOT NULL," +
                " $FAVORITE TEXT NOT NULL," +
                " $REPOSITORY TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}