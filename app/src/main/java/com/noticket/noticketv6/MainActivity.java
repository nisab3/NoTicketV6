package com.noticket.noticketv6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Poteau poteau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poteau = new Poteau();
        //créer les imageButton pour leur donner la fonction onclicklistener
        ImageButton bp1 = findViewById(R.id.boutPanc1);
        ImageButton bp2 = findViewById(R.id.boutPanc2);
        ImageButton bp3 = findViewById(R.id.boutPanc3);
        ImageButton bp4 = findViewById(R.id.boutPanc4);
        ImageButton bp5 = findViewById(R.id.boutPanc5);
        ImageButton bpa = findViewById(R.id.boutAjoutPanc);
        bp1.setOnClickListener(b);
        bp2.setOnClickListener(b);
        bp3.setOnClickListener(b);
        bp4.setOnClickListener(b);
        bp5.setOnClickListener(b);
        bpa.setOnClickListener(b);

    }
    // fonction qui ecoute le click des imageButton pour lui envoyer la bonne fonction
    private ImageButton.OnClickListener b = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.boutAjoutPanc){
                actionBoutNouveau(view);
            }
            if (view.getId() == R.id.boutPanc1){
                actionBoutPancarte(view, 1);
            }
            if (view.getId() == R.id.boutPanc2){
                actionBoutPancarte(view, 2);
            }
            if (view.getId() == R.id.boutPanc3){
                actionBoutPancarte(view, 3);
            }
            if (view.getId() == R.id.boutPanc4){
                actionBoutPancarte(view, 4);
            }
            if (view.getId() == R.id.boutPanc5){
                actionBoutPancarte(view, 5);
            }
        }
    };


    // quand le bouton de nouvelle pancarte est clicker
    // on crée un intent et lance la nouvelle activitée en
    // vérifiant quel est la prochaine pancarte pas active
    private void actionBoutNouveau(View view){
        int numero = 1;
        if (poteau.get_pancarte_active(1)){
            numero = 2;
            if (poteau.get_pancarte_active(2)){
                numero = 3;
                if (poteau.get_pancarte_active(3)){
                    numero = 4;
                    if (poteau.get_pancarte_active(4)){
                        numero= 5;
                        if (poteau.get_pancarte_active(5)){
                            Toast.makeText(getApplicationContext(),R.string.alerte_max_pancarte, Toast.LENGTH_LONG );
                        }
                    }
                }
            }
        }
        //appel la fonction de transfere a l'activity pancarte
        creeIntent(view, numero);
    }

    // fonction appelé par un bouton de pancarte déjà créé
    private void actionBoutPancarte(View view, int numero){
        creeIntent(view, numero);
    }

    // fonction pour cree le intent pour activity pancarte et
    // joindre le numero de la pancarte concerné
    // in: View view,
    //     int numero de la pancarte
    // out: rien
    private void creeIntent(View view, int numero){
        Pancarte pancarte = poteau.get_pancarte(numero);
        Intent intent = new Intent(this, PancarteActivity.class);
        intent.putExtra("NUMPANCARTE", numero);

        mettreInfo(intent, numero);     //mettre la pancarte dans le intent

        startActivity(intent);
    }

    // mets les infos d'un pancarte dans un intent et le retourne
    private Intent mettreInfo(Intent intent, int numero){
        // prend la pancarte
        Pancarte pancarte = poteau.get_pancarte(numero);
        // copie les infos
        intent.putExtra("HEURE1", pancarte.getHeure(1));
        intent.putExtra("HEURE2", pancarte.getHeure(2));
        intent.putExtra("HEURE3", pancarte.getHeure(3));
        intent.putExtra("JOUR1", pancarte.getJour(1));
        intent.putExtra("JOUR2", pancarte.getJour(2));
        intent.putExtra("JOUR3", pancarte.getJour(3));
        intent.putExtra("MOIS", pancarte.getMois());
        intent.putExtra("FLECHE", pancarte.getFleche());
        intent.putExtra("IMAGE", pancarte.getImage());

        return intent;
    }

    // prend le intent et remet les infos dans la pancarte
    private void prendreInfo(){
        Intent intent = getIntent();         //prend le intent
        int numero = intent.getIntExtra("NUMPANCARTE", 0);      //prend le numero de la pancarte
        Pancarte pancarte = poteau.get_pancarte(numero);          //prend la bonne pancarte du poteau
        // sort tout les infos et le mettre dans pancarte
        int[] heure1 = intent.getIntArrayExtra("HEURE1");
        pancarte.setHeure(heure1, 1);
        int[] heure2 = intent.getIntArrayExtra("HEURE2");
        pancarte.setHeure(heure2, 2);
        int[] heure3 = intent.getIntArrayExtra("HEURE3");
        pancarte.setHeure(heure3, 3);
    }
}
