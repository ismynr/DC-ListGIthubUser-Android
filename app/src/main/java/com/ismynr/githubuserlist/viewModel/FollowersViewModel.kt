package com.ismynr.githubuserlist.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ismynr.githubuserlist.model.Follower
import com.ismynr.githubuserlist.network.GithubRestNetwork
import com.loopj.android.http.AsyncHttpClient
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowersViewModel(context: Context) : ViewModel() {

    private val listMutableUsers = MutableLiveData<ArrayList<Follower>>()
    private val listUsers = ArrayList<Follower>()
    private val githubRest: GithubRestNetwork = GithubRestNetwork(context, AsyncHttpClient())

    fun getListUsers(): LiveData<ArrayList<Follower>> {
        return listMutableUsers
    }

    fun getAllUserApi(id: String = "") {
        githubRest.get("ALL_FOLLOWER", object: GithubRestNetwork.RequestListener{
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val jsonArray = JSONArray(String(responseBody))
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val usernameLogin = jsonObject.getString("login")
                    getDataGitDetail(usernameLogin)
                }
            }
        }, id)
    }

    fun getDataGitDetail(usernameLogin: String) {
        githubRest.get("DETAIL", object: GithubRestNetwork.RequestListener{
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val jsonObject = JSONObject(String(responseBody))
                val usersData = Follower()
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
            }
        }, usernameLogin)
    }

    // VIEW MODEL WITH ARGUMENT
    @Suppress("UNCHECKED_CAST")
    class VMFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {
        override fun <T: ViewModel> create(modelClass:Class<T>): T {
            return FollowersViewModel(context) as T
        }
    }
}