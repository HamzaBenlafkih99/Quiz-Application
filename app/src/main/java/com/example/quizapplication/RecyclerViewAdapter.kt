package com.example.quizapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(var listData: List<Chapitre>, val clickListener: Clicklistener):RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var titre: TextView

        init {
            name = view.findViewById(R.id.chapitre_name)
            titre = view.findViewById(R.id.chapitre_titre)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chapitre_item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = listData.get(position).nom
        holder.titre.text = listData.get(position).description

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(listData.get(position))
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    interface Clicklistener{
        fun onItemClick(dataModel: Chapitre)
    }

}