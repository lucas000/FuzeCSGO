package com.fuze.csgo.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fuze.csgo.R
import com.fuze.csgo.model.match.MatchResponse
import com.fuze.csgo.other.Utils

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
        private val date: TextView = itemView.findViewById(R.id.txt_date_match)

        fun bind(item: MatchResponse, onItemClickListener: (MatchResponse) -> Unit) {
            teamOne.text = item.opponents?.get(0)?.opponent?.name
            Glide.with(imgTeamOne).load(item.opponents?.get(0)?.opponent?.image_url).into(imgTeamOne)

            teamTwo.text = item.opponents?.get(1)?.opponent?.name
            Glide.with(imgTeamTwo).load(item.opponents?.get(1)?.opponent?.image_url).into(imgTeamTwo)

            Glide.with(imgLeague).load(item.league?.image_url).into(imgLeague)
            league.text = "${item.league?.name + ' ' +  item.serie?.name}"

            when(item.status) {
                "not_started" -> {
                    setColorDate(date, Utils.getDateTime(item.scheduled_at!!).toString(), R.color.background_grey)
                }
                "running" -> {
                    setColorDate(date, "AGORA", R.color.background_red)
                }
                "started" -> {
                    setColorDate(date, "AGORA", R.color.background_red)
                }
                "finished" -> {
                    setColorDate(date, "Cancelado", R.color.background_grey)
                }
                "canceled" -> {
                    setColorDate(date, "Cancelado", R.color.background_red)
                }
            }
            itemView.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }

        private fun setColorDate(dateView: View, value: String, rsId: Int) {
            var textDrawable: Drawable? = dateView.background
            textDrawable = DrawableCompat.wrap(textDrawable!!)
            DrawableCompat.setTint(textDrawable, rsId)
            dateView.background = textDrawable
            date.text = value
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