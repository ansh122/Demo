package com.v_care.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.v_care.R;

/**
 * Created by lenovo on 31-Mar-17.
 */
public class Video_viewActivity extends Activity
{
    static ProgressDialog mProgressDialog;
    VideoView videoview;
    String video_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video_view);
        videoview = (VideoView) findViewById(R.id.VideoView);

        Intent intent=getIntent();
        video_link=intent.getStringExtra("video");

       /* playVideo();
        showProgressDialog();*/

        new StreamVideo().execute();

    }
    //webservices using asynctask
    private class StreamVideo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressbar
            showProgressDialog();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            try {
                // Start the MediaController
                MediaController mediacontroller = new MediaController(Video_viewActivity.this);
                mediacontroller.setAnchorView(videoview);
                // Get the URL from String VideoURL
                Uri video = Uri.parse(video_link);
                videoview.setMediaController(mediacontroller);
                videoview.setVideoURI(video);

                videoview.requestFocus();
                videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                       hideProgressDialog();
                        videoview.start();
                    }
                });
            } catch (Exception e) {
               hideProgressDialog();
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        }

    }
    //method created
    public void playVideo()
    {
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(Video_viewActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(video_link);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            videoview.suspend();
            hideProgressDialog();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp)
            {
                videoview.start();
                hideProgressDialog();

            }
        });

    }

  //method for progress dialog
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this, R.style.Custom);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }
    //method to dismiss progress dialog
    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
