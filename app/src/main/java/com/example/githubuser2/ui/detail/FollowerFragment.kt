package com.example.githubuser2.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.adapter.FollowAdapter
import com.example.githubuser2.data.remote.response.FollowResponseItem
import com.example.githubuser2.databinding.FragmentListUserBinding
import com.example.githubuser2.ui.MainViewModel

class FollowerFragment : Fragment() {
    private var _binding: FragmentListUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

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
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.followers.observe(viewLifecycleOwner) { follower ->
            follower?.let { setFollowerData(it) }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.getFollowerData(DetailUserActivity.id)
    }

    private fun setFollowerData(listFollower: List<FollowResponseItem>) {
        val adapter =
            FollowAdapter(listFollower, emptyList(), false)
        if (listFollower.isEmpty()) binding.emptyList.visibility = View.VISIBLE
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }


}