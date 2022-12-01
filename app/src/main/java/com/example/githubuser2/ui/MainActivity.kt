package com.example.githubuser2.ui
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.R
import com.example.githubuser2.adapter.ListUserAdapter
import com.example.githubuser2.data.local.helper.ViewModelFactory
import com.example.githubuser2.data.remote.response.DetailUserResponse
import com.example.githubuser2.databinding.ActivityMainUserBinding
import com.example.githubuser2.ui.detail.DetailUserActivity
import com.example.githubuser2.ui.favorit.FavoriteUserActivity
import com.example.githubuser2.ui.theme.ThemeSettingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainUserBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var detailUser: DetailUserResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewModel = obtainViewModel(this@MainActivity)

        mainViewModel.user.observe(this) { user ->
            setUserData(user)
            if (user.isNotEmpty()) activityMainBinding.tvInfo.visibility = View.GONE
        }


        mainViewModel.detailUser.observe(this) { detail ->
            if (detail != null) {
                detailUser = DetailUserResponse(
                    detail.login,
                    detail.company,
                    detail.publicRepos,
                    detail.followers,
                    detail.avatarUrl,
                    detail.following, detail.name,
                    detail.location
                )
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.alertDialog.observe(this) { msg ->
            val alertDialogBuilder = AlertDialog.Builder(this)
            with(alertDialogBuilder) {
                setTitle("Info")
                setMessage(msg)
                setCancelable(false)
                setNegativeButton(getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            activityMainBinding.tvInfo.visibility = View.VISIBLE
        }

        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUser.layoutManager = layoutManager
        activityMainBinding.progressBar.visibility = View.INVISIBLE
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    private fun showLoading(state: Boolean) {
        activityMainBinding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setUserData(user: List<DetailUserResponse>) {
        val adapter = ListUserAdapter(user)
        activityMainBinding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailUserResponse) {
                val i = Intent(this@MainActivity, DetailUserActivity::class.java)
                i.putExtra(DetailUserActivity.EXTRA_USER, data)
                startActivity(i)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.searchUser(query)
                searchView.clearFocus()
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favoriteUser -> {
                val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.themeSetting -> {
                val i = Intent(this@MainActivity, ThemeSettingActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }
}