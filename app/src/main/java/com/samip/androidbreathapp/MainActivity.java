package com.samip.androidbreathapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.samip.androidbreathapp.util.Prefs;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView breathTakenTxt;
    private TextView lastBreathTakenTxt;
    private TextView todayBreathTakenTxt;
    private TextView oneMinbreathTakenTxt;
    private TextView guideTxt;
    private Button startBtn;
    private ImageView imageView;

    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        breathTakenTxt = findViewById(R.id.breathTakenTxt);
        lastBreathTakenTxt = findViewById(R.id.lastBreathTakenTxt);
        todayBreathTakenTxt = findViewById(R.id.todayBreathTakenTxt);
        oneMinbreathTakenTxt = findViewById(R.id.oneMinuteBreathTxt);
        guideTxt = findViewById(R.id.guideTxt);
        imageView = findViewById(R.id.breathTakenImage);
        startBtn = findViewById(R.id.startBtn);
        prefs = new Prefs(this);

        startIntroAnimation();

        // setting all the data into field
        breathTakenTxt.setText(MessageFormat.format("{0} Breaths", prefs.getBreaths()));
        todayBreathTakenTxt.setText(MessageFormat.format("{0} Min Today",prefs.getSessions()));
        lastBreathTakenTxt.setText(prefs.getDate());

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBreathingAnimation();
            }
        });
    }

    private void startIntroAnimation(){
        ViewAnimator
                .animate(guideTxt)
                .duration(2000)
                .scale(0,1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Breathe");
                    }
                })
                .start();
    }

    private void startBreathingAnimation(){
        ViewAnimator
                .animate(imageView)
                .alpha(0,1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhale... Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f,1.5f,0.02f)
                .rotation(360)
                .repeatCount(7) // which will take approximately 1 minutes
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideTxt.setText("Good Job");
                        // set image in original state from X and Y
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        // saved all the data
                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() + 1);
                        prefs.setDate(System.currentTimeMillis());

                        // refresh activity
                        new CountDownTimer(2000,1000){

                            @Override
                            public void onTick(long millisUntilFinished) {
                                // can put code to show ticking...1,2,3..
                                // ticks 2 times because of 2000 after 1 sec
                                Log.d(TAG, "onTick: ");
                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        }.start();
                    }
                })
                .start();
    }




}
