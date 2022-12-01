package com.example.githubuser2.ui.favorit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser2.data.FavoriteRepository
import com.example.githubuser2.data.local.entity.Favorite
import com.example.githubuser2.data.local.entity.Follow

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavorite(): LiveData<List<Favorite>> {
        _isLoading.value = true
        val user = mFavoriteRepository.getALlFav()
        _isLoading.value = false
        return user
    }

    fun getALlFollowers(login: String?): LiveData<List<Follow>> {
        _isLoading.value = true
        val follower = mFavoriteRepository.getAllFollower(login)
        _isLoading.value = false
        return follower
    }

    fun getALlFollowing(login: String?): LiveData<List<Follow>> {
        _isLoading.value = true
        val following = mFavoriteRepository.getAllFollowing(login)
        _isLoading.value = false
        return following
    }

    fun insert(user: Favorite?) {
        mFavoriteRepository.insert(user)
    }

    fun insertFollowData(user: Follow) {
        mFavoriteRepository.insertFollow(user)
    }

    fun delete(user: Favorite?) {
        mFavoriteRepository.delete(user)
    }

    fun deleteFollow(login: String?) {
        mFavoriteRepository.deleteFollow(login)
    }

    fun isFav(login: String?): LiveData<Boolean> {
        return mFavoriteRepository.isFav(login ?: "")
    }
}