package com.noticket.noticketv6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Nicolas on 2018-03-21.
 */

public class ChoixFragment extends Fragment {

    //1 = heure, 2 =jour, 3 = mois
    private int typeFrag = 0;
    // quel ligne 1, 2, 3 on se trouve
    private int numFrag = 0;

    @Nullable
    @Override
    //creation du fragment pour le retourner
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choix_fragment_layout, container, false);

        return v;
    }

    // appeler tout de suite apres la creation du fragement pour definir si c'est heure, jour ou mois
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Bundle paquet = getArguments();
        String id = paquet.getString("numero");
        switch (id){
            case "1":   numFrag = 1;
                        break;
            case "2":   numFrag = 2;
                        break;
            case "3":   numFrag = 3;
                        break;
            default:    numFrag = 0;
                        break;
        }
        String type = paquet.getString("type");
        switch (type){
            case "heure":    typeFrag = 1;
                             break;
            case "jour":     typeFrag = 2;
                             break;
            case "mois":     typeFrag= 3;
                             break;
            default:         typeFrag = 0;
                             break;
        }
        // call le bon contructeur de l'interface voulu
        //si heure
        if (typeFrag == 1) {
            makeHeureFrag();
        }
        //si jour
        if (typeFrag == 2){
            makeJourFrag();
        }
        //si mois
        if (typeFrag == 3){
            makeMoisFrag();
        }
    }

    // creation du fragement pour saisir l'heure
    private void makeHeureFrag(){

        // formate number picker de l'heure 1
        NumberPicker heure1 = getActivity().findViewById(R.id.heurePicker1); //va le chercher
        heure1.setWrapSelectorWheel(true);                                   //pour que la roue tourne sur elle meme
        heure1.setMinValue(0);                                               //valeur minimal
        heure1.setMaxValue(23);                                              //valeur maximal
        heure1.setDisplayedValues(getResources().getStringArray(R.array.heure)); //changer tout les valeurs affichées

        // formate number picker de l'heure 2
        NumberPicker heure2 = getActivity().findViewById(R.id.heurePicker2);
        heure2.setWrapSelectorWheel(true);
        heure2.setMinValue(0);
        heure2.setMaxValue(23);
        heure2.setDisplayedValues(getResources().getStringArray(R.array.heure));

        // formate number picker des minutes 1
        NumberPicker min1 = getActivity().findViewById(R.id.minPicker1);
        min1.setWrapSelectorWheel(true);
        min1.setMinValue(0);
        min1.setMaxValue(1);
        min1.setDisplayedValues(getResources().getStringArray(R.array.min));

        // formate number picker des minutes 1
        NumberPicker min2 = getActivity().findViewById(R.id.minPicker2);
        min2.setWrapSelectorWheel(true);
        min2.setMinValue(0);
        min2.setMaxValue(1);
        min2.setDisplayedValues(getResources().getStringArray(R.array.min));

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

    // creation du fragement pour saisir les jours
    private void makeJourFrag(){

        // formate number picker du premier jour
        NumberPicker jour1 = getActivity().findViewById(R.id.jourPicker1);
        jour1.setMinValue(0);
        jour1.setMaxValue(7);
        jour1.setWrapSelectorWheel(true);
        jour1.setDisplayedValues(getResources().getStringArray(R.array.jours));

        // formate number picker du deuxieme jour
        NumberPicker jour3 = getActivity().findViewById(R.id.jourPicker3);
        jour3.setMinValue(0);
        jour3.setMaxValue(7);
        jour3.setWrapSelectorWheel(true);
        jour3.setDisplayedValues(getResources().getStringArray(R.array.jours));

        // formate number picker du et ou à
        NumberPicker jour2 = getActivity().findViewById(R.id.jourPicker2);
        jour2.setMinValue(0);
        jour2.setMaxValue(2);
        jour2.setWrapSelectorWheel(true);
        jour2.setDisplayedValues(getResources().getStringArray(R.array.eta));

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
        getActivity().findViewById(R.id.moisNumPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisNumPicker2).setVisibility(View.GONE);
        getActivity().findViewById(R.id.textAMois).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisPicker1).setVisibility(View.GONE);
        getActivity().findViewById(R.id.moisPicker2).setVisibility(View.GONE);
    }

    // creation du fragement pour saisir les mois
    private void makeMoisFrag(){

        // formate number picker du numéro du mois 1
        NumberPicker moisNum1 = getActivity().findViewById(R.id.moisNumPicker1);
        moisNum1.setWrapSelectorWheel(true);
        moisNum1.setMaxValue(31);
        moisNum1.setMinValue(1);

        // formate number picker du numéro du mois 2
        NumberPicker moisNum2 = getActivity().findViewById(R.id.moisNumPicker2);
        moisNum2.setWrapSelectorWheel(true);
        moisNum2.setMaxValue(31);
        moisNum2.setMinValue(1);

        // formate number picker du nom du mois 1
        NumberPicker mois1 = getActivity().findViewById(R.id.moisPicker1);
        mois1.setWrapSelectorWheel(true);
        mois1.setMaxValue(12);
        mois1.setMinValue(1);
        mois1.setDisplayedValues(getResources().getStringArray(R.array.mois));

        // formate number picker du nom du mois 2
        NumberPicker mois2 = getActivity().findViewById(R.id.moisPicker2);
        mois2.setWrapSelectorWheel(true);
        mois2.setMaxValue(12);
        mois2.setMinValue(1);
        mois2.setDisplayedValues(getResources().getStringArray(R.array.mois));

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
        getActivity().findViewById(R.id.moisNumPicker1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.moisNumPicker2).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.textAMois).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.moisPicker1).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.moisPicker2).setVisibility(View.VISIBLE);
    }
}
