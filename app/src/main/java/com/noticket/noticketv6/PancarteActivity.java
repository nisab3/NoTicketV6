package com.noticket.noticketv6;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.SeekBar;


public class PancarteActivity extends AppCompatActivity {
    private int numero = 1;
    private SeekBar barHeure;
    private SeekBar barJour;
    private SeekBar barMois;
    private Button bh1;
    private Button bh2;
    private Button bh3;
    private Button bj1;
    private Button bj2;
    private Button bj3;
    private Button bm1;
    private FloatingActionButton ok;
    private Button can;
    // l'objet pancarte traité
    public Pancarte pancarte;

    //ImageSwitcher is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pancarte);

        Intent intent = getIntent();
        numero = intent.getIntExtra("NUMPANCARTE", numero);

        pancarte = new Pancarte();

        // listenner pour les seekbars
        barHeure = findViewById(R.id.seekBarHeure);
        barJour = findViewById(R.id.seekBarJour);
        barMois = findViewById(R.id.seekBarMois);
        barHeure.setOnSeekBarChangeListener(barChangement);
        barJour.setOnSeekBarChangeListener(barChangement);
        barMois.setOnSeekBarChangeListener(barChangement);

        //listener pour les boutons
        bh1 = findViewById(R.id.boutHeure1);
        bh2 = findViewById(R.id.boutHeure2);
        bh3 = findViewById(R.id.boutHeure3);
        bj1 = findViewById(R.id.boutJour1);
        bj2 = findViewById(R.id.boutJour2);
        bj3 = findViewById(R.id.boutJour3);
        bm1 = findViewById(R.id.boutMois1);
        ok = findViewById(R.id.floatingActionButton);
        can = findViewById(R.id.boutCancelPanc);

        bh1.setOnClickListener(b);
        bh2.setOnClickListener(b);
        bh3.setOnClickListener(b);
        bj1.setOnClickListener(b);
        bj2.setOnClickListener(b);
        bj3.setOnClickListener(b);
        bm1.setOnClickListener(b);
        ok.setOnClickListener(b);
        can.setOnClickListener(b);

        setInfoStart();

