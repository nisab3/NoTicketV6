package com.noticket.noticketv6;

import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TutorielPancarte extends AppCompatActivity {
    AnimationDrawable tutorielAnime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriel_pancarte);

        //enlever la bar en haut
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 8;
//
//        mBitmapInsurance = BitmapFactory.decodeFile(mCurrentPhotoPath,options);
        // créer l'animation de l'image et la fin si touché
        ImageView tutoimage = findViewById(R.id.imageTutorielPanc);
        tutoimage.setBackgroundResource(R.drawable.anime_pancarte);
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
