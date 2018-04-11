package com.noticket.noticketv6;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Nicolas Sabourin 1068459
 *            Tommy Côté  1056362
 *            Charles-Frédéric Amringer
 */
public class PancarteActivity extends AppCompatActivity {
    private static final int FAVORIE_ACTIVITY_REQUEST_CODE = 1;

    private int numero = 1; //no de pancarte 1 par default
    private int noFleche = 1; // no de l'image de la fleche
    private int noImageP = 0; // no de l'image du  P/stop

    //bouton de chaque ligne
    private Button bh1;
    private Button bh2;
    private Button bh3;
    private Button bj1;
    private Button bj2;
    private Button bj3;
    private Button bm1;
    //bouton ajouter (heure, jour, mois)
    private Button bah;
    private Button baj;
    private Button bam;
    //boutons effacer
    private ImageButton supprimerH1;
    private ImageButton supprimerH2;
    private ImageButton supprimerH3;
    private ImageButton supprimerJ1;
    private ImageButton supprimerJ2;
    private ImageButton supprimerJ3;
    private ImageButton supprimerM1;

    // bouton pour changer les images fleche
    private ImageButton fleche_g;
    private ImageButton fleche_d;

    // bouton pour changer les images pancarte
    private ImageButton panc_g;
    private ImageButton panc_d;

    // bouton cancel / ok
    private FloatingActionButton ok;
    private FloatingActionButton can;

    // bouton favorie
    private FloatingActionButton allerFav;
    private FloatingActionButton ajoutFav;

    // imageSwitcher fleche
    private ImageSwitcher imfleche;
    private ImageSwitcher imPanc;

    // l'objet pancarte traité
    public Pancarte pancarte;

