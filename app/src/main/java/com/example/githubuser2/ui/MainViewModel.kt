package com.example.githubuser2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser2.data.remote.response.DetailUserResponse
import com.example.githubuser2.data.remote.response.FollowResponseItem
import com.example.githubuser2.data.remote.response.GithubUserResponse
import com.example.githubuser2.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _user = MutableLiveData<List<DetailUserResponse>>()
    val user: LiveData<List<DetailUserResponse>> = _user

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    private val _following = MutableLiveData<List<FollowResponseItem>?>()
    val following: LiveData<List<FollowResponseItem>?> = _following

    private val _follower = MutableLiveData<List<FollowResponseItem>?>()
    val followers: LiveData<List<FollowResponseItem>?> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _alertDialog = MutableLiveData<String>()
    val alertDialog: LiveData<String> = _alertDialog

    fun searchUser(search: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserData(search)
        client.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody.items
                    }
                    if (responseBody?.items?.isEmpty() == true) {
                        _alertDialog.value = "$search tidak ditemukan!"
                    }
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getDetailData(idlogin: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(idlogin)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody
                    }
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getFollowingData(idLogin: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowing(idLogin)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = responseBody
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getFollowerData(idLogin: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollower(idLogin)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _follower.value = responseBody
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

}