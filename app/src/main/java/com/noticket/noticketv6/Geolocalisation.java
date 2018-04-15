package com.noticket.noticketv6;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
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
    private FloatingActionButton boutonOk;
    private FloatingActionButton boutonPoubelle;
    private SeekBar seekActif;
    // variable pour prendre les infos du intent
    private int[] analyse;
    private boolean alarmeActive;
    private int delai;
    private double[] geoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalisation);

        final Intent intent = getIntent();
        analyse = intent.getIntArrayExtra("ANALYSE");
        alarmeActive = intent.getBooleanExtra("ALARMEACTIVE", false);
        delai = intent.getIntExtra("DELAI", 15);
        geoPosition = intent.getDoubleArrayExtra("POSITION");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        boutonOk = findViewById(R.id.boutonOk);
        boutonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sauvegarder les données dans le intent et retourner RESULT_OK
                intent.putExtra("ALARMEACTIVE", alarmeActive);
                intent.putExtra("DELAI", delai);
                intent.putExtra("POSITION", geoPosition);
                intent.putExtra("SUPPRIMER",false);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        seekActif = findViewById(R.id.seekBarAlarme);
        seekActif.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0){
                    alarmeActive = false;
                }
                else{
                    alarmeActive = true;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // mettre la bonne valeur sur le seekbar de l'alarme active ou non
        if (alarmeActive){
            seekActif.setProgress(1);
        }
        else{
            seekActif.setProgress(0);
        }


        boutonPoubelle = findViewById(R.id.boutonPoubelle);
        boutonPoubelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpPoubelle();
            }
        });

        alerte = findViewById(R.id.numPickerAlerte);
        alerte.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                delai = i;
            }
        });
        alerte.setWrapSelectorWheel(true);                                   //pour que la roue tourne sur elle meme
        alerte.setMinValue(0);                                               //valeur minimal
        alerte.setMaxValue(8);                                              //valeur maximal
        alerte.setValue(delai);                                                     // mets la valeur de la pancarte
        alerte.setDisplayedValues(getResources().getStringArray(R.array.alerte)); //changer tout les valeurs affichées

        minRestantes = findViewById(R.id.minRestantes);
//        minRestantes.setText();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Vérifie si l'utilisation de la localisation fine est autorisée
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "La permission est requise pour pouvoir vous localiser par rapport à votre automobile.", Toast.LENGTH_LONG).show();
            // Demande la permission pour utiliser la localisation fine
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        // Met un marqueur sur la position de la voiture
        LatLng maVoiture = new LatLng(geoPosition[0], geoPosition[1]);
        String nomMarqueur;
        // Si la géolocalisation n'a pas fonctionnée, la position par défaut est l'UdeM
        if (geoPosition[0]==45.5016946599 && geoPosition[1]==-73.6171625313) {
            nomMarqueur="UdeM";
        } else {
            nomMarqueur="Ma voiture";
        }
        // Place le marqueur sur la map
        mMap.addMarker(new MarkerOptions().position(maVoiture).title(nomMarqueur));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(maVoiture));
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
        final AlertDialog.Builder poubelleBuilder = new AlertDialog.Builder(Geolocalisation.this);
        final View poubelleView = getLayoutInflater().inflate(R.layout.poubelle, null);
        poubelleBuilder.setView(poubelleView);
        final AlertDialog dialogPoubelle = poubelleBuilder.create();
        dialogPoubelle.show();

        Button boutonOui = poubelleView.findViewById(R.id.boutonOui);
        boutonOui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("ALARMEACTIVE", false);
                intent.putExtra("DELAI",delai);
                intent.putExtra("POSITION", geoPosition);
                intent.putExtra("SUPPRIMER", true);
                setResult(RESULT_OK, intent);
                dialogPoubelle.dismiss();
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
