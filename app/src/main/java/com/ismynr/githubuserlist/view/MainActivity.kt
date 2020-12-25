package com.ismynr.githubuserlist.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismynr.githubuserlist.adapter.ListUserAdapter
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.viewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.app.SearchManager;

class MainActivity : AppCompatActivity() {

    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listUserAdapter = ListUserAdapter(listUser)
        mainRecycleView()
        mainViewModel()
    }

    // MENU OPTION
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    searchView.clearFocus()
                    mainViewModelSearch(query)
                    return true
                }
                searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()){
                    mainViewModelSearch()
                    return true
                }
                return false
            }
        })
        return true
    }

    // LOADING MAIN
    private fun showLoading(state: Boolean) {
        if (state) loadingProgress.visibility = View.VISIBLE
        else loadingProgress.visibility = View.INVISIBLE
    }

    private fun mainRecycleView(){
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = listUserAdapter
        rv_users.setHasFixedSize(true)
    }

    private fun mainViewModel(){
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        showLoading(true)
        userViewModel.getAllUserApi(applicationContext)
        userViewModel.getListUsers().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                listUserAdapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun mainViewModelSearch(query: String = ""){
        showLoading(true)
        if(query.isNotEmpty()) userViewModel.getByQueryUserApi(query, applicationContext)
        else userViewModel.getAllUserApi(applicationContext)
        userViewModel.getListUsers().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                listUserAdapter.setData(listUsers)
            }
        })
    }
}