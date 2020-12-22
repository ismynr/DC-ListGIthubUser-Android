package com.ismynr.githubuserlist.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.view.fragment.FollowersFragment
import com.ismynr.githubuserlist.view.fragment.FollowingFragment

class ViewPagerFollAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val pages = listOf(
            FollowingFragment(),
            FollowersFragment()
        )

        private val tabTitles = intArrayOf(
            R.string.following,
            R.string.followers
        )

        override fun getItem(position: Int): Fragment {
            return pages[position]
        }

        override fun getCount(): Int {
            return pages.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mContext.resources.getString(tabTitles[position])
        }
}