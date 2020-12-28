package com.ismynr.githubuserlist.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.TABLE_NAME
import com.ismynr.githubuserlist.db.DatabaseContract.UserFavoriteColumns.Companion.USERNAME
import java.sql.SQLException

class UserFavHelper(context: Context) {

    private lateinit var database: SQLiteDatabase
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserFavHelper? = null

        fun getInstance(context: Context): UserFavHelper =
            INSTANCE?: synchronized(this){
                INSTANCE?: UserFavHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open(){
        database = dataBaseHelper.writableDatabase
    }

    fun close(){
        dataBaseHelper.close()
        if(database.isOpen){
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE, null, null, null, null, null, "$USERNAME ASC", null
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE, null, "$USERNAME = ?", arrayOf(id), null, null, null, null,
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues): Int {
        return database.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(id))
    }

    fun deleteBy(id: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }

}