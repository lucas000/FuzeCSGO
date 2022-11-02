package com.fuze.csgo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fuze.csgo.R
import com.fuze.csgo.model.team.PlayerResponse

class AdapterPlayers: RecyclerView.Adapter<AdapterPlayers.MatchViewHolder>() {

    var items: MutableList<PlayerResponse> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MatchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val imgPlayer: ImageView = itemView.findViewById(R.id.img_player)
        private val nickame: TextView = itemView.findViewById(R.id.txt_nick)
        private val name: TextView = itemView.findViewById(R.id.txt_name)

        fun bind(item: PlayerResponse) {
            nickame.text = item.name
            name.text = item.first_name + " " + item.last_name
            Glide.with(imgPlayer).load(item.image_url).into(imgPlayer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.players_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}