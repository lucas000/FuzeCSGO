package com.fuze.csgo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
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

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    lateinit var viewModel: FuzeViewModel
    private val adapter: AdapterMatches by lazy { AdapterMatches(::onItemClickListener) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = requireActivity().run {
            ViewModelProvider(this)[FuzeViewModel::class.java]
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
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
        viewModel?.matches?.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        adapter?.items = result.data!!.toMutableList()
                    }

                    Status.ERROR -> {
                        Snackbar.make(binding!!.root, getString(R.string.app_name), Snackbar.LENGTH_LONG).show()
                    }
                    Status.LOADING -> { }
                }
            }
        }
    }

    private fun getMatchesList() {
        lifecycleScope.launch {
            viewModel.getMatchesList()
        }
    }

    private fun onItemClickListener(item: MatchResponse) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
    }

}