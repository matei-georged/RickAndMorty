package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.CenterZoomLayoutManager.CenterZoomLayoutManager
import com.example.rickandmorty.carouselAdapters.CarouselAdapter
import com.example.rickandmorty.retrofitCharacter.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class EpisodeDetailActivity : AppCompatActivity() {

    private lateinit var mNmae: TextView
    private lateinit var mDate: TextView
    private lateinit var mSeas: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarouselAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)

        setupComponents()
        setupRecycleView()
        setupView()

    }

    private fun setupView() {

        if ( intent.extras != null ) {
            val charId = intent.extras?.getInt("episode_id") ?: -1
            val apiService = ApiService()

            apiService.getEpisodeDetails(charId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if( it != null ) {
                            mNmae.text = it.mNmae
                            mDate.text = it.mData
                            mSeas.text = it.mSeason
                            adapter.mData = it.mCharacters
                        }
                    },
                    { error -> Log.d("mylog",error.message.toString())}
                )

        }
    }

    private fun setupRecycleView() {
        recyclerView = findViewById(R.id.id_episode_detail_rv)
        recyclerView.layoutManager = CenterZoomLayoutManager( this, LinearLayout.HORIZONTAL, false )

        adapter = CarouselAdapter(this, listOf() )
        recyclerView.adapter = adapter
    }

    private fun setupComponents() {
        mNmae = findViewById(R.id.id_episode_detail_name)
        mDate = findViewById(R.id.id_episode_detail_date)
        mSeas = findViewById(R.id.id_episode_detail_season)
    }


}