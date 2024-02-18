package com.aditys.themusicalchemy

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

interface ItemClickListener {
    fun onItemClick(position: Int)
}

class MyAdapter(val context: Activity, val dataList: List<Data>, val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = dataList[position]

        val mediaPlayer = MediaPlayer.create(context, currentData.preview.toUri())
        holder.title.text = currentData.title

        Picasso.get().load(currentData.album.cover).into(holder.image);

        holder.play.setOnClickListener {
            mediaPlayer.start()
        }
        holder.pause.setOnClickListener {
            mediaPlayer.stop()
        }

        // Set click listener for item view
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.musicImage)
        val title: TextView = itemView.findViewById(R.id.musicTitle)
        val play: ImageButton = itemView.findViewById(R.id.btnPlay)
        val pause: ImageButton = itemView.findViewById(R.id.btnPause)
    }
}
