package com.noticket.noticketv6;
import java.util.Date;
/**
 * Created by tommy on 2018-03-24.
 */

public class Poteau {
    // Les 5 objets Pancarte
    private Pancarte p1, p2, p3, p4,  p5;
    // État des 5 pancartes
    private boolean p1_active, p2_active, p3_active, p4_active, p5_active = false;
    // L'alarme en int
    private int alarme;

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
            case 2: p2_active = active;
            case 3: p3_active = active;
            case 4: p4_active = active;
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

    // Analyse les 5 pancartes en fonction du
    // type de flèche: 0 pas de flèche
    //                 1 <-
    //                 2 ->
    //                 3 <->
    // et retourne:  -1 si impossible de se stationner
    //               -2 si toujours possible de se stationner
    //               int entre 0000 et 2359 l'heure de départ pour éviter une contravention
    public int analyse(int fleche) {
        Date now = new Date();
        String maintenant = now.toString();

        String mois = maintenant.substring(4, 7);
        String jour = maintenant.substring(0, 3);
        int heure = Integer.parseInt(maintenant.substring(11, 13));
        int minute = Integer.parseInt(maintenant.substring(14, 16));

        if (p1_active) {

        }

        return fleche; //temporaire il falait juste que ca retourne un int
    }
}
