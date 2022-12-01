package com.example.githubuser2.ui.favorit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.R
import com.example.githubuser2.adapter.FollowAdapter
import com.example.githubuser2.data.local.entity.Follow
import com.example.githubuser2.databinding.FragmentListUserBinding

class FavoriteFollowingFragment : Fragment() {
    private var _binding: FragmentListUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.layoutManager = layoutManager

        val favoriteViewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]

        favoriteViewModel.getALlFollowing(DetailFavoriteUser.id)
            .observe(viewLifecycleOwner) { following ->
                setFollowingData(following)
            }

        favoriteViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setFollowingData(listFollowing: List<Follow>) {
        val adapter = FollowAdapter(emptyList(), listFollowing, true)
        if (listFollowing.isEmpty()) {
            binding.emptyList.text = getString(R.string.empty_following)
            binding.emptyList.visibility = View.VISIBLE
        }
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}