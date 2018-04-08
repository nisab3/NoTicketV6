package com.noticket.noticketv6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * Created by Nicolas Sabourin 1068459
 *            Tommy Côté  1056362
 *            Charles-Frédéric Amringer
 */
public class Geolocalisation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int REQUEST_FINE_LOCATION;
    private NumberPicker alerte;
    private TextView minRestantes;
    private int[] analyse;
    private FloatingActionButton boutonOk;
    private FloatingActionButton boutonPoubelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalisation);

        Intent intent = getIntent();
        analyse = intent.getIntArrayExtra("INFO_ANALYSE");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        boutonOk = findViewById(R.id.boutonOk);
        boutonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO décider comment revenir au main
                // Démarre l'activité Geolocalisation
//                Intent intent = new Intent(Geolocalisation.this, MainActivity.class);
//                intent.putExtra("INFO_ANALYSE", analyse);
//                startActivityForResult(intent, GEOLOCALISATION_ACTIVITY_REQUEST_CODE);
//                startActivity(intent);
                finish();
            }
        });

        boutonPoubelle = findViewById(R.id.boutonPoubelle);
        boutonPoubelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpPoubelle();
            }
        });

        alerte = findViewById(R.id.numPickerAlerte);
//        alerte.setOnValueChangedListener(n);
        alerte.setWrapSelectorWheel(true);                                   //pour que la roue tourne sur elle meme
        alerte.setMinValue(0);                                               //valeur minimal
        alerte.setMaxValue(8);                                              //valeur maximal//mets la valeur de la pancarte
        alerte.setDisplayedValues(getResources().getStringArray(R.array.alerte)); //changer tout les valeurs affichées
        alerte.setValue(3);

        minRestantes = findViewById(R.id.minRestantes);
//        minRestantes.setText();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Vérifie si l'utilisation de la localisation fine est autorisée
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "La permission est requise pour pouvoir vous localiser par rapport à votre automobile.", Toast.LENGTH_SHORT).show();
            // Demande la permission pour utiliser la localisation fine
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "La permission a été accordée.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La permission a été refusée.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // Fait apparaitre un Pop Up poubelle
    private void popUpPoubelle() {
        AlertDialog.Builder poubelleBuilder = new AlertDialog.Builder(Geolocalisation.this);
        final View poubelleView = getLayoutInflater().inflate(R.layout.poubelle, null);
        poubelleBuilder.setView(poubelleView);
        final AlertDialog dialogPoubelle = poubelleBuilder.create();
        dialogPoubelle.show();

        Button boutonOui = (Button) poubelleView.findViewById(R.id.boutonOui);
        boutonOui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO doit effacer l'alarme et la géolocalisation
                finish();
            }
        });

        Button boutonNon = (Button) poubelleView.findViewById(R.id.boutonNon);
        boutonNon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPoubelle.dismiss();
            }
        });
    }
}
