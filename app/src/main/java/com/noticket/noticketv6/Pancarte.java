package com.noticket.noticketv6;

/**
 * Created by Nicolas Sabourin 1068459
 *            Tommy Côté  1056362
 *            Charles-Frédéric Amringer
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
    private Heure[] heure = new Heure[3];
    private Jour[] jour = new Jour[3];

    public Pancarte() {
        fleche = 1;
        image = 0;
        //crée les lignes heure
        hligne1 = new Heure();

        hligne2 = new Heure();
        hligne3 = new Heure();
        // initialise le array heure
        heure[0] = hligne1;
        heure[1] = hligne2;
        heure[2] = hligne3;
        //crée les lignes jour
        jligne1 = new Jour();

        jligne2 = new Jour();
        jligne3 = new Jour();
        // initialise le array jour
        jour[0] = jligne1;
        jour[1] = jligne2;
        jour[2] = jligne3;
        // crée le mois
        mois = new Mois();


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

    /*
    set si la ligne est active
    in: bool true/false
    int numero ligne
    out: rien
    */
    public void heureSetActive(boolean etat, int ligne){
        heure[ligne-1].active = etat;
    }

    /*
    demande si la ligne est active
    in: int numero ligne
    out: true/false
    */
    public boolean heureIsActive(int ligne){
        return heure[ligne-1].active;
    }

    /*
    mettre l'heure a la ligne donner
    in: int [heure1, min1, heure2, min2]
    int ligne
    out: rien
    */
    public void setHeure (int[] h, int ligne){
        heure[ligne-1].heure1 = h[0];
        heure[ligne-1].heure2 = h[2];
        heure[ligne-1].min1 = h[1];
        heure[ligne-1].min2 = h[3];
    }

    /*
    demander l'heure a la ligne donner
    in: numero de la ligne
    out: int[heure1, min1, heure2, min2]
    */
    public int[] getHeure(int ligne){
        int[] result = {heure[ligne-1].heure1, heure[ligne-1].min1, heure[ligne-1].heure2, heure[ligne-1].min2};
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

    /*
    demande si le jour est active
    in: int numero ligne
    out: true/false
    */
    public boolean jourIsActive(int ligne){
        return jour[ligne-1].active;
    }

    /*
    set si le jour est active
    in: bool true/false
    int numero ligne
    out: rien
    */
    public void jourSetActive(boolean etat, int ligne){
        jour[ligne-1].active = etat;
    }

    /*
    mettre le jour a la ligne donner
    in: int [jour1, jour2, et/a]
    int numero ligne
    out: rien
    */
    public void setJour(int[] j, int ligne){
        jour[ligne-1].jour1 = j[0];
        jour[ligne-1].jour2 = j[1];
        jour[ligne-1].eta = j[2];
    }

    /*
    demander le jour a la ligne donner
    in: numero de la ligne
    out: int[jour1, jour2, eta]
    */
    public int[] getJour(int ligne){
        int[] result = {jour[ligne-1].jour1, jour[ligne-1].jour2, jour[ligne-1].eta};
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

    /*
    demande si le mois est active
    in: rien
    out: true/false
    */
    public boolean moisIsActive(){
        return mois.active;
    }

    /*
    set si le mois est active
    in: bool true/false
    out: rien
    */
    public void moisSetActive(boolean etat){
        mois.active = etat;
    }

    /*
    mettre le mois
    in: int [date1, mois1, date2, mois2]
    out: rien
    */
    public void setmois (int[] m){
        mois.date1 = m[0];
        mois.mois1 = m[1];
        mois.date2 = m[2];
        mois.mois2 = m[3];
    }

    /*
    demander le mois
    in: rien
    out: int[date1, mois1, date2, mois2]
    */
    public int[] getMois(){
        int[] result = {mois.date1, mois.mois1, mois.date2, mois.mois2};
        return result;
    }

    /*
    mettre fleche
    in: int fleche
    out: rien
    */
    public void setFleche(int f){
        fleche = f;
    }

    /*
    demander fleche
    in: rien
    out: int fleche
    */
    public int getFleche(){
        return fleche;
    }

    /*
    mettre image
    in: int image
    out: rien
    */
    public void setImage(int i){
        image = i;
    }

    /*
    demander image
    in: rien
    out: int image
    */
    public int getImage(){
        return image;
    }
}

