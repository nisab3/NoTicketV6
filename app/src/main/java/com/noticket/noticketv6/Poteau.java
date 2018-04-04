package com.noticket.noticketv6;
import java.util.Date;
/**
 * Created by tommy on 2018-03-24.
 */

public class Poteau {
    // Les 5 objets Pancarte
    private Pancarte p1, p2, p3, p4,  p5;
    // État des 5 pancartes
    private boolean p1_active = false, p2_active = false, p3_active = false, p4_active = false, p5_active = false;
    // L'alarme en int
    private int alarme;
    // Rue One Way
    private boolean oneWay;
    // Position sur la rue (1, 2, 3 ou 4)
    private int position;

    // Constructeur
    Poteau() {
        p1 = new Pancarte();
        p2 = new Pancarte();
        p3 = new Pancarte();
        p4 = new Pancarte();
        p5 = new Pancarte();
    }

    // Donne le numéro de la Pancarte désirée et
    // retourne l'objet Pancarte
    public Pancarte get_pancarte(int numero) {
        switch (numero) {
            case 1: return p1;
            case 2: return p2;
            case 3: return p3;
            case 4: return p4;
            case 5: return p5;
            default: return p5;
        }
    }

//    public void set_pancarte(Pancarte pancarte, int numero){
//        switch(numero) {
//            case 1: p1 = pancarte; break;
//            case 2: p2 = pancarte; break;
//            case 3: p3 = pancarte; break;
//            case 4: p4 = pancarte; break;
//            case 5: p5 = pancarte; break;
//            default: break; // rien
//        }
//    }
    // donne l'etat de la pancarte désiré et
    // retourne un true/false
    public boolean get_pancarte_active(int numero){
        switch (numero) {
            case 1: return p1_active;
            case 2: return p2_active;
            case 3: return p3_active;
            case 4: return p4_active;
            case 5: return p5_active;
            default: return p5_active;
        }
    }

    // Imput: numéro de la Pancarte et vrai si on veut l'activer
    public void set_pancarte_active(int numero, boolean active) {
        switch (numero) {
            case 1: p1_active = active;
                    break;
            case 2: p2_active = active;
                    break;
            case 3: p3_active = active;
                    break;
            case 4: p4_active = active;
                    break;
            case 5: p5_active = active;
            default:
        }
    }

    // Retourne le int alarme
    public int get_alarme(){
        return  alarme;
    }

    // Input: la nouvelle valeur de l'alarme
    public void set_alarme(int nouvelle_alarme) {
        alarme = nouvelle_alarme;
    }

    // Retourne le booléen oneWay
    public boolean get_oneWay(){
        return oneWay;
    }

    // Input: la nouvelle valeur de oneWay
    public void set_oneWay(boolean nouveau) { oneWay = nouveau; }

    // Retourne le int position
    public int get_position(){
        return  position;
    }

    // Input: la nouvelle valeur de la position
    public void set_position(int nouvelle_position) { position = nouvelle_position; }

    // Analyse les 5 pancartes en fonction du
    // type de flèche: 0 pas de flèche
    //                 1 <-
    //                 2 ->
    //                 3 <->
    // et retourne:  -1 si impossible de se stationner
    //               -2 si toujours possible de se stationner
    //               int entre 0000 et 2359 l'heure de départ pour éviter une contravention
    // TODO pas d'input à la fonction analyse
    public int analyse(int fleche) {
        Date dateActuelle = new Date();
        String maintenant = dateActuelle.toString();

        // Date actuelle
        String mois = maintenant.substring(4, 7);
        String jour = maintenant.substring(0, 3);
//        int heure = Integer.parseInt(maintenant.substring(11, 13));
//        int minute = Integer.parseInt(maintenant.substring(14, 16));
        String heure = maintenant.substring(11, 13);
        String minute = maintenant.substring(14, 16);
        String[] now = {mois, jour, heure, minute};

        // Array de 48h en bloc de 30min initialisé à true
        boolean[] horaire = new boolean[96];
        for(int i=0;i<horaire.length;i++)
            horaire[i] = true;

        // Heure actuelle en terme de l'indice dans le tableau horaire
//        int horaireNow = heure_indice(heure, minute);

        // Modifie le tableau horaire selon les 5 pancartes
        traitement(p1, p1_active, position, horaire, now);
        traitement(p2, p2_active, position, horaire, now);
        traitement(p3, p3_active, position, horaire, now);
        traitement(p4, p4_active, position, horaire, now);
        traitement(p5, p5_active, position, horaire, now);

        // TODO interprete le tableau horaire et retourne l'heure de départ




        return fleche; //temporaire il falait juste que ca retourne un int
    }

    // Retourne booléen vrai si la pancarte s'applique dans ce contexte,
    //                          considérant la position de la voiture.
    private boolean applicable(Pancarte p, boolean p_active, int pos) {
        if (p_active==false){
            return  false;
        }
        if (pos==1 && p.getFleche()!=2) {
            return true;
        }
        if (pos==2 && p.getFleche()!=1) {
            return true;
        }
        if (pos==3 && p.getFleche()!=1) {
            return true;
        }
        if (pos==4 && p.getFleche()!=2) {
            return true;
        }
        return false;
    }

    // Retourne booléen vrai si la pancarte s'applique à ce mois
    private boolean applicableMois(Pancarte p, String[] n) {
        // Si il y a des mois sur la pancarte
        if (p.moisIsActive()) {
            if (p.getMois()[3] >= p.getMois()[1]) {
                if (Integer.parseInt(n[0]) >= p.getMois()[1]
                        && Integer.parseInt(n[0]) <= p.getMois()[3]
                        && Integer.parseInt(n[1]) >= p.getMois()[0]
                        && Integer.parseInt(n[1]) <= p.getMois()[2]) {
                    return true;
                }else {
                    return false;
                }
            }else {
                if ((Integer.parseInt(n[0]) >= p.getMois()[1]
                        || Integer.parseInt(n[0]) <= p.getMois()[3])
                        && Integer.parseInt(n[1]) >= p.getMois()[0]
                        && Integer.parseInt(n[1]) <= p.getMois()[2]) {
                    return true;
                }else {
                    return false;
                }
            }
        }
        // Sinon
        else {
            return true;
        }
    }

    // TODO est ce que les jour vont de 0=dim à 6=sam ???
    // TODO et=0   à=1  ???
    // TODO correspondance entre jour 1à3 et heure 1à3 ???
    // Retourne booléen vrai si la pancarte s'applique à ce jour
    private boolean applicableJour(Pancarte p, String[] n) {
        // Si il y a des jours sur la pancarte
        if (p.moisIsActive()) {

            // TEMPORAIRE
            return true;


        }
        // Sinon
        else {
            return true;
        }
    }

    // Fait les modification dans horaire
    private void traitement(Pancarte p, boolean p_active, int pos, boolean[] h, String[] n) {
        if (applicable(p, p_active, pos) && applicableMois(p, n) && applicableJour(p, n)) {
            // TODO joue dans le tableau horaire





        }else return;
    }

    // Convertie l'heure en indice dans le tableau horaire
    private int heure_indice(int h, int m) {
        int resultat = h;
        if (m==30) {
            resultat += 1;
        }
        return resultat;
    }

    // Convertie l'indice dans le tableau horaire en heure
    private int[] indice_heure(int i) {
        int[] resultat = {i/2, 0};
        if (i%2 == 1) {
            resultat[1] = 30;
        }
        return resultat;
    }
}
