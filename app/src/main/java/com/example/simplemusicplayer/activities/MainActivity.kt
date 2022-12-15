package com.example.simplemusicplayer.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemusicplayer.R
import com.example.simplemusicplayer.adapters.MusicAdapter
import com.example.simplemusicplayer.models.SongsModel

class MainActivity : AppCompatActivity() {

    //Initializing songs list
    private var songList = ArrayList<SongsModel>()
    private lateinit var runnable:Runnable
    private var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSongs()

        // getting the recyclerview by its id
        val recyclerView = findViewById<RecyclerView>(R.id.songsRecyclerView)
        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.setHasFixedSize(true)
            val adapter = MusicAdapter(songList)
        recyclerView.adapter = adapter
            //visibility = View.VISIBLE

        /*-------------custom play bar------------*/
        // Play button listener
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this,R.raw.inshallah)


        val playSeekBar: SeekBar = findViewById(R.id.seekbar)
        val playBtn: ImageButton = findViewById(R.id.playBtn)
        val nextBtn:ImageButton = findViewById(R.id.nextBtn)
        val previousBtn:ImageButton = findViewById(R.id.prevBtn)
        val repeatBtn:ImageButton = findViewById(R.id.repeatBtn)
        val replayBtn:ImageButton = findViewById(R.id.replayBtn)
        val forwardBtn:ImageButton = findViewById(R.id.forwardBtn)
        val totalDuration:TextView = findViewById(R.id.totalDuration)
        val currDuration:TextView = findViewById(R.id.currentPos)

        playSeekBar.progress = 0
        playSeekBar.max = mediaPlayer.duration

        val duration = mediaPlayer.duration
        //Song duration
        val minutes = (duration / 60000) % 60000
        val seconds = duration % 60000 / 1000;
        totalDuration.text = String.format("%02d:%02d", minutes, seconds)

        playBtn.setOnClickListener {
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
                playBtn.setImageResource(R.drawable.pause_btn)
            }else{
                mediaPlayer.pause()
                playBtn.setImageResource(R.drawable.play_btn)
            }

            playSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, position: Int, changed: Boolean) {
                    //play the seekbar pointed position
                    if (changed){
                        mediaPlayer.seekTo(position)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })

            runnable = Runnable {
                //progress bar position
                playSeekBar.progress = mediaPlayer.currentPosition
                //current position of the song
                val currentDuration = mediaPlayer.currentPosition
                val currMinutes = (currentDuration / 60000) % 60000
                val currSeconds = currentDuration % 60000 / 1000;
                currDuration.text = String.format("%02d:%02d", currMinutes, currSeconds)

                //forward 10 seconds
                forwardBtn.setOnClickListener {
                    mediaPlayer.seekTo(mediaPlayer.currentPosition + (10 * 1000))
                    currDuration.text = String.format("%02d:%02d", currMinutes, currSeconds)
                }

                //forward 10 seconds
                replayBtn.setOnClickListener {
                    mediaPlayer.seekTo(mediaPlayer.currentPosition - (10 * 1000))
                    currDuration.text = String.format("%02d:%02d", currMinutes, currSeconds)
                }

                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)
            //point seekbar to zero after finish

            mediaPlayer.setOnCompletionListener {
                playBtn.setImageResource(R.drawable.play_btn)
                playSeekBar.progress = 0
            }
        }
        //next song
        repeatBtn.setOnClickListener {
            if (!mediaPlayer.isLooping){
                repeatBtn.setImageResource(R.drawable.repeat_one_icon)
                mediaPlayer.isLooping = true
            }else{
                repeatBtn.setImageResource(R.drawable.repeat_icon)
                mediaPlayer.isLooping = false
            }
        }
        //previous song
        previousBtn.setOnClickListener {
            Toast.makeText(it.context, "Previous song is playing", Toast.LENGTH_SHORT).show()
        }
        //replay 10 seconds
        nextBtn.setOnClickListener {
            Toast.makeText(it.context, "Next song is playing", Toast.LENGTH_SHORT).show()
        }


    }

    private fun loadSongs() {
        songList.add(SongsModel("Audio 1", "R.raw.inshallah", 3))
        songList.add(SongsModel("Audio 2", "R.raw.freedom",  3))
        songList.add(SongsModel("Audio 3", "R.raw.mother",  2))
    }

}