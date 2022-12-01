package com.example.githubuser2.data.remote.response

import com.google.gson.annotations.SerializedName

data class FollowResponseItem(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

)
