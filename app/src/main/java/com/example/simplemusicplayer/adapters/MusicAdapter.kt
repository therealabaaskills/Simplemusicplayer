package com.example.simplemusicplayer.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemusicplayer.R
import com.example.simplemusicplayer.models.SongsModel

class MusicAdapter(private val musicList: ArrayList<SongsModel>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>(){



        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val songName:TextView = itemView.findViewById(R.id.songName)
            val songDuration:TextView = itemView.findViewById(R.id.songDuration)
            val playSongBtn:ImageButton = itemView.findViewById(R.id.songsRecyclerViewPlayIcon)
            //media player
          //  var mplayer: MediaPlayer? = null
            // Play button listener
            /*val playBtn: ImageButton = itemView.findViewById(R.id.playBtn)
            val playSeekBar: SeekBar = itemView.findViewById(R.id.seekbar)
            val nextBtn:ImageButton = itemView.findViewById(R.id.nextBtn)
            val previousBtn:ImageButton = itemView.findViewById(R.id.prevBtn)
            val repeatBtn:ImageButton = itemView.findViewById(R.id.repeatBtn)
            val replayBtn:ImageButton = itemView.findViewById(R.id.replayBtn)
            val forwardBtn:ImageButton = itemView.findViewById(R.id.forwardBtn)
            val totalDuration:TextView = itemView.findViewById(R.id.totalDuration)
            val currDuration:TextView = itemView.findViewById(R.id.currentPos)*/
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Inflating card view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_songs, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currSong = musicList[position]
        holder.songName.text = currSong.songName
        holder.songDuration.text = currSong.songDuration.toString()
        holder.playSongBtn.setImageResource(R.drawable.play_btn)

        val mplayer: MediaPlayer = MediaPlayer()
        //var mplayerData = mplayer!!.setDataSource(currSong.songPath)
            holder.playSongBtn.setOnClickListener {
                Toast.makeText(it.context, currSong.songPath.toString(), Toast.LENGTH_SHORT).show()
                holder.playSongBtn.setImageResource(R.drawable.pause_btn)
            }
    }

    override fun getItemCount(): Int = musicList.size
}