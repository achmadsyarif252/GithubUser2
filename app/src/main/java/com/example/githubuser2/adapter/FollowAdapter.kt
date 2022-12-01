package com.example.githubuser2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser2.data.local.entity.Follow
import com.example.githubuser2.data.remote.response.FollowResponseItem
import com.example.githubuser2.databinding.ListUserBinding

class FollowAdapter(
    private val listUser: List<FollowResponseItem>,
    private val listFollow: List<Follow>,
    private val isFavFragment: Boolean
) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    class ViewHolder(val userBinding: ListUserBinding) : RecyclerView.ViewHolder(userBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userBinding.tvUsername.text =
            if (isFavFragment) listFollow[position].login else listUser[position].login
        Glide.with(holder.itemView.context)
            .load(if (isFavFragment) listFollow[position].avatarUrl else listUser[position].avatarUrl)
            .circleCrop()
            .into(holder.userBinding.iVAvatar)
    }

    override fun getItemCount() = if (isFavFragment) listFollow.size else listUser.size
}