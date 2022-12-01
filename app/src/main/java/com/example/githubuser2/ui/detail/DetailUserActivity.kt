package com.example.githubuser2.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser2.R
import com.example.githubuser2.adapter.SectionPagerAdapter
import com.example.githubuser2.data.local.entity.Favorite
import com.example.githubuser2.data.local.entity.Follow
import com.example.githubuser2.data.local.helper.ViewModelFactory
import com.example.githubuser2.data.remote.response.DetailUserResponse
import com.example.githubuser2.databinding.ActivityDetailUserBinding
import com.example.githubuser2.ui.MainViewModel
import com.example.githubuser2.ui.favorit.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private var fav: Favorite? = null
    private var isFav = false

    private lateinit var mainViewModel: MainViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    private var _favoriteBinding: ActivityDetailUserBinding? = null
    private val binding get() = _favoriteBinding

    private var followerData = ArrayList<Follow>()
    private var followingData = ArrayList<Follow>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _favoriteBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        favoriteViewModel = obtainViewModel(this@DetailUserActivity)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        supportActionBar?.title = getString(R.string.titleDetail)
        val user = intent.getParcelableExtra<DetailUserResponse>(EXTRA_USER)
        id = user?.login
        mainViewModel.getDetailData(id)
        mainViewModel.getFollowerData(id)
        mainViewModel.getFollowingData(id)

        favoriteViewModel.isFav(id)

        mainViewModel.detailUser.observe(this) { detail ->
            setDetailUserData(detail)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.followers.observe(this) { follower ->
            follower?.forEach {
                val follow = Follow(it.login, it.avatarUrl, id, "1")
                followerData.add(follow)
            }
        }

        mainViewModel.following.observe(this) { following ->
            following?.forEach {
                val foll = Follow(it.login, it.avatarUrl, id, "2")
                followingData.add(foll)
            }
        }

        favoriteViewModel.isFav(id).observe(this) {
            setFavIcon(it)
            isFav = it
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, false)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding?.fabFav?.setOnClickListener {
            if (isFav) {
                cancelAddFav()
            } else {
                addToFav()
            }
        }
    }

    private fun insertFollower() {
        followerData.forEach {
            favoriteViewModel.insertFollowData(it)
        }
    }

    private fun insertFollowing() {
        followingData.forEach {
            favoriteViewModel.insertFollowData(it)
        }
    }

    private fun setDetailUserData(response: DetailUserResponse?) {
        binding?.tvUsername?.text = response?.login ?: "-"
        binding?.tvName?.text = response?.name ?: "-"
        binding?.tvCompany?.text = response?.company ?: "-"
        binding?.tvLocation?.text = response?.location ?: "-"
        binding?.tvRepo?.text = getString(R.string.repo, response?.publicRepos)
        binding?.tvFollowers?.text =
            getString(R.string.follow, response?.following, response?.followers)

        binding?.iVAvatar?.let {
            Glide.with(this@DetailUserActivity)
                .load(response?.avatarUrl)
                .circleCrop()
                .into(it)
        }

        fav = response?.login?.let {
            Favorite(
                it,
                response.company,
                response.publicRepos,
                response.followers,
                response.avatarUrl,
                response.following,
                response.name,
                response.location
            )
        }
    }

    private fun addToFav() {
        fav?.isFavorite = true
        favoriteViewModel.insert(fav)
        insertFollower()
        insertFollowing()
        showToast("Simpan Favorit Berhasil")
    }

    private fun cancelAddFav() {
        favoriteViewModel.delete(fav)
        favoriteViewModel.deleteFollow(fav?.login)
        favoriteViewModel.deleteFollow(fav?.login)
        showToast("Batal Simpan Favorit Berhasil")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    }

    private fun showLoading(state: Boolean) {
        binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun setFavIcon(favorite: Boolean) {
        binding?.fabFav?.setImageResource(if (favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_tab_following,
            R.string.content_tab_follower
        )
        const val EXTRA_USER = "EXTRA_USER"
        var id: String? = ""
    }
}