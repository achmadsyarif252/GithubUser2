package com.example.githubuser2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser2.data.local.entity.Favorite
import com.example.githubuser2.data.local.helper.FavDiffCallback
import com.example.githubuser2.databinding.ListUserBinding

class FavoriteUserAdapter :
    RecyclerView.Adapter<FavoriteUserAdapter.MyViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    private val listUser = ArrayList<Favorite>()

    class MyViewHolder(val binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fav: Favorite) {
            binding.tvUsername.text = fav.login
            Glide.with(itemView.context)
                .load(fav.avatarUrl)
                .circleCrop()
                .into(binding.iVAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listUser[holder.absoluteAdapterPosition])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.absoluteAdapterPosition ])
        }
    }

    override fun getItemCount() = listUser.size

    fun setListUser(listFav: List<Favorite>) {
        val diffCallback = FavDiffCallback(this.listUser, listFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listFav)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }

}