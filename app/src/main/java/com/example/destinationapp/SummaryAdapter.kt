package com.example.destinationapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Zabezpečuje sa, že zo dátového zoznamu ,,dataList" sa zobrazia všetky dáta aj s ich hodnotami v RecyclerView
class SummaryAdapter(private val dataList: ArrayList<ParcelModel>): RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {
    //zabranuje opakovanemu volaniu pomocou findViewById a uchovava referencie "Views" vo vnutri poloziek zoznamu
    //zobrazi taktiez textove pole daneho kola, vzdialenosti a skore pouzivatela
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textviewRound: TextView = view.findViewById(R.id.textview_round)
        val textviewDistance: TextView = view.findViewById(R.id.textview_distance)
        val textviewScore: TextView = view.findViewById(R.id.textview_score)
    }

    //vytvori nove instancie ViewHolderu, a rozbaluje vsetky definovane layouty pre polozky v summary_score_list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.summary_score_list, parent, false)
        return ViewHolder(view)
    }

    //vrati celkovy pocet poloziek z dataList, aby RecyclerView vedel, kolko ich ma zobrazit
    override fun getItemCount(): Int {
        return dataList.size
    }

    //priradi data z dataList k ViewHolderu, a vyplnuje data pre kazdu polozku v RecyclerView
    //ziskava data z datalistu na zaklade pozicie a nasledne ich zobrazi cez holder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val realDistance = dataList[position].distance
        val realScore = dataList[position].score

        holder.textviewRound.text = (position + 1).toString()
        holder.textviewDistance.text = "$realDistance kilometres far away"
        holder.textviewScore.text = "$realScore score"
    }
}