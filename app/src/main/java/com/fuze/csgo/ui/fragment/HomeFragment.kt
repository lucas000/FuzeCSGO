package com.fuze.csgo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fuze.csgo.R
import com.fuze.csgo.databinding.FragmentHomeBinding
import com.fuze.csgo.model.match.MatchResponse
import com.fuze.csgo.ui.FuzeViewModel
import com.fuze.csgo.other.Status
import com.fuze.csgo.ui.adapter.AdapterMatches
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import androidx.fragment.app.activityViewModels

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    val viewModel: FuzeViewModel by activityViewModels()
    private val adapter: AdapterMatches by lazy { AdapterMatches(::onItemClickListener) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            rvMatchesList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = this@HomeFragment.adapter
            }
        }

        setupToObservers()
        getMatchesList()
    }

    private fun setupToObservers() {
        viewModel?.matches.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        var monthList: List<MatchResponse> = result.data!!.filter { item -> item.opponents?.size == 2 }
                        adapter?.items = monthList.toMutableList()
                        viewModel.setStateLoading(false)
                    }

                    Status.ERROR -> {
                        Snackbar.make(binding!!.root, getString(R.string.app_name), Snackbar.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        viewModel.setStateLoading(true)
                    }
                }
            }
        }
    }

    private fun getMatchesList() {
        lifecycleScope.launch {
            viewModel.getMatchesList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onItemClickListener(item: MatchResponse) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
    }
}