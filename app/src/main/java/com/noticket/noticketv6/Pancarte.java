package com.noticket.noticketv6;

/**
 * Created by Nicolas on 2018-03-25.
 */

public class Pancarte {

    private Heure hligne1;
    private Heure hligne2;
    private Heure hligne3;
    private Jour jligne1;
    private Jour jligne2;
    private Jour jligne3;
    private Mois mois;
    private int fleche; // 0 = pas fleche, 1 = gauche, 2 = droite, 3 = les deux
    private int image;
    private Heure [] heure = {hligne1, hligne2, hligne2};
    private Jour[] jour = {jligne1, jligne2, jligne3};

    public Pancarte() {
        fleche = 0;
        image = 0;
    }

    // class pour nouvel objet heure
    private class Heure {
        // variable heure
        int min1;
        int min2;
        int heure1;
        int heure2;
        boolean active;

        public Heure() {
            active = false;
            heure1 = 12;
            min1 = 0;
            heure2 = 13;
            min2 = 0;
        }
    }
    // set si la ligne est active
    // in: bool true/false
    //     int numero ligne
    // out: rien
    public void heureSetActive(boolean etat, int ligne){
        heure[ligne].active = etat;
    }

    // demande si la ligne est active
    // in: int numero ligne
    // out: true/false
    public boolean heureIsActive(int ligne){
        return heure[ligne].active;
    }

    // mettre l'heure a la ligne donner
    // in: int heure1
    //     int min1
    //     int heure2
    //     int min2
    //     int numero ligne
    // out: rien
    public void setHeure (int h1, int m1, int h2, int m2, int ligne){
        heure[ligne].heure1 = h1;
        heure[ligne].heure2 = h2;
        heure[ligne].min1 = m1;
        heure[ligne].min2 = m2;
    }

    // demander l'heure a la ligne donner
    // in: numero de la ligne
    // out: int[heure1, min1, heure2, min2]
    public int[] getheure(int ligne){
        int[] result = {heure[ligne].heure1, heure[ligne].min1, heure[ligne].heure2, heure[ligne].min2};
        return result;
    }


    // class pour nouvel objet jour
    private class Jour {
        //variable jour
        int jour1;
        int jour2;
        int eta;
        boolean active;

        public Jour() {
            active = false;
            jour1 = 1;
            jour2 = 2;
            eta = 1;
        }
    }

    // demande si le jour est active
    // in: int numero ligne
    // out: true/false
    public boolean jourIsActive(int ligne){
        return jour[ligne].active;
    }

    // set si le jour est active
    // in: bool true/false
    //     int numero ligne
    // out: rien
    public void jourSetActive(boolean etat, int ligne){
        jour[ligne].active = etat;
    }

    // mettre le jour a la ligne donner
    // in: int jour1
    //     int jour2
    //     int et/a
    //     int numero ligne
    // out: rien
    public void setjour (int j1, int j2, int eta, int ligne){
        jour[ligne].jour1 = j1;
        jour[ligne].jour2 = j2;
        jour[ligne].eta = eta;
    }

    // demander le jour a la ligne donner
    // in: numero de la ligne
    // out: int[jour1, jour2, eta]
    public int[] getjour(int ligne){
        int[] result = {jour[ligne].jour1, jour[ligne].jour2, jour[ligne].eta};
        return result;
    }

    // class pour nouvel objet mois
    private class Mois {
        //variable mois
        int date1;
        int mois1;
        int date2;
        int mois2;
        boolean active;

        public Mois() {
            active = false;
            date1 = 1;
            mois1 = 12;
            date2 = 1;
            mois2 = 6;

        }
    }

    // demande si le mois est active
    // in: rien
    // out: true/false
    public boolean moisIsActive(){
        return mois.active;
    }

    // set si le mois est active
    // in: bool true/false
    // out: rien
    public void moisSetActive(boolean etat){
        mois.active = etat;
    }

    // mettre le mois
    // in: int date1
    //     int mois1
    //     int date2
    //     int mois2
    // out: rien
    public void setmois (int d1, int m1, int d2, int m2){
        mois.date1 = d1;
        mois.mois1 = m1;
        mois.date2 = d2;
        mois.mois2 = m2;
    }

    // demander le mois
    // in: rien
    // out: int[jour1, jour2, eta]
    public int[] getjour(){
        int[] result = {mois.date1, mois.mois1, mois.date2, mois.mois2};
        return result;
    }

    // mettre fleche
    // in: int fleche
    // out: rien
    public void setFleche(int f){
        fleche = f;
    }

    // demander fleche
    // in: rien
    // out: int fleche
    public int getFleche(){
        return fleche;
    }

    // mettre image
    // in: int image
    // out: rien
    public void setImage(int i){
        image = i;
    }

    // demander image
    // in: rien
    // out: int image
    public int getImage(){
        return image;
    }
}

