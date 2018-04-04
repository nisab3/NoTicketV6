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

        // Création des floating buttons Analyse et effacer toutes les pancartes
        FloatingActionButton analyse = (FloatingActionButton) findViewById(R.id.boutonAnalyse);
        FloatingActionButton reset = (FloatingActionButton) findViewById(R.id.boutonReset);

        // Listener du bouton Analyse
        analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.oneway, null);

                Button boutonOui = (Button) mView.findViewById(R.id.boutonOui);
                Button boutonNon = (Button) mView.findViewById(R.id.boutonNon);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                boutonOui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        poteau.set_oneWay(true);
                        dialog.dismiss();

                        AlertDialog.Builder positionBuilder = new AlertDialog.Builder(MainActivity.this);
                        final View positionView = getLayoutInflater().inflate(R.layout.position_oneway, null);

                        Button position1 = (Button) positionView.findViewById(R.id.position1);
                        Button position2 = (Button) positionView.findViewById(R.id.position2);
                        Button position3 = (Button) positionView.findViewById(R.id.position3);
                        Button position4 = (Button) positionView.findViewById(R.id.position4);


                        positionBuilder.setView(positionView);
                        final AlertDialog dialogPosition = positionBuilder.create();
                        dialogPosition.show();

                        position1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                poteau.set_position(1);
                                dialogPosition.dismiss();
                                // Démarre l'activité Geolocalisation
                                Intent intent = new Intent(MainActivity.this, Geolocalisation.class);
                                startActivity(intent);
                            }
                        });

                        position2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                poteau.set_position(2);
                                dialogPosition.dismiss();
                                // Démarre l'activité Geolocalisation
                                Intent intent = new Intent(MainActivity.this, Geolocalisation.class);
                                startActivity(intent);
                            }
                        });

                        position3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                poteau.set_position(3);
                                dialogPosition.dismiss();
                                // Démarre l'activité Geolocalisation
                                Intent intent = new Intent(MainActivity.this, Geolocalisation.class);
                                startActivity(intent);
                            }
                        });

                        position4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                poteau.set_position(4);
                                dialogPosition.dismiss();
                                // Démarre l'activité Geolocalisation
                                Intent intent = new Intent(MainActivity.this, Geolocalisation.class);
                                startActivity(intent);
                            }
                        });


                    }
                });

                boutonNon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        poteau.set_oneWay(false);
                        dialog.dismiss();

                        AlertDialog.Builder positionBuilder = new AlertDialog.Builder(MainActivity.this);
                        final View positionView = getLayoutInflater().inflate(R.layout.position_twoway, null);

                        Button position1 = (Button) positionView.findViewById(R.id.position1);
                        Button position2 = (Button) positionView.findViewById(R.id.position2);

                        positionBuilder.setView(positionView);
                        final AlertDialog dialogPosition = positionBuilder.create();
                        dialogPosition.show();

                        position1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                poteau.set_position(1);
                                dialogPosition.dismiss();
                                // Démarre l'activité Geolocalisation
                                Intent intent = new Intent(MainActivity.this, Geolocalisation.class);
                                startActivity(intent);
                            }
                        });

                        position2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                poteau.set_position(2);
                                dialogPosition.dismiss();
                                // Démarre l'activité Geolocalisation
                                Intent intent = new Intent(MainActivity.this, Geolocalisation.class);
                                startActivity(intent);
                            }
                        });
                    }
                });


            }
        });

        // Listener du bouton Reset
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.reset, null);

                Button boutonOui = (Button) mView.findViewById(R.id.boutonOui);
                Button boutonNon = (Button) mView.findViewById(R.id.boutonNon);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                boutonOui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Reseter le poteau
                        dialog.dismiss();
                    }
                });

                boutonNon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    // fonction qui ecoute le click des imageButton pour lui envoyer la bonne fonction
    private ImageView.OnClickListener b = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.boutAjoutPanc){
                actionBoutNouveau(view);
            }
            if (view.getId() == R.id.boutImPanc1){
                actionBoutPancarte(view, 1);
            }
            if (view.getId() == R.id.boutImPanc2){
                actionBoutPancarte(view, 2);
            }
            if (view.getId() == R.id.boutImPanc3){
                actionBoutPancarte(view, 3);
            }
            if (view.getId() == R.id.boutImPanc4){
                actionBoutPancarte(view, 4);
            }
            if (view.getId() == R.id.boutImPanc5){
                actionBoutPancarte(view, 5);
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
            Toast.makeText(getApplicationContext(),R.string.alerte_max_pancarte, Toast.LENGTH_LONG );
        }



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

        startActivityForResult(intent, PANCARTE_ACTIVITY_REQUEST_CODE);
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
        boolean[] actif = {pancarte.heureIsActive(1), pancarte.heureIsActive(2), pancarte.heureIsActive(3),
                            pancarte.jourIsActive(1), pancarte.jourIsActive(2), pancarte.jourIsActive(3),
                            pancarte.moisIsActive()};
        intent.putExtra("ACTIVE", actif);

        return intent;
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

        // faire apparaitre la pancarte et l'activer et la mettre dans le poteau
        poteau.set_pancarte_active(numero, true);
        poteau.set_pancarte(pancarteRetour, numero);
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
