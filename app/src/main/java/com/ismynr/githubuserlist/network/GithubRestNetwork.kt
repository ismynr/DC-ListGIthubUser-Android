package com.ismynr.githubuserlist.network

import android.content.Context
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header

class GithubRestNetwork(private val context: Context, private val client: AsyncHttpClient) {

    private lateinit var urlType: String
    private lateinit var username: String
    companion object{
        private const val BASE_URL = "https://api.github.com/"
    }

    init{
        client.addHeader("Authorization", "token 5a96c6013a5aa9c4b145c92d2630d42b43401885")
        client.addHeader("User-Agent", "request")
    }

    fun get(urlType: String, listener: RequestListener, username: String = ""){
        this.urlType = urlType
        this.username = username

        client.get(getUrl(), object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    listener.onSuccess(statusCode, headers, responseBody)
                } catch (e: Exception) {
                    Toast.makeText(context, "Could Be Loaded", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUrl(): String{
        return when(urlType){
            "ALL"           -> getAbsoluteUrl("users?per_page=10")
            "ALL_FOLLOWER"  -> getAbsoluteUrl("users/$username/followers?per_page=10")
            "ALL_FOLLOWING" -> getAbsoluteUrl("users/$username/following?per_page=10")
            "DETAIL"        -> getAbsoluteUrl("users/$username")
            "SEARCH"        -> getAbsoluteUrl("search/users?q=$username&per_page=3")
            else            -> getAbsoluteUrl("users?per_page=10")
        }
    }

    private fun getAbsoluteUrl(relativeUrl: String): String{
        return BASE_URL + relativeUrl
    }

    interface RequestListener{
        fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray)
    }
}