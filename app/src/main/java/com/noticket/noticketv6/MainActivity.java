package com.noticket.noticketv6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    // quand le bouton de nouvelle pancarte est clicker
    // on crée un intent et lance la nouvelle activitée
    public void ActionBoutPancarte(View view){
        Intent intent = new Intent(this, PancarteActivity.class);
        startActivity(intent);
    }
}
