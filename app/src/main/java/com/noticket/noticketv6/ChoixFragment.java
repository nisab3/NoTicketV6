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
    int typeFrag = 0; //1 = heure, 2 =jour, 3 = mois
    int numFrag = 0; // quel ligne 1, 2, 3 on se trouve

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choix_fragment_layout, container, false);

        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        getActivity().findViewById(R.id.boutHeure1).setClickable(false);

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
        if (typeFrag == 1) {
            makeHeureFrag();
        }
        if (typeFrag == 2){
            makeJourFrag();
        }
    }

    private void makeHeureFrag(){
        NumberPicker heure1 = getActivity().findViewById(R.id.heurePicker1);
        heure1.setWrapSelectorWheel(true);
        heure1.setMinValue(0);
        heure1.setMaxValue(23);

        NumberPicker heure2 = getActivity().findViewById(R.id.heurePicker2);
        heure2.setWrapSelectorWheel(true);
        heure2.setMinValue(0);
        heure2.setMaxValue(23);

        String[] min = {"0", "15", "30", "45"};
        NumberPicker min1 = getActivity().findViewById(R.id.minPicker1);
        min1.setWrapSelectorWheel(true);
        min1.setMinValue(0);
        min1.setMaxValue(3);
        min1.setDisplayedValues(min);

        NumberPicker min2 = getActivity().findViewById(R.id.minPicker2);
        min2.setWrapSelectorWheel(true);
        min2.setMinValue(0);
        min2.setMaxValue(3);
        min2.setDisplayedValues(min);

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
    }

    private void makeJourFrag(){
        String[] semaine = {"", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        String[] eta = {"", "à", "et"};

        NumberPicker jour1 = getActivity().findViewById(R.id.jourPicker1);
        jour1.setMinValue(0);
        jour1.setMaxValue(7);
        jour1.setWrapSelectorWheel(true);
        jour1.setDisplayedValues(semaine);

        NumberPicker jour3 = getActivity().findViewById(R.id.jourPicker3);
        jour3.setMinValue(0);
        jour3.setMaxValue(7);
        jour3.setWrapSelectorWheel(true);
        jour3.setDisplayedValues(semaine);

        NumberPicker jour2 = getActivity().findViewById(R.id.jourPicker2);
        jour2.setMinValue(0);
        jour2.setMaxValue(2);
        jour2.setWrapSelectorWheel(true);
        jour2.setDisplayedValues(eta);

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

    }
}
