package com.example.githubuser2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser2.ui.detail.FollowerFragment
import com.example.githubuser2.ui.detail.FollowingFragment
import com.example.githubuser2.ui.favorit.FavoriteFollowerFragment
import com.example.githubuser2.ui.favorit.FavoriteFollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val isFragmentFav: Boolean) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = if (isFragmentFav) FavoriteFollowingFragment() else FollowingFragment()
            1 -> fragment = if (isFragmentFav) FavoriteFollowerFragment() else FollowerFragment()
        }
        return fragment as Fragment
    }
}