        //is = findViewById(R.id.stop);
        //is.setOnGenericMotionListener(im);
    }

    // que faire si le bouton back est clicker
    // je dois la overrider car sinon ca repars une nouvelle MainActivity et perds tout les pancarte déja fait
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    /* ecoute si il y a un changement sur les SeekBars et change les dessins de numero */
    private SeekBar.OnSeekBarChangeListener barChangement = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (i == 0) {
                seekBar.setThumb(getDrawable(R.drawable.number_0));
                barChangeVisible(seekBar, i);
            } else {
                if (i == 1) {
                    seekBar.setThumb(getDrawable(R.drawable.number_1));
                    barChangeVisible(seekBar, i);
                } else {
                    if (i == 2) {
                        seekBar.setThumb(getDrawable(R.drawable.number_2));
                        barChangeVisible(seekBar, i);
                    } else {
                        seekBar.setThumb(getDrawable(R.drawable.number_3));
                        barChangeVisible(seekBar, i);
                    }
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //test imageswitcher

//    ImageSwitcher.OnGenericMotionListener im = new View.OnGenericMotionListener() {
//        @Override
//        public boolean onGenericMotion(View view, MotionEvent motionEvent) {
//            if (view.getId() == R.id.stop){
//                is.getNextView();
//            }
//            return false;
//        }
//    };

    /*
    fonction pour faire apparaitre les boutons heure, jour, mois
    selon le nombre dans le seekBar fournie
    et activer ou désactiver le ligne dans pancarte
    in : SeekBar, int état u seekbar
    out: rien
    */
    private void barChangeVisible(SeekBar seekbar, int i){
        int barId = seekbar.getId();
        if (barId == R.id.seekBarHeure){
            for (int j = 0; j < 3; j++ ){
                if (j < i){
                    findViewById(R.id.boutHeure1 + j).setVisibility(View.VISIBLE);
                    pancarte.heureSetActive(true, 1+j);
                }
                else{
                    findViewById(R.id.boutHeure1 + j).setVisibility(View.GONE);
                    pancarte.heureSetActive(false, 1+j);
                }
            }
        }
        else {
            if (barId == R.id.seekBarJour) {
                for (int j = 0; j < 3; j++) {
                    if (j < i) {
                        findViewById(R.id.boutJour1 + j).setVisibility(View.VISIBLE);
                        pancarte.jourSetActive(true, 1+j);
                    } else {
                        findViewById(R.id.boutJour1 + j).setVisibility(View.GONE);
                        pancarte.jourSetActive(false, 1+j);
                    }
                }
            }
            else {
                //(barId == R.id.barMois)

                    if (i == 1) {
                        findViewById(R.id.boutMois1).setVisibility(View.VISIBLE);
                        pancarte.moisSetActive(true);
                    } else {
                        findViewById(R.id.boutMois1).setVisibility(View.GONE);
                        pancarte.moisSetActive(false);
                    }
            }
        }
    }

    /* écoute si on click sur un bouton de l'activité ou du fragment et décide de ce qu'il doit faire */
    private Button.OnClickListener b = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //floating button
            if (view.getId() == R.id.floatingActionButton){
                setInfoStop();
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
            if (view.getId() == R.id.boutCancelPanc){
                cancel();
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


    public void clickFermerFrag(View view){

        //reactivation des boutons de pancarte activity
        activationBouton();

        //enlever et détruire le fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment frag = fragmentManager.findFragmentById(R.id.layoutPourChoixFrag);

        // va chercher les infos stocker dans le fragment avant de le détruire
        // et changer le text du bouton concerné
        // et mettre les changement dans pancarte
        if (view.getId() == R.id.boutFragOk) {
            // sors les info du paquet de fragment
            Bundle paquet = frag.getArguments();
            Resources res = getResources();
            int[] nouvelinfo = paquet.getIntArray("info");  //le int array avec les valeurs de la ligne
            int no = paquet.getInt("numero", 0);
            int type = paquet.getInt("type", 0);
            if(type == 1){
                String[] min = res.getStringArray(R.array.min);
                String s_heure = nouvelinfo[0] + "h" + min[nouvelinfo[1]] + " - " + nouvelinfo[2] + "h" + min[nouvelinfo[3]];
                if (no == 1){
                    bh1.setText(s_heure);
                    pancarte.setHeure(nouvelinfo, no);
                }
                else{
                    if (no == 2){
                        bh2.setText(s_heure);
                        pancarte.setHeure(nouvelinfo, no);
                    }
                    if(no == 3){
                        bh3.setText(s_heure);
                        pancarte.setHeure(nouvelinfo, no);
                    }
                }
            }
            else{
                if (type == 2){
                    String[] eta = res.getStringArray(R.array.eta);
                    String ligneJour = quelJour(nouvelinfo[0]) + " " + eta[nouvelinfo[2]] + " " + quelJour(nouvelinfo[1]);
                    if (no == 1){
                        bj1.setText(ligneJour);
                        pancarte.setJour(nouvelinfo, no);
                    }
                    else{
                        if (no ==2){
                            bj2.setText(ligneJour);
                            pancarte.setJour(nouvelinfo, no);
                        }
                        else{
                            if (no == 3){
                                bj3.setText(ligneJour);
                                pancarte.setJour(nouvelinfo, no);
                            }
                        }
                    }
                }
                else {
                    if (type == 3) {
                        String ligneMois = nouvelinfo[0] + " " + quelMois(nouvelinfo[1]) + " - " + nouvelinfo[2] + " " + quelMois(nouvelinfo[3]);
                        bm1.setText(ligneMois);
                        pancarte.setmois(nouvelinfo);
                    }
                }
            }

        }

        fragmentTransaction.remove(frag);
        fragmentTransaction.commit();
    }

    // désactive tout les boutons de l'activity pancarte lorsque nous somme dans le fragment
    private void desactivationBouton(){
        findViewById(R.id.seekBarHeure).setEnabled(false);
        findViewById(R.id.seekBarJour).setEnabled(false);
        findViewById(R.id.seekBarMois).setEnabled(false);
        findViewById(R.id.seekBarJour).setEnabled(false);
        findViewById(R.id.seekBarMois).setEnabled(false);
        findViewById(R.id.boutHeure1).setEnabled(false);
        findViewById(R.id.boutHeure2).setEnabled(false);
        findViewById(R.id.boutHeure3).setEnabled(false);
        findViewById(R.id.boutJour1).setEnabled(false);
        findViewById(R.id.boutJour2).setEnabled(false);
        findViewById(R.id.boutJour3).setEnabled(false);
        findViewById(R.id.boutMois1).setEnabled(false);
    }

    // réactive tout les bouton de activity pancarte lorsque nous sortont du fragment
    private void activationBouton(){
        findViewById(R.id.seekBarHeure).setEnabled(true);
        findViewById(R.id.seekBarJour).setEnabled(true);
        findViewById(R.id.seekBarMois).setEnabled(true);
        findViewById(R.id.seekBarHeure).setEnabled(true);
        findViewById(R.id.seekBarJour).setEnabled(true);
        findViewById(R.id.seekBarMois).setEnabled(true);
        findViewById(R.id.boutHeure1).setEnabled(true);
        findViewById(R.id.boutHeure2).setEnabled(true);
        findViewById(R.id.boutHeure3).setEnabled(true);
        findViewById(R.id.boutJour1).setEnabled(true);
        findViewById(R.id.boutJour2).setEnabled(true);
        findViewById(R.id.boutJour3).setEnabled(true);
        findViewById(R.id.boutMois1).setEnabled(true);
    }

    // va chercher tout les infos de la pancarte et les mets dans les divers boutons
    private void setInfoStart(){
        Intent intent = getIntent();

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
        int image = intent.getIntExtra("IMAGE", 0);
        pancarte.setImage(image);
        boolean[] actif = intent.getBooleanArrayExtra("ACTIVE");
        pancarte.heureSetActive(actif[0], 1);
        pancarte.heureSetActive(actif[1], 2);
        pancarte.heureSetActive(actif[2], 3);
        pancarte.jourSetActive(actif[3], 1);
        pancarte.jourSetActive(actif[4], 2);
        pancarte.jourSetActive(actif[5], 3);
        pancarte.moisSetActive(actif[6]);

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
        int actifMois = 0;
        for (int i = 0; i<3; i++){
            if (actif[i]) {
                    actifHeure ++;
            }
        }
        for (int i = 3; i<6; i++){
            if (actif[i]) {
                actifJour ++;
            }
        }
        if (actif[6]){
            actifMois ++;
        }
        barMois.setProgress(actifMois);
        barJour.setProgress(actifJour);
        barHeure.setProgress(actifHeure);
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
        setResult(RESULT_OK, intent);
        finish();
    }
    // le bouton cancel
    private void cancel(){
        finish();
    }

}
