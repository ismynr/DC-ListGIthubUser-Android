package com.ismynr.githubuserlist.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.adapter.UserAdapter
import com.ismynr.githubuserlist.databinding.ActivityMainBinding
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.viewModel.UserViewModel


class MainActivity : AppCompatActivity() {

    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(listUser)
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
                searchView.clearFocus(); return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()) { mainViewModelSearch(); return true }
                return false
            }
        }); return true
    }

    private fun mainRecycleView(){
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = userAdapter
        binding.rvUsers.setHasFixedSize(true)
    }

    private fun mainViewModel(){
        userViewModel = ViewModelProvider(this, UserViewModel.VMFactory(applicationContext)).get(UserViewModel::class.java)
        showLoading(true)
        userViewModel.getAllUserApi()
        userViewModel.getListUsers().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                userAdapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun mainViewModelSearch(query: String = ""){
        showLoading(true)
        if(query.isNotEmpty()) userViewModel.getByQueryUserApi(query)
        else userViewModel.getAllUserApi()
        userViewModel.getListUsers().observe(this, Observer { listUsers ->
            if (listUsers != null) { userAdapter.setData(listUsers) }
        })
    }

    // LOADING MAIN
    private fun showLoading(state: Boolean) {
        if (state) binding.loadingProgress.visibility = View.VISIBLE
        else binding.loadingProgress.visibility = View.INVISIBLE
    }
}