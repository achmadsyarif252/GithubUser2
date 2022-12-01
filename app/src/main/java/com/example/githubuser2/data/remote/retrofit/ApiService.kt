package com.example.githubuser2.data.remote.retrofit

import com.example.githubuser2.data.remote.response.DetailUserResponse
import com.example.githubuser2.data.remote.response.FollowResponseItem
import com.example.githubuser2.data.remote.response.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?")
    fun getUserData(
        @Query("q") login: String?
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") id: String?
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String?
    ): Call<List<FollowResponseItem>>

    @GET("users/{username}/followers")
    fun getListFollower(
        @Path("username") username: String?
    ): Call<List<FollowResponseItem>>
}