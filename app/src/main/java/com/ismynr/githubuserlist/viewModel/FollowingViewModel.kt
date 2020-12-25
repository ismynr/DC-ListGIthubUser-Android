package com.ismynr.githubuserlist.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismynr.githubuserlist.model.Following
import com.ismynr.githubuserlist.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class FollowingViewModel : ViewModel() {

    private val listMutableUsers = MutableLiveData<ArrayList<Following>>()
    private val listUsers = ArrayList<Following>()
    private val client = AsyncHttpClient()

    init{
        client.addHeader("Authorization", "token ff9bd9737014f6fd3bf9d4b748f03169712ba11e")
        client.addHeader("User-Agent", "request")
    }

    fun getListUsers(): LiveData<ArrayList<Following>> {
        return listMutableUsers
    }

    fun getAllUserApi(context: Context, id: String = "") {
        val urlClient = "https://api.github.com/users/$id/following?per_page=10"
        client.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val jsonArray = JSONArray(String(responseBody))
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getDataGitDetail(usernameLogin, context)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Chould Be Loaded", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure( statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                failureResponse(context, statusCode, error)
            }
        })
    }

    fun getDataGitDetail(usernameLogin: String, context: Context) {
        val urlClient = "https://api.github.com/users/$usernameLogin"
        client.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val jsonObject = JSONObject(String(responseBody))
                    val usersData = Following()
                    usersData.username = jsonObject.getString("login")
                    usersData.name = jsonObject.getString("name")
                    usersData.avatar = jsonObject.getString("avatar_url")
                    usersData.company = jsonObject.getString("company")
                    usersData.location = jsonObject.getString("location")
                    usersData.repository = jsonObject.getString("public_repos")
                    usersData.followers = jsonObject.getString("followers")
                    usersData.following = jsonObject.getString("following")
                    listUsers.add(usersData)
                    listMutableUsers.postValue(listUsers)
                } catch (e: Exception) {
                    Toast.makeText(context, "Chould Be Loaded", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                failureResponse(context, statusCode, error)
            }
        })
    }

    private fun failureResponse(context: Context, statusCode: Int, error: Throwable){
        val errorMessage = when (statusCode) {
            401 -> "$statusCode : Bad Request"
            403 -> "$statusCode : Forbidden"
            404 -> "$statusCode : Not Found"
            else -> "$statusCode : ${error.message}"
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}