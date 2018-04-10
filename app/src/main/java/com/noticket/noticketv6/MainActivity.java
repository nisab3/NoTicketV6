package com.noticket.noticketv6;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * Created by Nicolas Sabourin 1068459
 *            Tommy Côté  1056362
 *            Charles-Frédéric Amringer
 */

public class MainActivity extends AppCompatActivity {

    public Poteau poteau;

    //analyse [heure, min, int(non(0), oui(1)), int(aujourdhui(0), demain(1))]
    int[] analyse = {0, 0, 0, 0};

    float[] geoPosition = {0, 0};

    int delai = 3;

    boolean alarmeActive = false;




    //variable utilisé pour identifier PancarteActivité a son retour
    private static final int PANCARTE_ACTIVITY_REQUEST_CODE = 0;
    private static final int GEOLOCALISATION_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //commencer le tutoriel
        Intent tutoIntent = new  Intent(this, TutorielDebut.class);
        startActivity(tutoIntent);


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

        // Création des floating buttons Analyse et effacer toutes les pancartes
        FloatingActionButton analyse = findViewById(R.id.boutonAnalyse);
        FloatingActionButton reset = findViewById(R.id.boutonReset);

        // bouton float alarme
        FloatingActionButton cloche = findViewById(R.id.boutonCloche);
        rechercheFichierNumero();
        if (alarmeActive){
            cloche.setVisibility(View.VISIBLE);
        }
        else{
            cloche.setVisibility(View.GONE);
        }

