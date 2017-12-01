package com.example.ahmed.bakingapp;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ahmed.bakingapp.Models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailsFragment extends Fragment {

    private ArrayList<Step> stepArrayList;
    private int position;
    private Step step;
    private SimpleExoPlayer player;
    private long currentPosition;
    private boolean isPlaying = true;

    @Nullable
    @BindView(R.id.step_description)
    TextView stepDescriptionText;
    @BindView(R.id.next_step_btn)
    ImageButton nextButton;
    @BindView(R.id.back_step_btn)
    ImageButton backButton;
    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);

        playerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.thumbnail));

        stepArrayList = Parcels.unwrap(getArguments().getParcelable("steps_list"));

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("position"))
                position = savedInstanceState.getInt("position");
            if (savedInstanceState.containsKey("playbackPosition"))
                currentPosition = savedInstanceState.getLong("playbackPosition");
            if (savedInstanceState.containsKey("isPlaying"))
                isPlaying = savedInstanceState.getBoolean("isPlaying");
        } else {
            position = getArguments().getInt("position") - 3;
        }

        step = stepArrayList.get(position);
        if (stepDescriptionText != null)
            stepDescriptionText.setText(step.getDescription());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < stepArrayList.size() - 1) {
                    changeStep(++position);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    changeStep(--position);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null)
            initializePlayer(Uri.parse(step.getVideoURL()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        currentPosition = player.getCurrentPosition();
        isPlaying = player.getPlayWhenReady();
        outState.putInt("position", position);
        outState.putLong("playbackPosition", currentPosition);
        outState.putBoolean("isPlaying", isPlaying);
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            playerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(getContext(), userAgent);

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    dataSourceFactory,
                    new DefaultExtractorsFactory(), null, null);

            player.prepare(mediaSource);
            player.setPlayWhenReady(isPlaying);
            player.seekTo(currentPosition);
        }
    }

    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    private void changeStep(int position) {
        step = stepArrayList.get(position);
        currentPosition = 0;
        isPlaying = true;
        releasePlayer();
        initializePlayer(Uri.parse(step.getVideoURL()));
        if (stepDescriptionText != null)
            stepDescriptionText.setText(step.getDescription());
    }
}
