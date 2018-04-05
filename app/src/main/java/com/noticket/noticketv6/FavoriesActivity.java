package com.noticket.noticketv6;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class FavoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    MyAdapter adapter;
    ListView list;
    String[] favlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favories);

        // prendre la liste de tout les favories
        favlist = fileList();

        list = (ListView) findViewById((R.id.list_fav));

        // call pour faire le boucle et ajouter tout les files
        adapter = new MyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(FavoriesActivity.this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // a remplir pour si je click sur un item de la liste
        Toast.makeText(FavoriesActivity.this, "click", Toast.LENGTH_SHORT).show();
    }


    public class MyAdapter extends BaseAdapter{

        LayoutInflater inflater;

        public MyAdapter(){
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return favlist.length;
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
            try{
                if (favlist[i].startsWith("com.noticket.noticketv6.sauvegarde"));
                {
                    FileInputStream fis = openFileInput(favlist[i]);
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
//
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
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return view;
        }
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

}
