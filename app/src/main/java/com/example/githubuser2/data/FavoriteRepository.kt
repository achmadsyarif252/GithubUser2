package com.example.githubuser2.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser2.data.local.entity.Favorite
import com.example.githubuser2.data.local.entity.Follow
import com.example.githubuser2.data.local.room.FavoriteDao
import com.example.githubuser2.data.local.room.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favDao()
    }

    fun getALlFav(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorite()
    fun getAllFollower(login: String?): LiveData<List<Follow>> = mFavoriteDao.getFollowerData(login)
    fun getAllFollowing(login: String?): LiveData<List<Follow>> = mFavoriteDao.getFollowingData(login)
    fun isFav(login: String): LiveData<Boolean> = mFavoriteDao.isFavoriteUser(login)

    fun insert(user: Favorite?) {
        executorService.execute { mFavoriteDao.insert(user) }
    }

    fun insertFollow(user: Follow) {
        executorService.execute { mFavoriteDao.insert(user) }
    }

    fun delete(user: Favorite?) {
        executorService.execute { mFavoriteDao.delete(user) }
    }

    fun deleteFollow(login: String?) {
        executorService.execute { mFavoriteDao.deleteFollower(login) }
    }
}