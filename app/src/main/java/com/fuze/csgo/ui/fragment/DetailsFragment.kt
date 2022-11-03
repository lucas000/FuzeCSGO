package com.fuze.csgo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.fuze.csgo.R
import com.fuze.csgo.databinding.FragmentDetailsBinding
import com.fuze.csgo.ui.FuzeViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fuze.csgo.other.Status
import com.fuze.csgo.other.Utils
import com.fuze.csgo.ui.adapter.AdapterPlayersTeamOne
import com.fuze.csgo.ui.adapter.AdapterPlayersTeamTwo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private val viewModel: FuzeViewModel by activityViewModels()
    private val adapterPlayerOne: AdapterPlayersTeamOne by lazy { AdapterPlayersTeamOne() }
    private val adapterPlayerTwo: AdapterPlayersTeamTwo by lazy { AdapterPlayersTeamTwo() }
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
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

            when(args.matchItem.status) {
                "not_started" -> { txtTimeMatch.text = Utils.getDateTime(args.matchItem.scheduled_at!!)}
                "started" -> { txtTimeMatch.text = "AGORA" }
                "running" -> { txtTimeMatch.text = "AGORA" }
                "finished" -> { txtTimeMatch.text = "Encerrado" }
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
        viewModel.teams.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        adapterPlayerOne?.items = result.data?.get(0)?.players!!.toMutableList()
                        adapterPlayerTwo?.items = result.data?.get(1)!!.players!!.toMutableList()
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}