        cloche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Démarre l'activité Geolocalisation
                Intent intent = geoIntent();
                startActivityForResult(intent, GEOLOCALISATION_ACTIVITY_REQUEST_CODE);
            }
        });
        // Listener du bouton Analyse
        analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lance le Pop Up Question Oneway
                popUpQuestionOneway();
            }
        });

        // Listener du bouton Reset
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lance le Pop Up Reset
                popUpReset();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.avertissement:
                AlertDialog.Builder avertissementBuilder = new AlertDialog.Builder(MainActivity.this);
                final View avertissementView = getLayoutInflater().inflate(R.layout.avertissement, null);
                avertissementBuilder.setView(avertissementView);
                final AlertDialog dialogAvertissement = avertissementBuilder.create();
                dialogAvertissement.show();
                break;
            case R.id.tutoriel:
                AlertDialog.Builder tutorielBuilder = new AlertDialog.Builder(MainActivity.this);
                final View tutorielView = getLayoutInflater().inflate(R.layout.tutoriel, null);
                tutorielBuilder.setView(tutorielView);
                final AlertDialog dialogTutoriel = tutorielBuilder.create();

                // création de l'annimation de anime_setting
                AnimationDrawable tutorielAnimeSetting;
                ImageView tutoSetting = tutorielView.findViewById(R.id.imageTutorielSetting);
                tutoSetting.setBackgroundResource(R.drawable.anime_setting);
                tutorielAnimeSetting = (AnimationDrawable) tutoSetting.getBackground();

                tutorielAnimeSetting.start();
                //ferme si on touche l'image
                tutoSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       dialogTutoriel.dismiss();
                    }
                });
                dialogTutoriel.show();
                break;
        }
        return true;
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

    // fonction pour créer le Intent pour géolacalisation et y mettre les infos
    private Intent geoIntent(){
        Intent geoIntent = new Intent(this, Geolocalisation.class);
        geoIntent.putExtra("ANALYSE", analyse);
        geoIntent.putExtra("POSITION", geoPosition);
        geoIntent.putExtra("DELAI", delai);
        geoIntent.putExtra("ALARMEACTIVE", alarmeActive);

        return geoIntent;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == PANCARTE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                prendreInfo(data);
            }
        }
        if ( requestCode == GEOLOCALISATION_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){

               geoPosition = data.getFloatArrayExtra("POSITION");
               delai = data.getIntExtra("DELAI", 15);
               alarmeActive = data.getBooleanExtra("ALARMEACTIVE", false);
               boolean supprimer = data.getBooleanExtra("SUPPRIMER", false);

               FloatingActionButton cloche = findViewById(R.id.boutonCloche);
                if (supprimer){
                    cloche.setVisibility(View.GONE);
                    cancelNotif();
                    delai = 3;
                    geoPosition[0] = 0;
                    geoPosition[1] = 0;
                    alarmeActive = false;
                }
                else{
                    if (alarmeActive){
                        cancelNotif();
                        mettreNotif(analyse, delai);
                    }
                    else{
                        cancelNotif();
                    }
                    cloche.setVisibility(View.VISIBLE);
                }
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
                f.setImageResource(R.drawable.fleche_vide);
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

    // fonction pour faire le text complet de la pancarte
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

    /*
    /désactivation du back button car lorqu'on reviens il repart a zero
    /il faut donc utiliser le home button pour sortir
    */
    @Override
    public void onBackPressed() {

    }

    /*
     *partie pour la Notification
     *
     *
     *-------------><-------------
     *
    */

    // annuler tout les nofications qui sont en actives
    public void cancelNotif(){
        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }
    // la fonction qu'on call pour mettre la notification
    // créer le intent pour le donner a l'alarme manager et calculer le temps délais
    // in: int[] = [min, heure, (oui/non), jour]
    //     int = delai
    public void mettreNotif(int[] temps, int delai){

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 567);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, faireNotif(temps, delai));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //TODO calculer le timer a partir de int donné
        // calcul du timer
        long timer = SystemClock.elapsedRealtime() + 15000;
        AlarmManager alarme = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarme.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timer, pendingIntent);
    }

    // fonction qui cree la notification
    // c'est ici qu'on lui passe les textes et alarme
    // in: int[] = [min, heure, (oui/non), jour]
    //     int = delai
    // out: une Notification
    private Notification faireNotif(int[] temps, int delai){
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = {0, 100, 1000, 100, 1000, 100, 1000, 100, 1000};

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);

        builder.setContentTitle("Alarme NoTicket");
        builder.setContentText("Il faut déplacer votre voiture avant " + delai*5 + " minutes");
        builder.setSound(uri);
        builder.setVibrate(pattern);

        return builder.build();
    }


    // Fait apparaitre un Pop Up avec le résultat de l'analyse
    private void popUpAnalyse() {
        AlertDialog.Builder analyseBuilder = new AlertDialog.Builder(MainActivity.this);
        final View analyseView = getLayoutInflater().inflate(R.layout.analyse, null);

        Button boutonOk = (Button) analyseView.findViewById(R.id.boutonOk);
        Button boutonAlarme = (Button) analyseView.findViewById(R.id.boutonFavAnuler);
        Button boutonRetour = (Button) analyseView.findViewById(R.id.boutonFavSup);

        TextView reponse = (TextView) analyseView.findViewById(R.id.titreAvertissement);
        TextView momentDisponible = (TextView) analyseView.findViewById(R.id.momentDisponible);
        TextView tempsDisponible = (TextView) analyseView.findViewById(R.id.tempsRestant);

        // analyse return int[heure, min, peutMaintenant=>(0=non et 1=Oui), jour=>(0=aujourdhui et 1=demain)]
        analyse = poteau.analyse();

        // Formattage
        String heure = "" + analyse[0];
        String minute = "" + analyse[1];
        // Pour faire que les heures comme 12h00 ne donne pas 12h0
        if (minute.equals("0")) minute="00";
        int[] tempsRestant = diffTemps(analyse[0], analyse[1], analyse[3]);
        String textHeure = " heure";
        String textMinute = " minute";
        if (tempsRestant[0]>1) textHeure=" heures";
        if (tempsRestant[1]>1) textMinute=" minutes";

        // Si on ne peut pas se stationner
        if (analyse[2]==0) {
            reponse.setText("Non");
            reponse.setTextColor(Color.RED);
            if (tempsRestant[0]<24) {
                momentDisponible.setText("Le stationnement ne sera disponible qu'à "+heure+"h"+minute);
                tempsDisponible.setText("Il vous reste à attendre "+tempsRestant[0]+textHeure+" et "+tempsRestant[1]+textMinute);
            } else {
                momentDisponible.setText("Le stationnement ne sera pas disponible pendant les 24 prochaines heures");
                tempsDisponible.setText("Essayez de trouver une autre place de stationnement");
            }
        // Sinon
        } else {
            reponse.setText("Oui");
            reponse.setTextColor(Color.GREEN);
            if (tempsRestant[0]<24) {
                momentDisponible.setText("Le stationnement est disponible jusqu'à "+heure+"h"+minute);
                tempsDisponible.setText("Vous devrez déplacez votre véhicule dans "+tempsRestant[0]+textHeure+" et "+tempsRestant[1]+textMinute);
            } else {
                momentDisponible.setText("Le stationnement est disponible tout au long des 24 prochaines heures");
                tempsDisponible.setText("N'oubliez pas de revérifier sa disponibilité d'ici là");
            }

        }

        analyseBuilder.setView(analyseView);
        final AlertDialog dialogAnalyse = analyseBuilder.create();
        dialogAnalyse.show();
//                                dialogAnalyse.getWindow().setLayout(1000, 1000);

        boutonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAnalyse.dismiss();
            }
        });

        boutonAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAnalyse.dismiss();
                // Démarre l'activité Geolocalisation
                Intent intent = geoIntent();
                startActivityForResult(intent, GEOLOCALISATION_ACTIVITY_REQUEST_CODE);
            }
        });

        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poteau.get_oneWay()==true) {
                    dialogAnalyse.dismiss();
                    popUpOneway();
                } else {
                    dialogAnalyse.dismiss();
                    popUpTwoway();
                }
            }
        });
    }

    // Calcule la différence entre un temps donné et le temps actuel
    private int[] diffTemps(int h, int m, int jour) {
        Date dateActuelle = new Date();
        String maintenant = dateActuelle.toString();
        int heure = Integer.parseInt(maintenant.substring(11, 13));
        int minute = Integer.parseInt(maintenant.substring(14, 16));

        int[] resultat = {h-heure, m-minute};
        if (resultat[1]<0) {
            resultat[0]-=1;
            resultat[1]+=60;
        }
        if (resultat[1]>=60) {
            resultat[0]+=1;
            resultat[1]-=60;
        }
        if (jour==1) resultat[0]+=24;

        return resultat;
    }

    // Fait apparaitre un Pop Up avec le choix de position dans le Oneway
    private void popUpOneway() {
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
                popUpAnalyse();
            }
        });

        position2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poteau.set_position(2);
                dialogPosition.dismiss();
                popUpAnalyse();
            }
        });

        position3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poteau.set_position(3);
                dialogPosition.dismiss();
                popUpAnalyse();
            }
        });

        position4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poteau.set_position(4);
                dialogPosition.dismiss();
                popUpAnalyse();
            }
        });
    }

    // Fait apparaitre un Pop Up avec le choix de position dans le Twoway
    private void popUpTwoway() {
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
                popUpAnalyse();
            }
        });

        position2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poteau.set_position(2);
                dialogPosition.dismiss();
                popUpAnalyse();
            }
        });
    }

    // Fait apparaitre un Pop Up avec la question Oneway V/F?
    private void popUpQuestionOneway() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.oneway, null);

        Button boutonOui = (Button) mView.findViewById(R.id.boutonOui);
        Button boutonNon = (Button) mView.findViewById(R.id.boutonNon);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setLayout(800, 500);

        boutonOui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poteau.set_oneWay(true);
                dialog.dismiss();
                popUpOneway();
            }
        });

        boutonNon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poteau.set_oneWay(false);
                dialog.dismiss();
                popUpTwoway();
            }
        });
    }

    // Fait apparaitre un Pop Up avec la question Reset le poteau?
    private void popUpReset() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.reset, null);

        Button boutonOui = (Button) mView.findViewById(R.id.boutonOui);
        Button boutonNon = (Button) mView.findViewById(R.id.boutonNon);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setLayout(800, 550);

        boutonOui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poteau = new Poteau();
                findViewById(R.id.boutPanc1).setVisibility(View.GONE);
                findViewById(R.id.boutPanc2).setVisibility(View.GONE);
                findViewById(R.id.boutPanc3).setVisibility(View.GONE);
                findViewById(R.id.boutPanc4).setVisibility(View.GONE);
                findViewById(R.id.boutPanc5).setVisibility(View.GONE);
                findViewById(R.id.boutAjoutPanc).setVisibility(View.VISIBLE);
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

    // fonction pour chercher le fichier de base numero pour sortir les info sauvegardé
    // ou le créer
    private void rechercheFichierNumero(){

        // donne la liste des files
        String [] liste = fileList();
        // je vais choisir le prochain nom disponible

        String name = "com.noticket.numero" ;  // jai enregistrer le numero de la derniere files sauvgarde ici
        int trouver = 0;
        // recherche et prend le fichier numero
        for ( String n: liste){
            if (n.equals(name)){
                // reprend tout les info dans le fichier
                try {
                    FileInputStream fis = openFileInput(name);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    trouver = (int) is.readObject(); // juste bon pour favorie. donc je le mets nulpart
                    analyse = (int[]) is.readObject();
                    geoPosition = (float[]) is.readObject();
                    delai = (int) is.readObject();
                    is.close();
                    fis.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        // si pas trouver fichier numero alors on le creer et on y mets 1
        if (trouver == 0){
            try {

                File file = new File(getFilesDir(),name );
                FileOutputStream fos = openFileOutput(name, MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(trouver); // pour analyse
                os.writeObject(analyse);
                os.writeObject(geoPosition);
                os.writeObject(delai);
                os.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
