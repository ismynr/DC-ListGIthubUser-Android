package com.ismynr.githubuserlist.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.adapter.ListUserFollowersAdapter
import com.ismynr.githubuserlist.model.Follower
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.viewModel.FollowersViewModel
import com.ismynr.githubuserlist.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listUser: ArrayList<Follower> = ArrayList()
    private lateinit var listUserAdapter: ListUserFollowersAdapter
    private lateinit var followerViewModel: FollowersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listUserAdapter = ListUserFollowersAdapter(listUser)

        rv_users_followers.layoutManager = LinearLayoutManager(activity)
        rv_users_followers.adapter = listUserAdapter
        rv_users_followers.setHasFixedSize(true)

        followerViewModel = ViewModelProvider(activity!!, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)

        val dataUser = activity!!.intent.getParcelableExtra<User>(EXTRA_DETAIL) as User
        followerViewModel.getAllUserApi(activity!!.applicationContext, dataUser.username.toString())

        showLoading(true)
        followerViewModel.getListUsers().observe(activity!!, Observer { listUsers ->
            if (listUsers != null) {
                listUserAdapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loadingFollowers.visibility = View.VISIBLE
        } else {
            loadingFollowers.visibility = View.INVISIBLE
        }
    }

}