package com.fuze.csgo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fuze.csgo.R
import com.fuze.csgo.databinding.FragmentDetailsBinding
import com.fuze.csgo.ui.FuzeViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fuze.csgo.other.Status
import com.fuze.csgo.ui.adapter.AdapterPlayers
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    lateinit var viewModel: FuzeViewModel
    private val adapterPlayerOne: AdapterPlayers by lazy { AdapterPlayers() }
    private val adapterPlayerTwo: AdapterPlayers by lazy { AdapterPlayers() }
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        viewModel = requireActivity().run {
            ViewModelProvider(this)[FuzeViewModel::class.java]
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            rv_player_one.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = this@DetailsFragment.adapterPlayerOne
            }
            rv_player_two.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = this@DetailsFragment.adapterPlayerTwo
            }
            args.matchItem.let { match ->
                txtLeagueSerie.text = "${match.league?.name + ' ' +  match.serie?.name}"
                txt_team_one.text = match.opponents?.get(0)?.opponent?.name
                txt_team_two.text = match.opponents?.get(1)?.opponent?.name
                Glide.with(img_team_one).load(match.opponents?.get(0)?.opponent?.image_url).into(img_team_one)
                Glide.with(img_team_two).load(match.opponents?.get(1)?.opponent?.image_url).into(img_team_two)
            }

            imgBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }

        setupToObservers()
        viewModel.getTeamMembers(
            args.matchItem?.opponents?.get(0)?.opponent?.id!!,
            args.matchItem?.opponents?.get(1)?.opponent?.id!!,
        )
    }

    private fun setupToObservers() {
        viewModel?.teams?.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        adapterPlayerOne?.items = result.data?.get(0)?.players!!.toMutableList()
                        adapterPlayerTwo?.items = result.data?.get(1)!!.players!!.toMutableList()
                    }

                    Status.ERROR -> {
                        Snackbar.make(binding!!.root, getString(R.string.app_name), Snackbar.LENGTH_LONG).show()
                    }
                    Status.LOADING -> { }
                }
            }
        }
    }
}