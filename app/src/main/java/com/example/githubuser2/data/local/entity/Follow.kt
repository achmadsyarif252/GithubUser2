package com.example.githubuser2.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Follow")
@Parcelize
class Follow(
    @PrimaryKey
    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = "",

    @ColumnInfo(name = "owner")
    var owner: String? = "",

    @ColumnInfo(name = "type")
    var type: String? = ""

) : Parcelable