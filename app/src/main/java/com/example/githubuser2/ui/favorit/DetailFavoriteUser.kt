package com.example.githubuser2.ui.favorit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser2.R
import com.example.githubuser2.adapter.SectionPagerAdapter
import com.example.githubuser2.data.local.entity.Favorite
import com.example.githubuser2.data.local.helper.ViewModelFactory
import com.example.githubuser2.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFavoriteUser : AppCompatActivity() {
    private var user: Favorite? = null
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _favUserBinding: ActivityDetailUserBinding? = null
    private val binding get() = _favUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _favUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.progressBar?.visibility = View.GONE

        supportActionBar?.title = getString(R.string.title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.fabFav?.setImageResource(R.drawable.ic_baseline_delete_24)

        favoriteViewModel = obtainViewModel(this@DetailFavoriteUser)
        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        user = intent.getParcelableExtra(EXTRA_USER)
        id = user?.login
        setDetailUserData(user)
        binding?.fabFav?.setOnClickListener {
            showAlertDialog(user as Favorite)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, true)
        val viewPager: ViewPager2? = binding?.viewPager
        viewPager?.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        viewPager?.let {
            TabLayoutMediator(tabs, it) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

    }

    private fun setDetailUserData(user: Favorite?) {
        binding?.tvUsername?.text = user?.login ?: "-"
        binding?.tvName?.text = user?.name ?: "-"
        binding?.tvCompany?.text = user?.company ?: "-"
        binding?.tvLocation?.text = user?.location ?: "-"
        binding?.tvFollowers?.text = user?.followers.toString()
        binding?.tvRepo?.text = user?.publicRepos.toString()
        binding?.tvRepo?.text = getString(R.string.repo, user?.publicRepos)
        binding?.tvFollowers?.text =
            getString(R.string.follow, user?.following, user?.followers)

        binding?.let {
            Glide.with(this@DetailFavoriteUser)
                .load(user?.avatarUrl)
                .circleCrop()
                .into(it.iVAvatar)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showAlertDialog(user: Favorite) {
        val dialogMessage: String = getString(R.string.message_delete)
        val dialogTitle: String = getString(R.string.delete)

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                favoriteViewModel.delete(user)
                Toast.makeText(
                    this@DetailFavoriteUser,
                    "Berhasil dihapus dari favorit",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showLoading(state: Boolean) {
        binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_tab_following,
            R.string.content_tab_follower
        )
        var id: String? = ""
        const val EXTRA_USER = "extra_user"
    }
}