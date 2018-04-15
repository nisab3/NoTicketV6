package com.noticket.noticketv6;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class TutorielPancarte extends AppCompatActivity {


    int numeroTuto = 1;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriel_pancarte);


        actionBar = getSupportActionBar();
        //changer le titre
        actionBar.setTitle("TUTORIEL 1/3");


        final ImageView tutoimage = findViewById(R.id.imageTutorielPanc);
        Picasso.with(getApplicationContext()).load(R.drawable.image_tuto_fav3).into(tutoimage);

        tutoimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numeroTuto == 1){
                    Picasso.with(getApplicationContext()).load(R.drawable.image_tuto_fav1).into(tutoimage);

                    numeroTuto = 2;
                    actionBar.setTitle("TUTORIEL 2/3");
                }
                else{
                    if(numeroTuto == 2){
                        Picasso.with(getApplicationContext()).load(R.drawable.image_tuto_fav2).into(tutoimage);

                        numeroTuto = 3;
                        actionBar.setTitle("TUTORIEL 3/3");
                    }
                    else{
                        finish();
                    }
                }
            }
        });


    }
}
