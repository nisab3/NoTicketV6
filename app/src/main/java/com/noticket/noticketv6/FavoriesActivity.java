package com.noticket.noticketv6;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
/**
 * Created by Nicolas Sabourin 1068459
 *            Tommy Côté  1056362
 *            Charles-Frédéric Amringer
 */
public class FavoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    MyAdapter adapter;
    ListView list;
    String[] favlist;
    String[] favbonlist;

    Button ok;
    Button cancel;
    Button supprimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favories);

        //changer le titre de l'activité
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Favoris");


        // faire la liste de tout les files favorie
        rechercheFavorie();

        list = (ListView) findViewById((R.id.list_fav));

        // call pour faire le boucle et ajouter tout les files
        adapter = new MyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(FavoriesActivity.this);


    }

    private void rechercheFavorie(){
        // prendre la liste de tout les favories
        favlist = fileList();
        // chercher juste les files qui sont des favoris
        String[] favbonlisttemp = new String[favlist.length];
        int indexbonfav = 0;
        for(int indexfav = 0; indexfav < favlist.length; indexfav++){
            if (favlist[indexfav].startsWith("com.noticket.sauvegarde")) {
                favbonlisttemp[indexbonfav] = favlist[indexfav];
                indexbonfav++;
            }
        }
        // tranfere dans une liste sans null a la fin
        if (favbonlisttemp.length >= 0) {
            favbonlist = new String[indexbonfav + 1];
            for (int i = 0; i < indexbonfav ; i++) {
                favbonlist[i] = favbonlisttemp[i];
            }
        }
    }
    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view1, final int i, long l) {

        AlertDialog.Builder positionBuilder = new AlertDialog.Builder(FavoriesActivity.this);
        final View fView = getLayoutInflater().inflate(R.layout.pop_favorie, null);
        positionBuilder.setView(fView);
        final AlertDialog dialog = positionBuilder.create();

        //initialiser les boutons
        ok = fView.findViewById(R.id.boutFavOk);
        cancel = fView.findViewById(R.id.boutFavAnnuler);
        supprimer = fView.findViewById(R.id.boutFavSup);

         //fonction pour les clicks sur les bouton Cancel, supprimer, ok du fragment popFavorie
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                fermeture(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                suprimeItemFavorie(adapterView, view1, i);
            }
        });

        dialog.show();
    }



    public class MyAdapter extends BaseAdapter{

        LayoutInflater inflater;

        public MyAdapter(){
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            return favbonlist.length -1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null){
                view = inflater.inflate(R.layout.rangee_pancarte_fav, viewGroup, false);
            }
            if (favbonlist[i] != null) {
                try {
                    File file = new File(getFilesDir(), favbonlist[i]);
                    FileInputStream fis = openFileInput(favbonlist[i]);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    //aller chercher les objets
                    int[] heure1 = (int[]) is.readObject();
                    int[] heure2 = (int[]) is.readObject();
                    int[] heure3 = (int[]) is.readObject();
                    int[] jour1 = (int[]) is.readObject();
                    int[] jour2 = (int[]) is.readObject();
                    int[] jour3 = (int[]) is.readObject();
                    int[] mois1 = (int[]) is.readObject();
                    int fleche = (int) is.readObject();
                    int image = (int) is.readObject();
                    boolean[] actif = (boolean[]) is.readObject();

                    // fermer le tout
                    is.close();
                    fis.close();

                    //creer une pancarte pour la lire
                    Pancarte panc = new Pancarte();
                    panc.setHeure(heure1, 1);
                    panc.setHeure(heure2, 2);
                    panc.setHeure(heure3, 3);
                    panc.setJour(jour1, 1);
                    panc.setJour(jour2, 2);
                    panc.setJour(jour3, 3);
                    panc.setmois(mois1);
                    panc.setFleche(fleche);
                    panc.setImage(image);

                    panc.heureSetActive(actif[0], 1);
                    panc.heureSetActive(actif[1], 2);
                    panc.heureSetActive(actif[2], 3);
                    panc.jourSetActive(actif[3], 1);
                    panc.jourSetActive(actif[4], 2);
                    panc.jourSetActive(actif[5], 3);
                    panc.moisSetActive(actif[6]);
                    // mets le texte
                    String resultat = formateText(panc);
                    TextView t = view.findViewById(R.id.textImFav);
                    t.setText(resultat);
                    //met l'image
                    ImageView im = view.findViewById(R.id.boutImFav);
                    if (image == 1) {
                        im.setImageResource(R.drawable.no_stop_blank);
                    } else {
                        im.setImageResource(R.drawable.no_parking_blank);
                    }

                    // mets la bonne fleche
                    ImageView f = view.findViewById(R.id.flecheFav);
                    switch (fleche) {
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return view;
        }
    }


    /*
    suprimer un ligne de la liste view et le files qui vient avec
    in : le adapterView pour enlever la ligne
    int i = place dans le view liste
    int index = place dans la favlist des nom de fichier
    */
    private void suprimeItemFavorie(AdapterView adapterView, View view, int index){
        //enleve la view et reset la page


        String name = favbonlist[index];
        try{
            // delete la file
            File file = new File(getFilesDir(), name);
            file.delete();

            // refait la liste de favorie
            rechercheFavorie();

            //refait la liste de view
            list.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // faire le text de la pancarte
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

    /*
    remet tout les infos dans le intent pour les retourner a main par un intent
    in: numero ou se trouve le nom de file dans la liste favlist
    out: rien
    */
    private void fermeture( int i){
        Intent intent = new Intent();
        try{

            FileInputStream fis = openFileInput(favbonlist[i]);
            ObjectInputStream is = new ObjectInputStream(fis);
            //aller chercher les objets
            int[] heure1 = (int[]) is.readObject();
            int[] heure2 = (int[]) is.readObject();
            int[] heure3 = (int[]) is.readObject();
            int[] jour1 = (int[]) is.readObject();
            int[] jour2 = (int[]) is.readObject();
            int[] jour3 = (int[]) is.readObject();
            int[] mois1 = (int[]) is.readObject();
            int fleche = (int) is.readObject();
            int image = (int) is.readObject();
            boolean[] actif = (boolean[]) is.readObject();

            // fermer le tout
            is.close();
            fis.close();

            intent.putExtra("HEURE1", heure1);
            intent.putExtra("HEURE2", heure2);
            intent.putExtra("HEURE3", heure3);
            intent.putExtra("JOUR1", jour1);
            intent.putExtra("JOUR2", jour2);
            intent.putExtra("JOUR3", jour3);
            intent.putExtra("MOIS", mois1);
            intent.putExtra("FLECHE", fleche);
            intent.putExtra("IMAGE", image);
            intent.putExtra("ACTIVE", actif);

        } catch (Exception e) {
            e.printStackTrace();
        }
        setResult(RESULT_OK, intent);
        finish();
    }

}
