package com.fuze.csgo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fuze.csgo.R
import com.fuze.csgo.model.match.MatchResponse

class AdapterMatches(
    private val onItemClickListener: (MatchResponse) -> Unit
) : RecyclerView.Adapter<AdapterMatches.MatchViewHolder>() {

    var items: MutableList<MatchResponse> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MatchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val teamOne: TextView = itemView.findViewById(R.id.txt_team_one)
        private val imgTeamOne: ImageView = itemView.findViewById(R.id.img_team_one)
        private val teamTwo: TextView = itemView.findViewById(R.id.txt_team_two)
        private val imgTeamTwo: ImageView = itemView.findViewById(R.id.img_team_two)
        private val imgLeague: ImageView = itemView.findViewById(R.id.img_league)
        private val league: TextView = itemView.findViewById(R.id.txt_league)

        fun bind(item: MatchResponse, onItemClickListener: (MatchResponse) -> Unit) {
            teamOne.text = item.opponents?.get(0)?.name
            Glide.with(imgTeamOne).load(item.opponents?.get(0)?.image_url).into(imgTeamOne)

            teamTwo.text = item.opponents?.get(1)?.name
            Glide.with(imgTeamTwo).load(item.opponents?.get(1)?.image_url).into(imgTeamTwo)

            Glide.with(imgLeague).load(item.opponents?.get(1)?.image_url).into(imgLeague)
            league.text = item.league?.name + item.serie?.name

            itemView.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.matches_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener)
    }

    override fun getItemCount(): Int = items.size
}