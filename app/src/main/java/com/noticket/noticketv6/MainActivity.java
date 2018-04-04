package com.noticket.noticketv6;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Poteau poteau;
    //variable utilisé pour identifier PancarteActivité a son retour
    private static final int PANCARTE_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poteau = new Poteau();
        //créer les imageButton pour leur donner la fonction onclicklistener
        ImageView bp1 = findViewById(R.id.boutImPanc1);
        ImageView bp2 = findViewById(R.id.boutImPanc2);
        ImageView bp3 = findViewById(R.id.boutImPanc3);
        ImageView bp4 = findViewById(R.id.boutImPanc4);
        ImageView bp5 = findViewById(R.id.boutImPanc5);
        ImageButton bpa = findViewById(R.id.boutAjoutPanc);
        ImageButton poubelle1 = findViewById(R.id.boutDeletePanc1);
        ImageButton poubelle2 = findViewById(R.id.boutDeletePanc2);
        ImageButton poubelle3 = findViewById(R.id.boutDeletePanc3);
        ImageButton poubelle4 = findViewById(R.id.boutDeletePanc4);
        ImageButton poubelle5 = findViewById(R.id.boutDeletePanc5);


        bp1.setOnClickListener(b);
        bp2.setOnClickListener(b);
        bp3.setOnClickListener(b);
        bp4.setOnClickListener(b);
        bp5.setOnClickListener(b);
        bpa.setOnClickListener(b);
        poubelle1.setOnClickListener(b);
        poubelle2.setOnClickListener(b);
        poubelle3.setOnClickListener(b);
        poubelle4.setOnClickListener(b);
        poubelle5.setOnClickListener(b);

    }
    // fonction qui ecoute le click des imageButton pour lui envoyer la bonne fonction
    private ImageView.OnClickListener b = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //bouton ajoute pancarte
            if (view.getId() == R.id.boutAjoutPanc){
                actionBoutNouveau(view);
            }
            //bouton pancarte 1
            if (view.getId() == R.id.boutImPanc1){
                actionBoutPancarte(view, 1);
            }
            //bouton pancarte 2
            if (view.getId() == R.id.boutImPanc2){
                actionBoutPancarte(view, 2);
            }
            //bouton pancarte 3
            if (view.getId() == R.id.boutImPanc3){
                actionBoutPancarte(view, 3);
            }
            //bouton pancarte 4
            if (view.getId() == R.id.boutImPanc4){
                actionBoutPancarte(view, 4);
            }
            //bouton pancarte 5
            if (view.getId() == R.id.boutImPanc5){
                actionBoutPancarte(view, 5);
            }
            // bouton delete pancarte 1
            if (view.getId() == R.id.boutDeletePanc1){
                findViewById(R.id.boutPanc1).setVisibility(View.GONE);
                poteau.set_pancarte_active(1, false);
                findViewById(R.id.boutAjoutPanc).setVisibility(View.VISIBLE);
            }
            // bouton delete pancarte 2
            if (view.getId() == R.id.boutDeletePanc2){
                findViewById(R.id.boutPanc2).setVisibility(View.GONE);
                poteau.set_pancarte_active(2, false);
                findViewById(R.id.boutAjoutPanc).setVisibility(View.VISIBLE);
            }
            // bouton delete pancarte 3
            if (view.getId() == R.id.boutDeletePanc3){
                findViewById(R.id.boutPanc3).setVisibility(View.GONE);
                poteau.set_pancarte_active(3, false);
                findViewById(R.id.boutAjoutPanc).setVisibility(View.VISIBLE);
            }
            // bouton delete pancarte 4
            if (view.getId() == R.id.boutDeletePanc4){
                findViewById(R.id.boutPanc4).setVisibility(View.GONE);
                poteau.set_pancarte_active(4, false);
                findViewById(R.id.boutAjoutPanc).setVisibility(View.VISIBLE);
            }
            // bouton delete pancarte 5
            if (view.getId() == R.id.boutDeletePanc5){
                findViewById(R.id.boutPanc5).setVisibility(View.GONE);
                poteau.set_pancarte_active(5, false);
                findViewById(R.id.boutAjoutPanc).setVisibility(View.VISIBLE);
            }
        }
    };


    // quand le bouton de nouvelle pancarte est clicker
    // on crée un intent et lance la nouvelle activitée en
    // vérifiant quel est la prochaine pancarte pas active
    private void actionBoutNouveau(View view){
        int numero = 1;
        boolean trouve = false;
        while(numero < 6 && trouve == false){
            if (poteau.get_pancarte_active(numero)){
                numero++;
            }
            else{
                trouve = true;
            }
        }
        if (numero < 6) {
            //appel la fonction de transfere a l'activity pancarte
            creeIntent(view, numero);
        }
        else{
            Toast.makeText(getApplicationContext(),R.string.alerte_max_pancarte, Toast.LENGTH_LONG ).show();
        }
        // TEST TOMMY
//        Intent intent = new Intent(this, Geolocalisation.class);
//        startActivity(intent);
        
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
        Pancarte pancarteUtile = poteau.get_pancarte(numero);
        Intent intent = new Intent(this, PancarteActivity.class);
        intent.putExtra("NUMPANCARTE", numero);

        // copie les infos
        intent.putExtra("HEURE1", pancarteUtile.getHeure(1));
        intent.putExtra("HEURE2", pancarteUtile.getHeure(2));
        intent.putExtra("HEURE3", pancarteUtile.getHeure(3));
        intent.putExtra("JOUR1", pancarteUtile.getJour(1));
        intent.putExtra("JOUR2", pancarteUtile.getJour(2));
        intent.putExtra("JOUR3", pancarteUtile.getJour(3));
        intent.putExtra("MOIS", pancarteUtile.getMois());
        intent.putExtra("FLECHE", pancarteUtile.getFleche());
        intent.putExtra("IMAGE", pancarteUtile.getImage());
        boolean[] actif = {pancarteUtile.heureIsActive(1), pancarteUtile.heureIsActive(2), pancarteUtile.heureIsActive(3),
                            pancarteUtile.jourIsActive(1), pancarteUtile.jourIsActive(2), pancarteUtile.jourIsActive(3),
                            pancarteUtile.moisIsActive()};
        intent.putExtra("ACTIVE", actif);

        startActivityForResult(intent, PANCARTE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == PANCARTE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                prendreInfo(data);
            }
        }
    }

    // prend le intent et remet les infos dans la pancarte
    private void prendreInfo(Intent intent){
        int numero = intent.getIntExtra("NUMPANCARTE", 0);      //prend le numero de la pancarte
        Pancarte pancarteRetour = poteau.get_pancarte(numero);          //prend la bonne pancarte du poteau

        // sort tout les infos et le mettre dans pancarte
        int[] heure1 = intent.getIntArrayExtra("HEURE1");
        pancarteRetour.setHeure(heure1, 1);
        int[] heure2 = intent.getIntArrayExtra("HEURE2");
        pancarteRetour.setHeure(heure2, 2);
        int[] heure3 = intent.getIntArrayExtra("HEURE3");
        pancarteRetour.setHeure(heure3, 3);
        int[] jour1 = intent.getIntArrayExtra("JOUR1");
        pancarteRetour.setJour(jour1, 1);
        int[] jour2 = intent.getIntArrayExtra("JOUR2");
        pancarteRetour.setJour(jour1, 2);
        int[] jour3 = intent.getIntArrayExtra("JOUR3");
        pancarteRetour.setJour(jour1, 3);
        int[] mois = intent.getIntArrayExtra("MOIS");
        pancarteRetour.setmois(mois);
        int fleche = intent.getIntExtra("FLECHE", 0);
        pancarteRetour.setFleche(fleche);
        int image = intent.getIntExtra("IMAGE", 0);
        pancarteRetour.setImage(image);
        boolean[] actif = intent.getBooleanArrayExtra("ACTIVE");
        pancarteRetour.heureSetActive(actif[0], 1);
        pancarteRetour.heureSetActive(actif[1], 2);
        pancarteRetour.heureSetActive(actif[2], 3);
        pancarteRetour.jourSetActive(actif[3], 1);
        pancarteRetour.jourSetActive(actif[4], 2);
        pancarteRetour.jourSetActive(actif[5], 3);
        pancarteRetour.moisSetActive(actif[6]);

        // ici je dois appeler a réecrire le texte sur les pancartes
        String resutatText = formateText(pancarteRetour);
        TextView t = findViewById(R.id.textImPanc1 +(numero -1));
        t.setText(resutatText);

        // changer les images
        ImageView f = findViewById(R.id.fleche1 + (numero - 1));
        switch (fleche){
            case 0:
                f.setImageResource(R.drawable.fleche_pas);
                break;
            case 1:
                f.setImageResource(R.drawable.fleche_g);
                break;
            case 2:
                f.setImageResource(R.drawable.fleche_double);
                break;
            case 3:
                f.setImageResource(R.drawable.fleche_d);
                break;
        }
        ImageView im = findViewById(R.id.boutImPanc1 + (numero - 1));
        if (image == 0){
            im.setImageResource(R.drawable.no_parking_blank);
        }
        else{
            im.setImageResource(R.drawable.no_stop_blank);
        }

        // faire apparaitre la pancarte et l'activer et la mettre dans le poteau
        poteau.set_pancarte_active(numero, true);

        findViewById(R.id.boutPanc1 + (numero -1) ).setVisibility(View.VISIBLE);
        // cacher le bouton ajouterPancarte si il y a maintenant 5 pancarte
        cacheBoutAjoute();
    }

    public String formateText (Pancarte panc){
        String result = "";
        Resources res = getResources();
        for( int i =1; i < 4; i++){
            if (panc.heureIsActive(i)){
                int[] heure = panc.getHeure(i);
                String[] min = res.getStringArray(R.array.min);
                result = result + heure[0] + "h" + min[heure[1]] + " - " + heure[2] + "h" + min[heure[3]] + "\n";
            }
        }
        for( int i =1; i < 4; i++){
            if (panc.jourIsActive(i)){
                int[] jour = panc.getJour(i);
                String[] eta = res.getStringArray(R.array.eta);
                String[] s_jour = res.getStringArray(R.array.jours);
                result = result + s_jour[jour[0]] + " " + eta[jour[2]] + " " + s_jour[jour[1]] + "\n";
            }
        }
        if (panc.moisIsActive()){
            int[] mois = panc.getMois();
            String[] s_mois = res.getStringArray(R.array.mois);
            result = result + mois[0] + " " + s_mois[mois[1]-1] + " - " + mois[2] + " " + s_mois[mois[3]-1];
        }
        return result;
    }

    // fonction qui rend gone/visible le bouton ajouté selon e nombre de pancarte(max 5)
    private void cacheBoutAjoute(){
        int total = 0;
        for(int i = 1; i<6; i++){
            if (poteau.get_pancarte_active(i)){
                total++;
            }
        }
        if (total ==5){
            findViewById(R.id.boutAjoutPanc).setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.boutAjoutPanc).setVisibility(View.VISIBLE);
        }
    }

    // désactivation du back button car lorqu'on reviens il repart a zero
    // il faut donc utiliser le home button pour sortir
    @Override
    public void onBackPressed() {

    }
}
