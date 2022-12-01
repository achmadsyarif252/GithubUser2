package com.example.githubuser2.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser2.data.local.entity.Favorite
import com.example.githubuser2.data.local.entity.Follow

@Dao
interface FavoriteDao {
    @Query("SELECT EXISTS(SELECT * FROM FavoriteUser where login = :login)")
    fun isFavoriteUser(login: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Favorite?)

    @Delete
    fun delete(user: Favorite?)

    @Query("SELECT * FROM FavoriteUser ORDER BY login ASC")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT * FROM Follow where owner = :owner AND type = 1")
    fun getFollowerData(owner: String?): LiveData<List<Follow>>

    @Query("SELECT * FROM Follow where owner = :owner AND type = 2")
    fun getFollowingData(owner: String?): LiveData<List<Follow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Follow)

    @Query("DELETE FROM Follow where owner = :owner")
    fun deleteFollower(owner: String?)

}