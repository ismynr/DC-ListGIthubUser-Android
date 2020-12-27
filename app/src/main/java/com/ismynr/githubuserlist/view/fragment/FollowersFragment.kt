package com.ismynr.githubuserlist.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismynr.githubuserlist.adapter.FollowersAdapter
import com.ismynr.githubuserlist.databinding.FragmentFollowersBinding
import com.ismynr.githubuserlist.model.Follower
import com.ismynr.githubuserlist.model.User
import com.ismynr.githubuserlist.viewModel.FollowersViewModel

class FollowersFragment : Fragment() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listUser: ArrayList<Follower> = ArrayList()
    private lateinit var adapter: FollowersAdapter
    private lateinit var followerViewModel: FollowersViewModel
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter(listUser)
        binding.rvUsersFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvUsersFollowers.adapter = adapter
        binding.rvUsersFollowers.setHasFixedSize(true)
        followerViewModel = ViewModelProvider(
            this, FollowersViewModel.VMFactory(activity!!.applicationContext)
        ).get(FollowersViewModel::class.java)

        val dataUser = activity!!.intent.getParcelableExtra<User>(EXTRA_DETAIL) as User
        followerViewModel.getAllUserApi(dataUser.username.toString())

        showLoading(true)
        followerViewModel.getListUsers().observe(activity!!, Observer { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.loadingFollowers.visibility = View.VISIBLE
        else binding.loadingFollowers.visibility = View.INVISIBLE
    }
}