    // numero de favorie ou on est rendu
    int trouver;
    // bool pour savoir si on commence avec le Tutoriel
    boolean tutorielPanc;
    // bool si la pancarte est un favorie
    boolean etoile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pancarte);


        //changer le titre de l'activité
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Création d'une pancarte");



        pancarte = new Pancarte();

        //listener pour les boutons
        bh1 = findViewById(R.id.boutHeure1);
        bh2 = findViewById(R.id.boutHeure2);
        bh3 = findViewById(R.id.boutHeure3);
        bj1 = findViewById(R.id.boutJour1);
        bj2 = findViewById(R.id.boutJour2);
        bj3 = findViewById(R.id.boutJour3);
        bm1 = findViewById(R.id.boutMois1);
        bah = findViewById(R.id.boutAjoutHeure);
        baj = findViewById(R.id.boutAjoutJour);
        bam = findViewById(R.id.boutAjoutMois);
        supprimerH1 = findViewById(R.id.boutDeleteH1);
        supprimerH2 = findViewById(R.id.boutDeleteH2);
        supprimerH3 = findViewById(R.id.boutDeleteH3);
        supprimerJ1 = findViewById(R.id.boutDeleteJ1);
        supprimerJ2 = findViewById(R.id.boutDeleteJ2);
        supprimerJ3 = findViewById(R.id.boutDeleteJ3);
        supprimerM1 = findViewById(R.id.boutDeleteM1);
        fleche_g = findViewById(R.id.boutFlecheG);
        fleche_d = findViewById(R.id.boutFlecheD);
        panc_g = findViewById(R.id.boutPancG);
        panc_d = findViewById(R.id.boutPancD);
        ok = findViewById(R.id.boutPancOk);
        can = findViewById(R.id.boutCancelPanc);
        allerFav = findViewById(R.id.boutFavorie);
        ajoutFav = findViewById(R.id.boutAjoutFavorie);

        imfleche = findViewById(R.id.switcherFleche);
        imPanc = findViewById(R.id.imageParkingSwitcher);

        bh1.setOnClickListener(b);
        bh2.setOnClickListener(b);
        bh3.setOnClickListener(b);
        bj1.setOnClickListener(b);
        bj2.setOnClickListener(b);
        bj3.setOnClickListener(b);
        bm1.setOnClickListener(b);
        bah.setOnClickListener(b);
        baj.setOnClickListener(b);
        bam.setOnClickListener(b);
        ok.setOnClickListener(b);
        can.setOnClickListener(b);
        supprimerH1.setOnClickListener(b);
        supprimerH2.setOnClickListener(b);
        supprimerH3.setOnClickListener(b);
        supprimerJ1.setOnClickListener(b);
        supprimerJ2.setOnClickListener(b);
        supprimerJ3.setOnClickListener(b);
        supprimerM1.setOnClickListener(b);
        fleche_d.setOnClickListener(b);
        fleche_g.setOnClickListener(b);
        panc_d.setOnClickListener(b);
        panc_g.setOnClickListener(b);
        allerFav.setOnClickListener(b);
        ajoutFav.setOnClickListener(b);

        //va chercher tout les infos recu de l'activité main
        Intent intent = getIntent();
        setInfoStart(intent);

        // commencer le tutoriel
        if(tutorielPanc) {
            Intent tutoIntent = new Intent(this, TutorielPancarte.class);
            startActivity(tutoIntent);
        }
        //pour ne pas qu'il passe une deuxième fois
        tutorielPanc = false;

    }


    // que faire si le bouton back est clicker
    // je dois la overrider car sinon ca repars une nouvelle MainActivity et perds tout les pancarte déja fait
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == FAVORIE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                ajoutFav.setImageResource(R.drawable.icon_etoile_jaune);
                etoile = true;
                setInfoStart(data);
            }
        }
    }

    /* écoute si on click sur un bouton de l'activité ou du fragment et décide de ce qu'il doit faire */
    private Button.OnClickListener b = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.boutAjoutFavorie){
                if (!etoile) {
                    sauvegardeFavorie();
                    ajoutFav.setImageResource(R.drawable.icon_etoile_jaune);
                    etoile = true;
                }
            }

            if (view.getId() == R.id.boutFavorie){
                Intent intent = new Intent(getApplicationContext(), FavoriesActivity.class);
                startActivityForResult(intent, FAVORIE_ACTIVITY_REQUEST_CODE);
            }
            //floating button ok de l'activité pancarte
            if (view.getId() == R.id.boutPancOk){
                setInfoStop();
            }
            // bouton droit pour changer l'image pancarte
            if (view.getId() == R.id.boutPancD){
                if (noImageP == 0){
                    noImageP = 1;
                }
                else{
                    noImageP = 0;
                }
                setImageParking(noImageP);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;

            }
            // bouton gauche pour changer l'image pancarte
            if (view.getId() == R.id.boutPancG){
                if (noImageP == 1){
                    noImageP = 0;
                }
                else{
                    noImageP = 1;
                }
                setImageParking(noImageP);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            // bouton droite pour changer la fleche
            if (view.getId() == R.id.boutFlecheD){
                if (noFleche == 3){
                   noFleche = 0;
                }
                else{
                    noFleche++;
                }
                setFleche(noFleche);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            // bouton gauche pour changer la fleche
            if (view.getId() == R.id.boutFlecheG){
                if (noFleche == 0){
                    noFleche = 3;
                }
                else{
                    noFleche--;
                }
                setFleche(noFleche);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            // bouton ajouter ligne heure
            // trouve une ligne non-active
            if (view.getId() == R.id.boutAjoutHeure){
                if (!pancarte.heureIsActive(1)){
                    int[] info = pancarte.getHeure(1);
                    clickModifieLigne(view, 1, 1, info);
                }
                else{
                    if (!pancarte.heureIsActive(2)){
                        int[] info = pancarte.getHeure(2);
                        clickModifieLigne(view, 1, 2, info);
                    }
                    else{
                        if(!pancarte.heureIsActive(3)){
                            int[] info = pancarte.getHeure(3);
                            clickModifieLigne(view, 1, 3, info);
                        }
                    }
                }
            }
            // bouton ajouter ligne jour
            // trouve une ligne non-active
            if (view.getId() == R.id.boutAjoutJour){
                if(!pancarte.jourIsActive(1)){
                    int[] info = pancarte.getJour(1);
                    clickModifieLigne(view, 2, 1, info);
                }
                else{
                    if (!pancarte.jourIsActive(2)){
                        int[] info = pancarte.getJour(2);
                        clickModifieLigne(view, 2, 2, info);
                    }
                    else{
                        if (!pancarte.jourIsActive(3)) {
                            int[] info = pancarte.getJour(3);
                            clickModifieLigne(view, 2, 3, info);
                        }
                    }
                }
            }
            // bouton ajouter mois
            if (view.getId() == R.id.boutAjoutMois){
                if (!pancarte.moisIsActive()){
                    int[] info = pancarte.getMois();
                    clickModifieLigne(view, 3, 1, info);
                }
            }
            // 1er ligne d'heure
            if (view.getId() == R.id.boutHeure1){
                int[] info = pancarte.getHeure(1);
                clickModifieLigne(view, 1, 1, info);
            }
            // 2e ligne d'heure
            if (view.getId() == R.id.boutHeure2){
                int[] info = pancarte.getHeure(2);
                clickModifieLigne(view, 1, 2, info);
            }
            // 3e ligne d'heure
            if (view.getId() == R.id.boutHeure3){
                int[] info = pancarte.getHeure(3);
                clickModifieLigne(view, 1, 3, info);
            }

            // 1er ligne jour
            if (view.getId() == R.id.boutJour1){
                int[] info = pancarte.getJour(1);
                clickModifieLigne(view, 2, 1, info);
            }
            // 2e ligne jour
            if (view.getId() == R.id.boutJour2){
                int[] info = pancarte.getJour(2);
                clickModifieLigne(view, 2, 2, info);
            }
            // 3e ligne jour
            if (view.getId() == R.id.boutJour3){
                int[] info = pancarte.getJour(3);
                clickModifieLigne(view, 2, 3, info);
            }

            // 1er ligne mois
            if (view.getId() == R.id.boutMois1){
                int[] info = pancarte.getMois();
                clickModifieLigne(view, 3, 1, info);
            }

            // bouton cancel de tous fragments
            if (view.getId() == R.id.boutFragCancel){
                clickFermerFrag(view);
            }

            // bouton ok de tous fragments
            if (view.getId() == R.id.boutFragOk){
                clickFermerFrag(view);
            }
            // bouton cancel la pancarte reviens vers Main activity sans sauvegarder
            if (view.getId() == R.id.boutCancelPanc){
                cancel();
            }
            // delete ligne heure 1
            if (view.getId() == R.id.boutDeleteH1){
                findViewById(R.id.layoutHeure1).setVisibility(View.GONE);
                findViewById(R.id.boutAjoutHeure).setVisibility(View.VISIBLE);
                pancarte.heureSetActive(false, 1);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            // delete ligne heure 2
            if (view.getId() == R.id.boutDeleteH2){
                findViewById(R.id.layoutHeure2).setVisibility(View.GONE);
                pancarte.heureSetActive(false, 2);
                findViewById(R.id.boutAjoutHeure).setVisibility(View.VISIBLE);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            //delete ligne heure 3
            if (view.getId() == R.id.boutDeleteH3){
                findViewById(R.id.layoutHeure3).setVisibility(View.GONE);
                pancarte.heureSetActive(false, 3);
                findViewById(R.id.boutAjoutHeure).setVisibility(View.VISIBLE);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            //delete ligne jour1
            if (view.getId() == R.id.boutDeleteJ1){
                findViewById(R.id.layoutJour1).setVisibility(View.GONE);
                pancarte.jourSetActive(false, 1);
                findViewById(R.id.boutAjoutJour).setVisibility(View.VISIBLE);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            //delete ligne jour 2
            if (view.getId() == R.id.boutDeleteJ2){
                findViewById(R.id.layoutJour2).setVisibility(View.GONE);
                pancarte.jourSetActive(false, 2);
                findViewById(R.id.boutAjoutJour).setVisibility(View.VISIBLE);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            //delete ligne jour 3
            if (view.getId() == R.id.boutDeleteJ3){
                findViewById(R.id.layoutJour3).setVisibility(View.GONE);
                pancarte.jourSetActive(false, 3);
                findViewById(R.id.boutAjoutJour).setVisibility(View.VISIBLE);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
            //delete ligne mois 1
            if (view.getId() == R.id.boutDeleteM1){
                findViewById(R.id.layoutMois1).setVisibility(View.GONE);
                pancarte.moisSetActive(false);
                findViewById(R.id.boutAjoutMois).setVisibility(View.VISIBLE);
                ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                etoile = false;
            }
        }
    };

    /*
    mettre tout les infos nécessaires dans un bundle et lancer le fragment
    in : View view = view
    string type = heure, jour, mois (type du bouton)
    String numero = 1, 2, 3 (ligne du bouton)
    */
    private void clickModifieLigne(View view, int type, int numero, int[] info){


        Bundle paquet = new Bundle();
        paquet.putInt("type", type);
        paquet.putInt("numero", numero);
        paquet.putIntArray("info", info);
        Fragment fragChoix = new ChoixFragment();
        fragChoix.setArguments(paquet);

        //desactive tout les boutons dans pancarte activity
        desactivationBouton();

        //creer et lancer le fragment
        lanceFragment(view, fragChoix);

    }

    /* lancement du frangement */
    private void lanceFragment(View view, Fragment fragChoix ){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layoutPourChoixFrag, fragChoix);
        fragmentTransaction.commit();
    }


    // fonction qui verifie l'entré quand on appuis sur ok pour s'assurer que l'entré est valide
    // in: le type de fragement a verifier
    // out: oui ou non
    public boolean verifieValide(int type, int[] info){
        boolean reponse = true;
        // si le fragemnt est heure = les 2 heures ne peuvent etre pareille
        if (type == 1) {
            int heure1 = info[0];
            int heure2 = info[2];
            if (heure1 == heure2) {
                reponse = false;
            }
        }
        // si le fragemnt est jour = les 2 jours ne peuvent etre pareille
        if (type == 2) {
            int jour1 = info[0];
            int jour2 = info[1];
            if (jour1 == jour2) {
                reponse = false;
            }
        }
        // si le fragemnt est mois = les 2 mois ne peuvent etre pareille
        if (type == 3) {
            int mois1 = info[1];
            int mois2 = info[3];
            if (mois1 == mois2) {
                reponse = false;
            }
        }
        return reponse;
    }

    // création du popUp validation
    // in: le type du fragment
    private void popValidation(int type){
        final AlertDialog.Builder valBuilder = new AlertDialog.Builder(PancarteActivity.this);
        final View valView = getLayoutInflater().inflate(R.layout.pop_validation_donnee_panc, null);
        valBuilder.setView(valView);
        final AlertDialog dialogVal = valBuilder.create();

        TextView textVal = valView.findViewById(R.id.textValidation);
        if (type == 1){
            textVal.setText(R.string.text_validation_heure);
        }
        else{
            if (type == 2){
                textVal.setText(R.string.text_validation_jour);
            }
            else{
                textVal.setText(R.string.text_validation_mois);
            }
        }

        dialogVal.show();

        Button bok = valView.findViewById(R.id.boutOkValidation);
        bok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogVal.dismiss();
            }
        });
    }

    // faire le néssecaire pour fermer le fragment
    public void clickFermerFrag(View view){


        //prépaprer le fragment manager le fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment frag = fragmentManager.findFragmentById(R.id.layoutPourChoixFrag);

        //va chercher les infos stocker dans le fragment avant de le détruire
        //et changer le text du bouton concerné
        //et mettre les changement dans pancarte
        if (view.getId() == R.id.boutFragOk) {
            // sors les info du paquet de fragment
            Bundle paquet = frag.getArguments();
            Resources res = getResources();
            int[] nouvelinfo = paquet.getIntArray("info");  //le int array avec les valeurs de la ligne
            int no = paquet.getInt("numero", 0);
            int type = paquet.getInt("type", 0);


            if(type == 1){
                //verifie la validité de l'entrée et fait un popup
                if (!verifieValide(type, nouvelinfo)){
                    popValidation(1);
                }
                else {
                    String[] min = res.getStringArray(R.array.min);
                    String s_heure = nouvelinfo[0] + "h" + min[nouvelinfo[1]] + " - " + nouvelinfo[2] + "h" + min[nouvelinfo[3]];
                    // ligne heure 3
                    if (no == 1) {
                        bh1.setText(s_heure);
                        pancarte.setHeure(nouvelinfo, no);
                        pancarte.heureSetActive(true, no);
                        findViewById(R.id.layoutHeure1).setVisibility(View.VISIBLE);
                        // efface je bouton ajout si les 3 lignes sont activés
                        if (pancarte.heureIsActive(1) && pancarte.heureIsActive(2) && pancarte.heureIsActive(3)) {
                            findViewById(R.id.boutAjoutHeure).setVisibility(View.GONE);
                        }
                    } else {
                        // ligne heure 2
                        if (no == 2) {
                            bh2.setText(s_heure);
                            pancarte.setHeure(nouvelinfo, no);
                            pancarte.heureSetActive(true, no);
                            findViewById(R.id.layoutHeure2).setVisibility(View.VISIBLE);
                            // efface je bouton ajout si les 3 lignes sont activés
                            if (pancarte.heureIsActive(1) && pancarte.heureIsActive(2) && pancarte.heureIsActive(3)) {
                                findViewById(R.id.boutAjoutHeure).setVisibility(View.GONE);
                            }

                        }
                        // ligne heure 3
                        if (no == 3) {
                            bh3.setText(s_heure);
                            pancarte.setHeure(nouvelinfo, no);
                            pancarte.heureSetActive(true, no);
                            findViewById(R.id.layoutHeure3).setVisibility(View.VISIBLE);
                            // efface je bouton ajout si les 3 lignes sont activés
                            if (pancarte.heureIsActive(1) && pancarte.heureIsActive(2) && pancarte.heureIsActive(3)) {
                                findViewById(R.id.boutAjoutHeure).setVisibility(View.GONE);
                            }
                        }
                    }
                    //enlever et détruire le fragment
                    ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                    fragmentTransaction.remove(frag);
                    fragmentTransaction.commit();
                    //reactivation des boutons de pancarte activity
                    activationBouton();
                }

            }
            else{
                if (type == 2){
                    //verifie la validité de l'entrée et fait un popup
                    if (!verifieValide(type, nouvelinfo)){
                        popValidation(2);
                    }
                    else {
                        String[] eta = res.getStringArray(R.array.eta);
                        String ligneJour = quelJour(nouvelinfo[0]) + " " + eta[nouvelinfo[2]] + " " + quelJour(nouvelinfo[1]);
                        // ligne heure 1
                        if (no == 1) {
                            bj1.setText(ligneJour);
                            pancarte.setJour(nouvelinfo, no);
                            pancarte.jourSetActive(true, no);
                            findViewById(R.id.layoutJour1).setVisibility(View.VISIBLE);
                            // efface je bouton ajout si les 3 lignes sont activés
                            if (pancarte.jourIsActive(1) && pancarte.jourIsActive(2) && pancarte.jourIsActive(3)) {
                                findViewById(R.id.boutAjoutJour).setVisibility(View.GONE);
                            }
                        } else {
                            // ligne heure 2
                            if (no == 2) {
                                bj2.setText(ligneJour);
                                pancarte.setJour(nouvelinfo, no);
                                pancarte.jourSetActive(true, no);
                                findViewById(R.id.layoutJour2).setVisibility(View.VISIBLE);
                                // efface je bouton ajout si les 3 lignes sont activés
                                if (pancarte.jourIsActive(1) && pancarte.jourIsActive(2) && pancarte.jourIsActive(3)) {
                                    findViewById(R.id.boutAjoutJour).setVisibility(View.GONE);
                                }
                            } else {
                                // ligne heure 3
                                if (no == 3) {
                                    bj3.setText(ligneJour);
                                    pancarte.setJour(nouvelinfo, no);
                                    pancarte.jourSetActive(true, no);
                                    findViewById(R.id.layoutJour3).setVisibility(View.VISIBLE);
                                    // efface je bouton ajout si les 3 lignes sont activés
                                    if (pancarte.jourIsActive(1) && pancarte.jourIsActive(2) && pancarte.jourIsActive(3)) {
                                        findViewById(R.id.boutAjoutJour).setVisibility(View.GONE);

                                    }
                                }
                            }
                        }
                        //enlever et détruire le fragment
                        ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                        fragmentTransaction.remove(frag);
                        fragmentTransaction.commit();
                        //reactivation des boutons de pancarte activity
                        activationBouton();
                    }
                }
                else {
                    if (type == 3) {
                        //verifie la validité de l'entrée et fait un popup
                        if (!verifieValide(type, nouvelinfo)) {
                            popValidation(3);
                        } else {
                            String ligneMois = nouvelinfo[0] + " " + quelMois(nouvelinfo[1]) + " - " + nouvelinfo[2] + " " + quelMois(nouvelinfo[3]);
                            bm1.setText(ligneMois);
                            pancarte.setmois(nouvelinfo);
                            pancarte.moisSetActive(true);
                            findViewById(R.id.layoutMois1).setVisibility(View.VISIBLE);
                            // efface je bouton ajout
                            findViewById(R.id.boutAjoutMois).setVisibility(View.GONE);
                            //enlever et détruire le fragment
                            ajoutFav.setImageResource(R.drawable.icon_etoile_vide);
                            fragmentTransaction.remove(frag);
                            fragmentTransaction.commit();
                            //reactivation des boutons de pancarte activity
                            activationBouton();
                        }
                    }
                }
            }

        }
        else{
            fragmentTransaction.remove(frag);
            fragmentTransaction.commit();
            //reactivation des boutons de pancarte activity
            activationBouton();
        }
    }

    // désactive tout les boutons de l'activity pancarte lorsque nous somme dans le fragment
    private void desactivationBouton(){

        findViewById(R.id.boutHeure1).setEnabled(false);
        findViewById(R.id.boutHeure2).setEnabled(false);
        findViewById(R.id.boutHeure3).setEnabled(false);
        findViewById(R.id.boutAjoutHeure).setEnabled(false);
        findViewById(R.id.boutJour1).setEnabled(false);
        findViewById(R.id.boutJour2).setEnabled(false);
        findViewById(R.id.boutJour3).setEnabled(false);
        findViewById(R.id.boutAjoutJour).setEnabled(false);
        findViewById(R.id.boutMois1).setEnabled(false);
        findViewById(R.id.boutAjoutMois).setEnabled(false);
        findViewById(R.id.boutDeleteH1).setEnabled(false);
        findViewById(R.id.boutDeleteH2).setEnabled(false);
        findViewById(R.id.boutDeleteH3).setEnabled(false);
        findViewById(R.id.boutDeleteJ1).setEnabled(false);
        findViewById(R.id.boutDeleteJ2).setEnabled(false);
        findViewById(R.id.boutDeleteJ3).setEnabled(false);
        findViewById(R.id.boutDeleteM1).setEnabled(false);
        findViewById(R.id.boutFlecheD).setEnabled(false);
        findViewById(R.id.boutFlecheG).setEnabled(false);
        findViewById(R.id.boutPancD).setEnabled(false);
        findViewById(R.id.boutPancG).setEnabled(false);
        findViewById(R.id.boutAjoutFavorie).setVisibility(View.GONE);
        findViewById(R.id.boutFavorie).setVisibility(View.GONE);
        findViewById(R.id.boutPancOk).setVisibility(View.GONE);
        findViewById(R.id.boutCancelPanc).setVisibility(View.GONE);
    }

    // réactive tout les bouton de activity pancarte lorsque nous sortont du fragment
    private void activationBouton(){

        findViewById(R.id.boutHeure1).setEnabled(true);
        findViewById(R.id.boutHeure2).setEnabled(true);
        findViewById(R.id.boutHeure3).setEnabled(true);
        findViewById(R.id.boutAjoutHeure).setEnabled(true);
        findViewById(R.id.boutJour1).setEnabled(true);
        findViewById(R.id.boutJour2).setEnabled(true);
        findViewById(R.id.boutJour3).setEnabled(true);
        findViewById(R.id.boutAjoutJour).setEnabled(true);
        findViewById(R.id.boutMois1).setEnabled(true);
        findViewById(R.id.boutAjoutMois).setEnabled(true);
        findViewById(R.id.boutDeleteH1).setEnabled(true);
        findViewById(R.id.boutDeleteH2).setEnabled(true);
        findViewById(R.id.boutDeleteH3).setEnabled(true);
        findViewById(R.id.boutDeleteJ1).setEnabled(true);
        findViewById(R.id.boutDeleteJ2).setEnabled(true);
        findViewById(R.id.boutDeleteJ3).setEnabled(true);
        findViewById(R.id.boutDeleteM1).setEnabled(true);
        findViewById(R.id.boutFlecheD).setEnabled(true);
        findViewById(R.id.boutFlecheG).setEnabled(true);
        findViewById(R.id.boutPancD).setEnabled(true);
        findViewById(R.id.boutPancG).setEnabled(true);
        findViewById(R.id.boutAjoutFavorie).setVisibility(View.VISIBLE);
        findViewById(R.id.boutFavorie).setVisibility(View.VISIBLE);
        findViewById(R.id.boutPancOk).setVisibility(View.VISIBLE);
        findViewById(R.id.boutCancelPanc).setVisibility(View.VISIBLE);
    }

    // va chercher tout les infos de la pancarte et les mets dans les divers boutons
    private void setInfoStart(Intent intent){

        // sort tout les infos et le mettre dans pancarte
        int[] heure1 = intent.getIntArrayExtra("HEURE1");
        pancarte.setHeure(heure1, 1);
        int[] heure2 = intent.getIntArrayExtra("HEURE2");
        pancarte.setHeure(heure2, 2);
        int[] heure3 = intent.getIntArrayExtra("HEURE3");
        pancarte.setHeure(heure3, 3);
        int[] jour1 = intent.getIntArrayExtra("JOUR1");
        pancarte.setJour(jour1, 1);
        int[] jour2 = intent.getIntArrayExtra("JOUR2");
        pancarte.setJour(jour2, 2);
        int[] jour3 = intent.getIntArrayExtra("JOUR3");
        pancarte.setJour(jour3, 3);
        int[] mois = intent.getIntArrayExtra("MOIS");
        pancarte.setmois(mois);
        int fleche = intent.getIntExtra("FLECHE", 0);
        pancarte.setFleche(fleche);
        noFleche = fleche;
        setFleche(noFleche);
        int image = intent.getIntExtra("IMAGE", 0);
        pancarte.setImage(image);
        noImageP = image;
        setImageParking(noImageP);

        boolean[] actif = intent.getBooleanArrayExtra("ACTIVE");
        pancarte.heureSetActive(actif[0], 1);
        pancarte.heureSetActive(actif[1], 2);
        pancarte.heureSetActive(actif[2], 3);
        pancarte.jourSetActive(actif[3], 1);
        pancarte.jourSetActive(actif[4], 2);
        pancarte.jourSetActive(actif[5], 3);
        pancarte.moisSetActive(actif[6]);

        tutorielPanc = intent.getBooleanExtra("TUTORIELPANC", true);
        trouver = intent.getIntExtra("TROUVER", 0);

        // mettre les info de la pancarte dans les boutons
        ecrireBoutonHeure(1);
        ecrireBoutonHeure(2);
        ecrireBoutonHeure(3);
        ecrireBoutonJour(1);
        ecrireBoutonJour(2);
        ecrireBoutonJour(3);
        ecrireBoutonMois();

        //ajustement du nombre de bouton actif
        int actifHeure = 0;
        int actifJour = 0;
        for (int i = 0; i<3; i++){
            if (actif[i]) {
                    findViewById(R.id.layoutHeure1 + i).setVisibility(View.VISIBLE);
                    if (actifHeure == 3){
                        findViewById(R.id.boutAjoutHeure).setVisibility(View.GONE);
                    }
            }
            else{
                findViewById(R.id.layoutHeure1 + i).setVisibility(View.GONE);
                findViewById(R.id.boutAjoutHeure).setVisibility(View.VISIBLE);
            }
        }
        for (int i = 3; i<6; i++){
            if (actif[i]) {
                findViewById(R.id.layoutJour1 + (i - 3)).setVisibility(View.VISIBLE);
                if (actifJour == 3){
                    findViewById(R.id.boutAjoutJour).setVisibility(View.GONE);
                }
            }
            else{
                findViewById(R.id.layoutJour1 + (i - 3)).setVisibility(View.GONE);
                findViewById(R.id.boutAjoutJour).setVisibility(View.VISIBLE);
            }
        }
        if (actif[6]){
            findViewById(R.id.layoutMois1).setVisibility(View.VISIBLE);
            findViewById(R.id.boutAjoutMois).setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.layoutMois1).setVisibility(View.GONE);
            findViewById(R.id.boutAjoutMois).setVisibility(View.VISIBLE);
        }
    }

    /*
    met le texte dans le bouton d'heure
    in: numéro de bouton
    */
    private void ecrireBoutonHeure(int numero){
        //aller chercher la ligne dans la pancarte
        int[] heure = pancarte.getHeure(numero);
        //aller chercher le string array des minutes dans les resources
        Resources res = getResources();
        String[] min = res.getStringArray(R.array.min);
        // ecrire le string
        String s_heure = heure[0] + "h" + min[heure[1]] + " - " + heure[2] + "h" + min[heure[3]];
        // mettre dans le bon bouton
        if (numero == 1) {
            bh1.setText(s_heure);
        }
        else{
            if (numero == 2) {
                bh2.setText(s_heure);
            }
            else{
                bh3.setText(s_heure);
            }
        }
    }

    /*
    met le texte dans le bouton de jour
    in: numéro de bouton
    */
    private void ecrireBoutonJour(int numero){

        int[] jour = pancarte.getJour(numero);  //aller chercher la ligne dans la pancarte
        // aller chercher le stringarray de ET/A dans les ressources
        Resources res = getResources();
        String[] eta = res.getStringArray(R.array.eta);

        String ligneJour = quelJour(jour[0]) + " " + eta[jour[2]] + " " + quelJour(jour[1]);
        // choisir le bon bouton
        if (numero == 1){
            bj1.setText(ligneJour);
        }
        else{
            if (numero == 2){
                bj2.setText(ligneJour);
            }
            else{
                bj3.setText(ligneJour);
            }
        }
    }

    /*
    convertir le int du jour a un String
    in: int numero du jour
    out: string du jour
    */
    public String quelJour(int numero){
        //aller chercher le stringarray de jour dans les ressources
        Resources res = getResources();
        String[] jour = res.getStringArray(R.array.jours);
        return jour[numero];
    }

    //met le texte dans le bouton des mois
    private void ecrireBoutonMois(){
        int[] mois = pancarte.getMois();  //aller chercher la ligne dans la pancarte
        String ligneMois = mois[0] + " " + quelMois(mois[1]) + " - " + mois[2] + " " + quelMois(mois[3]);
        bm1.setText(ligneMois);
    }

    /*
    convertir le int du mois a un String
    in: int numero du mois
    out: string du mois
    */
    public String quelMois (int numero) {
        //aller chercher le stringarray de mois dans les ressources
        Resources res = getResources();
        String[] mois = res.getStringArray(R.array.mois);
        return mois[numero-1];
    }

    //change l'image de fleche selon le numero donné de 0 à 3
    private void setFleche(int no){
        pancarte.setFleche(no);
        ImageView im = findViewById(R.id.imageViewFleche);
        switch (no){
            case 0: im.setImageResource(R.drawable.fleche_pas);
                break;
            case 1: im.setImageResource(R.drawable.fleche_g);
                break;
            case 2: im.setImageResource(R.drawable.fleche_double);
                break;
            default: im.setImageResource(R.drawable.fleche_d);
                break;
        }
    }

    //change l'image du p/stop selon le numero donné de 0 à 1
    private void setImageParking(int no){
        pancarte.setImage(no);
        ImageView im = findViewById(R.id.imageViewPanc);
        switch(no) {
            case 0:
                im.setImageResource(R.drawable.nop);
                break;
            default:
                im.setImageResource(R.drawable.noa);
                break;
        }
    }

    // remet tout les infos dans le intent pour les retourner a main par un intent
    private void setInfoStop(){
        Intent intent = new Intent();
        intent.putExtra("HEURE1", pancarte.getHeure(1));
        intent.putExtra("HEURE2", pancarte.getHeure(2));
        intent.putExtra("HEURE3", pancarte.getHeure(3));
        intent.putExtra("JOUR1", pancarte.getJour(1));
        intent.putExtra("JOUR2", pancarte.getJour(2));
        intent.putExtra("JOUR3", pancarte.getJour(3));
        intent.putExtra("MOIS", pancarte.getMois());
        intent.putExtra("FLECHE", pancarte.getFleche());
        intent.putExtra("IMAGE", pancarte.getImage());
        intent.putExtra("NUMPANCARTE", numero);
        boolean[] actif = {pancarte.heureIsActive(1), pancarte.heureIsActive(2), pancarte.heureIsActive(3),
                pancarte.jourIsActive(1), pancarte.jourIsActive(2), pancarte.jourIsActive(3),
                pancarte.moisIsActive()};
        intent.putExtra("ACTIVE", actif);
        intent.putExtra("TUTORIELPANC", tutorielPanc);
        intent.putExtra("TROUVER", trouver);
        setResult(RESULT_OK, intent);
        finish();
    }
    // le bouton cancel fait comme le bouton back
    private void cancel(){
        Intent intent = getIntent();
        intent.putExtra("TUTORIELPANC", tutorielPanc);
        intent.putExtra("TROUVER", trouver);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    //fonction pour sauvegarger un nouveau favorie

    private void sauvegardeFavorie() {
        // donne la liste des files
        String[] liste = fileList();
        // je vais choisir le prochain nom disponible

//        String name = "com.noticket.numero";  // jai enregistrer le numero de la derniere files sauvgarde ici
//        int trouver= 0;
//        // recherche et prend le fichier numero
//        try {
//            FileInputStream fis = openFileInput(name);
//            ObjectInputStream is = new ObjectInputStream(fis);
//            trouver = (int) is.readObject();
//            is.close();
//            fis.close();
//            FileOutputStream fos = openFileOutput(name, MODE_PRIVATE);
//            ObjectOutputStream os = new ObjectOutputStream(fos);
//            trouver++;
//            os.writeObject(trouver);
//            os.close();
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // nom de la prochaine file de favorie
        String name = "com.noticket.sauvegarde" + trouver;

        //ouvrire la file
        try {
            File file = new File(getFilesDir(), name);
            FileOutputStream outputStream = openFileOutput(name, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            //y mettre les objets

            os.writeObject(pancarte.getHeure(1)); //HEURE1
            os.writeObject(pancarte.getHeure(2)); //HEURE2
            os.writeObject(pancarte.getHeure(3)); //HEURE3
            os.writeObject(pancarte.getJour(1)); //JOUR1
            os.writeObject(pancarte.getJour(2)); //JOUR2
            os.writeObject(pancarte.getJour(3)); //JOUR3
            os.writeObject(pancarte.getMois()); //MOIS1
            os.writeObject( pancarte.getFleche()); //FLECHE
            os.writeObject( pancarte.getImage());  //IMAGE PANCARTE
            boolean[] actif = {pancarte.heureIsActive(1), pancarte.heureIsActive(2), pancarte.heureIsActive(3),
                    pancarte.jourIsActive(1), pancarte.jourIsActive(2), pancarte.jourIsActive(3),
                    pancarte.moisIsActive()};
            os.writeObject(actif);
            //fermer le tout
            os.close();
            outputStream.close();
            Toast.makeText(getApplicationContext(),"Sauvegardé dans vos favories", Toast.LENGTH_SHORT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
