package com.example.githubuser2.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FavoriteUser")
@Parcelize
class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "public_repos")
    var publicRepos: Int = 0,

    @ColumnInfo(name = "followers")
    var followers: Int = 0,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = "",

    @ColumnInfo(name = "following")
    var following: Int = 0,

    @field:ColumnInfo(name = "name")
    var name: String? = "",

    @field:ColumnInfo(name = "location")
    var location: String? = "",

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean = false,
) : Parcelable