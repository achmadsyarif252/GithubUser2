package com.example.githubuser2.ui.favorit

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.R
import com.example.githubuser2.adapter.FavoriteUserAdapter
import com.example.githubuser2.data.local.entity.Favorite
import com.example.githubuser2.data.local.helper.ViewModelFactory
import com.example.githubuser2.databinding.FragmentListUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private var _activityFavoriteBinding: FragmentListUserBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var favAdapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = FragmentListUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.emptyList?.text = getString(R.string.guide_add_favorite)

        val favoriteViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        favoriteViewModel.getAllFavorite().observe(this) { userList ->
            if (userList != null) favAdapter.setListUser(userList)
            if (userList.isEmpty()) binding?.emptyList?.visibility = View.VISIBLE
        }
        favAdapter = FavoriteUserAdapter()
        binding?.rvUser?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favAdapter
        }

        favAdapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Favorite) {
                val intent = Intent(this@FavoriteUserActivity, DetailFavoriteUser::class.java)
                intent.putExtra(DetailFavoriteUser.EXTRA_USER, data)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}