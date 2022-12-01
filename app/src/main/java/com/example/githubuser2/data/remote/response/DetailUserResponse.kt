package com.example.githubuser2.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubUserResponse(
    @field:SerializedName("items")
    val items: List<DetailUserResponse>
)

@Parcelize
data class DetailUserResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("location")
    val location: String?,
) : Parcelable
