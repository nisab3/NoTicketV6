package com.noticket.noticketv6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;


/**
 * Created by Nicolas Sabourin 1068459
 *            Tommy Côté  1056362
 *            Charles-Frédéric Amringer
 */

public class ChoixFragment extends Fragment {

    //1 = heure, 2 =jour, 3 = mois
    private int typeFrag = 0;
    // quel ligne 1, 2, 3 on se trouve
    private int numFrag = 0;

    // utilisé pour retourner les infos a pancarte activité
    private int[] resultat = {0, 0, 0, 0};

    //nommer tout les numberpicker
    private NumberPicker heure1;
    private NumberPicker heure2;
    private NumberPicker min1;
    private NumberPicker min2;
    private NumberPicker jour1;
    private NumberPicker jour2;
    private NumberPicker jour3;
    private NumberPicker mois1;
    private NumberPicker mois2;
    private NumberPicker moisNum1;
    private NumberPicker moisNum2;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choix_fragment_layout, container, false);



        return v;
    }

    /* appeler tout de suite apres la creation du fragement pour definir si c'est heure, jour ou mois */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // initialisation de tout les numberPickers
        heure1 = getActivity().findViewById(R.id.heurePicker1);
        heure2 = getActivity().findViewById(R.id.heurePicker2);
        min1 = getActivity().findViewById(R.id.minPicker1);
        min2 = getActivity().findViewById(R.id.minPicker2);
        jour1 = getActivity().findViewById(R.id.jourPicker1);
        jour2 = getActivity().findViewById(R.id.jourPicker2);
        jour3 = getActivity().findViewById(R.id.jourPicker3);
        moisNum1 = getActivity().findViewById(R.id.moisNumPicker1);
        moisNum2 = getActivity().findViewById(R.id.moisNumPicker2);
        mois1 = getActivity().findViewById(R.id.moisPicker1);
        mois2 = getActivity().findViewById(R.id.moisPicker2);

        // mettre le listener
        heure1.setOnValueChangedListener(n);
        heure2.setOnValueChangedListener(n);
        min1.setOnValueChangedListener(n);
        min2.setOnValueChangedListener(n);
        jour1.setOnValueChangedListener(n);
        jour2.setOnValueChangedListener(n);
        jour3.setOnValueChangedListener(n);
        moisNum1.setOnValueChangedListener(n);
        moisNum2.setOnValueChangedListener(n);
        mois1.setOnValueChangedListener(n);
        mois2.setOnValueChangedListener(n);

        Bundle paquet = getArguments();
        //sortir le numero du paquet
        numFrag = paquet.getInt("numero", 0);

        //sortir le type du paquet
        typeFrag = paquet.getInt("type", 0);

        //sortir la ligne des infos pancarte du paquet
        int[] info = paquet.getIntArray("info");

        // call le bon contructeur de l'interface voulu
        //si heure
        if (typeFrag == 1) {
            makeHeureFrag(info);
        }
        //si jour
        if (typeFrag == 2){
            makeJourFrag(info);
        }
        //si mois
        if (typeFrag == 3){
            makeMoisFrag(info);
        }
    }

    /* ecoute les changements des numberPicker et les sauve dans le bundle pour le retour */
    private NumberPicker.OnValueChangeListener n = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            if (typeFrag == 1){
                resultat[0] = heure1.getValue();
                resultat[1] = min1.getValue();
                resultat[2] = heure2.getValue();
                resultat[3] = min2.getValue();
                sauveBundle();
            }
            else {
                if(typeFrag ==2){
                    resultat[0] = jour1.getValue();
                    resultat[1] = jour3.getValue();
                    resultat[2] = jour2.getValue();
                    sauveBundle();
                }
                else {
                    if (typeFrag == 3) {
                        resultat[0] = moisNum1.getValue();
                        resultat[1] = mois1.getValue();
                        resultat[2] = moisNum2.getValue();
                        resultat[3] = mois2.getValue();
                        sauveBundle();
                        ajutementMois();
                    }
                }
            }
        }
    };

    /* fonction pour ajouter l'info de resultat dans le bundle */
    private void sauveBundle(){
        Bundle bundle = getArguments();
        bundle.putIntArray("info", resultat);
        this.setArguments(bundle);
    }

    /*
    creation du fragement pour saisir l'heure
    in: les informations de la ligne de la pancarte
    */
    private void makeHeureFrag(int[] info){
        TextView t = getActivity().findViewById(R.id.textMessagePicker);
        t.setText(R.string.text_heure_frag);
        // formate number picker de l'heure 1
        heure1.setWrapSelectorWheel(true);                                   //pour que la roue tourne sur elle meme
        heure1.setMinValue(0);                                               //valeur minimal
        heure1.setMaxValue(23);                                              //valeur maximal//mets la valeur de la pancarte
        heure1.setDisplayedValues(getResources().getStringArray(R.array.heure)); //changer tout les valeurs affichées
        heure1.setValue(info[0]);                                               //mets la valeur de la pancarte
        // formate number picker de l'heure 2
        heure2.setWrapSelectorWheel(true);
        heure2.setMinValue(0);
        heure2.setMaxValue(23);
        heure2.setDisplayedValues(getResources().getStringArray(R.array.heure));
        heure2.setValue(info[2]);

        // formate number picker des minutes 1
        min1.setWrapSelectorWheel(true);
        min1.setMinValue(0);
        min1.setMaxValue(1);
        min1.setValue(info[1]);
        min1.setDisplayedValues(getResources().getStringArray(R.array.min));


        // formate number picker des minutes 1
        min2.setWrapSelectorWheel(true);
        min2.setMinValue(0);
        min2.setMaxValue(1);
        min2.setDisplayedValues(getResources().getStringArray(R.array.min));
        min2.setValue(info[3]);

        // cache tout ce que nous avons pas besoin
        getActivity().findViewById(R.id.textHPicker1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.textHPicker2).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.textTraitPicker).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.heurePicker1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.heurePicker2).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.minPicker1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.minPicker2).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.jourPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.jourPicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.jourPicker3).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisNumPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisNumPicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.textAMois).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisPicker2).setVisibility(View.GONE);
    }

    /*
    creation du fragement pour saisir les jours
    in: les informations de la ligne de la pancarte
    */
    private void makeJourFrag(int[] info){
        TextView t = getActivity().findViewById(R.id.textMessagePicker);
        t.setText(R.string.text_jour_frag);
        // formate number picker du premier jour
        jour1.setMinValue(1);
        jour1.setMaxValue(7);
        jour1.setWrapSelectorWheel(true);
        jour1.setDisplayedValues(getResources().getStringArray(R.array.jours1));
        jour1.setValue(info[0]);

        // formate number picker du deuxieme jour
        jour3.setMinValue(0);
        jour3.setMaxValue(7);
        jour3.setWrapSelectorWheel(true);
        jour3.setDisplayedValues(getResources().getStringArray(R.array.jours));
        jour3.setValue(info[1]);

        // formate number picker du et ou à
        jour2.setMinValue(0);
        jour2.setMaxValue(2);
        jour2.setWrapSelectorWheel(true);
        jour2.setDisplayedValues(getResources().getStringArray(R.array.eta));
        jour2.setValue(info[2]);

        // cache tout ce que nous avons pas besoin
        getActivity().findViewById(R.id.textHPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.textHPicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.textTraitPicker).setVisibility(View.GONE);
        getActivity().findViewById(R.id.heurePicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.heurePicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.minPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.minPicker2).setVisibility(View.GONE);

        getActivity().findViewById(R.id.jourPicker1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.jourPicker2).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.jourPicker3).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.spaceJ1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.spaceJ2).setVisibility(View.VISIBLE);

        getActivity().findViewById(R.id.moisNumPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisNumPicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.textAMois).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisPicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.spaceM1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.spaceM2).setVisibility(View.GONE);
    }

    /*
    creation du fragement pour saisir les mois
    in: les informations de la ligne de la pancarte
    */
    private void makeMoisFrag(int[] info){
        TextView t = getActivity().findViewById(R.id.textMessagePicker);
        t.setText(R.string.text_mois_frag);
        // formate number picker du numéro du mois 1
        //moisNum1.setWrapSelectorWheel(true);
        moisNum1.setMaxValue(31);
        moisNum1.setMinValue(1);
        moisNum1.setValue(info[0]);

        // formate number picker du numéro du mois 2
        //moisNum2.setWrapSelectorWheel(true);
        moisNum2.setMaxValue(31);
        moisNum2.setMinValue(1);
        moisNum2.setValue(info[2]);

        // formate number picker du nom du mois 1
        //mois1.setWrapSelectorWheel(true);
        mois1.setMaxValue(12);
        mois1.setMinValue(1);
        mois1.setDisplayedValues(getResources().getStringArray(R.array.mois));
        mois1.setValue(info[1]);

        // formate number picker du nom du mois 2
        //mois2.setWrapSelectorWheel(true);
        mois2.setMaxValue(12);
        mois2.setMinValue(1);
        mois2.setDisplayedValues(getResources().getStringArray(R.array.mois));
        mois2.setValue(info[3]);

        //on ajuste au cas ou
        ajutementMois();

        // cache tout ce que nous avons pas besoin
        getActivity().findViewById(R.id.textHPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.textHPicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.textTraitPicker).setVisibility(View.GONE);
        getActivity().findViewById(R.id.heurePicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.heurePicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.minPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.minPicker2).setVisibility(View.GONE);

        getActivity().findViewById(R.id.jourPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.jourPicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.jourPicker3).setVisibility(View.GONE);
        getActivity().findViewById(R.id.spaceJ1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.spaceJ2).setVisibility(View.GONE);

        getActivity().findViewById(R.id.moisNumPicker1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.moisNumPicker2).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.textAMois).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.moisPicker1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.moisPicker2).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.spaceM1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.spaceM2).setVisibility(View.VISIBLE);
    }

    //TODO cest possible que ca marche pas ou je l'ai callé
    // fonction qui vérifie les mois entré pour changer le nombre de jour
    private void ajutementMois(){
       int m1 = mois1.getValue();
       int m2 = mois2.getValue();

       //février
       if (m1 == 1){
           moisNum1.setMaxValue(28);
       }
        if (m2 == 1){
            moisNum2.setMaxValue(28);
        }

        // mois a 31 jours
        if (m1 == 0  || m1 == 2 || m1 == 4 || m1 == 6 || m1 == 7 || m1 == 9 || m1 == 11){
            moisNum1.setMaxValue(31);
        }
        if (m2 == 0  || m2 == 2 || m2 == 4 || m2 == 6 || m2 == 7 || m2 == 9 || m2 == 11){
            moisNum2.setMaxValue(31);
        }

        // mois a 30 jours
        if (m1 == 3  || m1 == 5 || m1 == 4 || m1 == 8 || m1 == 10 || m1 == 12){
            moisNum1.setMaxValue(31);
        }
        if (m2 == 3  || m2 == 5 || m2 == 4 || m2 == 8 || m2 == 10 || m2 == 12){
            moisNum2.setMaxValue(31);
        }
    }

}
