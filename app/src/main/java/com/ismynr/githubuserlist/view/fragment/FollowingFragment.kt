package com.ismynr.githubuserlist.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismynr.githubuserlist.adapter.FollowingAdapter
import com.ismynr.githubuserlist.databinding.FragmentFollowingBinding
import com.ismynr.githubuserlist.model.Following
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.viewModel.FollowingViewModel

class FollowingFragment : Fragment() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    private var listUser: ArrayList<Following> = ArrayList()
    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowingAdapter(listUser)
        binding.rvUsersFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvUsersFollowing.setHasFixedSize(true)
        binding.rvUsersFollowing.adapter = adapter
        followingViewModel = ViewModelProvider(
            this, FollowingViewModel.VMFactory(activity!!.applicationContext)
        ).get(FollowingViewModel::class.java)

        val dataUser = activity!!.intent.getParcelableExtra<User>(EXTRA_DETAIL) as User
        followingViewModel.getAllUserApi(dataUser.username.toString())

        showLoading(true)
        followingViewModel.getListUsers().observe(activity!!, Observer { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.loadingFollowing.visibility = View.VISIBLE
        else binding.loadingFollowing.visibility = View.INVISIBLE
    }
}