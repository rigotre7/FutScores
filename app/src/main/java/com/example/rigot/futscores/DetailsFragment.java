package com.example.rigot.futscores;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    Button goalsButton;
    VideoView video;

    public DetailsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        goalsButton = (Button) view.findViewById(R.id.goalsButton);
        video = (VideoView)view.findViewById(R.id.videoView);
        video.setVisibility(View.GONE);



        goalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaController mediaController = new MediaController(getContext());
                mediaController.setAnchorView(video);

                video.setVisibility(View.VISIBLE);
                Uri file = Uri.parse("http://cdn-e1.streamable.com/video/mp4/j0ql0_1.mp4?token=1485795563_b754448a62770a4f9d86d3aa373b820b377866d0");
                video.setMediaController(mediaController);
                video.setVideoURI(file);
                video.start();
            }
        });



        return view;
    }

}
