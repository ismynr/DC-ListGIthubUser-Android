package com.ismynr.githubuserlist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismynr.githubuserlist.adapter.ListUserAdapter
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.viewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var userViewModel: UserViewModel

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listUserAdapter = ListUserAdapter(listUser)
        listUserAdapter.notifyDataSetChanged()

//        searchData()
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = listUserAdapter
        rv_users.setHasFixedSize(true)

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        userViewModel.getAllUserApi(applicationContext)
        showLoading(true)

        userViewModel.getListUsers().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                listUserAdapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.INVISIBLE
        }
    }

}