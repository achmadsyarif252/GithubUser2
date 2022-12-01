package com.example.githubuser2.data.local.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser2.data.local.entity.Favorite

class FavDiffCallback(
    private val mOldFavList: List<Favorite>,
    private val mNewFavList: List<Favorite>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].login == mNewFavList[newItemPosition].login

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorit = mOldFavList[oldItemPosition]
        val newFavorit = mNewFavList[newItemPosition]
        return oldFavorit.name == newFavorit.name && oldFavorit.publicRepos == newFavorit.publicRepos
    }
}