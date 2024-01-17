package com.example.destinationapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SummaryAdapter(private val dataList: ArrayList<ParcelModel>): RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textviewRound: TextView = view.findViewById(R.id.textview_round)
        val textviewDistance: TextView = view.findViewById(R.id.textview_distance)
        val textviewScore: TextView = view.findViewById(R.id.textview_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.summary_score_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val realDistance = dataList[position].distance
        val realScore = dataList[position].score

        holder.textviewRound.text = (position + 1).toString()
        holder.textviewDistance.text = "$realDistance kilometres far away"
        holder.textviewScore.text = "$realScore score"
    }
}