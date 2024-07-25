package com.example.asynctaskbannerdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView bannerTextView;
    private Button startButton;
    private Button stopButton;
    private BannerAsyncTask bannerAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerTextView = findViewById(R.id.bannerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerAsyncTask = new BannerAsyncTask();
                bannerAsyncTask.execute();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bannerAsyncTask != null) {
                    bannerAsyncTask.cancel(true);
                }
            }
        });
    }

    private class BannerAsyncTask extends AsyncTask<Void, Float, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bannerTextView.setSelected(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            float position = 1.0f;
            while (!isCancelled()) {
                try {
                    Thread.sleep(30);
                    position -= 0.01f;
                    if (position < -1.0f) {
                        position = 1.0f;
                    }
                    publishProgress(position);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            super.onProgressUpdate(values);
            bannerTextView.setTranslationX(bannerTextView.getWidth() * values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            bannerTextView.setTranslationX(0);
        }
    }
}