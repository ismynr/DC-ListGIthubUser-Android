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
import com.ismynr.githubuserlist.adapter.ListUserFollowingAdapter
import com.ismynr.githubuserlist.model.Following
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.viewModel.FollowingViewModel
import com.ismynr.githubuserlist.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    private var listUser: ArrayList<Following> = ArrayList()
    private lateinit var listUserAdapter: ListUserFollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listUserAdapter = ListUserFollowingAdapter(listUser)

        rv_users_following.layoutManager = LinearLayoutManager(activity)
        rv_users_following.setHasFixedSize(true)
        rv_users_following.adapter = listUserAdapter

        followingViewModel = ViewModelProvider(activity!!, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

        val dataUser = activity!!.intent.getParcelableExtra<User>(EXTRA_DETAIL) as User
        followingViewModel.getAllUserApi(activity!!.applicationContext, dataUser.username.toString())

        showLoading(true)
        followingViewModel.getListUsers().observe(activity!!, Observer { listUsers ->
            if (listUsers != null) {
                listUserAdapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loadingFollowing.visibility = View.VISIBLE
        } else {
            loadingFollowing.visibility = View.INVISIBLE
        }
    }
}