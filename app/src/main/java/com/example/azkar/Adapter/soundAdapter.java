package com.example.azkar.Adapter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azkar.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class soundAdapter extends RecyclerView.ViewHolder {
    View mView;
    SimpleExoPlayer simpleExoPlayer;
    PlayerView mPlayerView;
    public soundAdapter(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);
        mView=itemView;
    }
    public void setMedia(final Application application,String title, final String url){
        TextView textView=mView.findViewById(R.id.title_vid);
        mPlayerView=mView.findViewById(R.id.player_view);
        textView.setText(title);

        try {
            BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter.Builder(application).build();
            TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(application);
            Uri media=Uri.parse(url);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory("video");
            ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
            MediaSource mediaSource=new ExtractorMediaSource(media,httpDataSourceFactory,extractorsFactory,null,null);
            mPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(false);
        }catch (Exception e){
            Log.d("ttt", "setMedia false gone "+e.toString());

        }

    }
}
