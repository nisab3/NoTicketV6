package com.noticket.noticketv6;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TutorielDebut extends AppCompatActivity {

    //animation pour le tutoriel
    AnimationDrawable tutorielAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriel_debut);

        //enlever la bar en haut
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView tutoimage = findViewById(R.id.imageTutoriel);
        tutoimage.setBackgroundResource(R.drawable.anime_debut);
        tutorielAnime = (AnimationDrawable) tutoimage.getBackground();

        tutorielAnime.start();

        tutoimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
