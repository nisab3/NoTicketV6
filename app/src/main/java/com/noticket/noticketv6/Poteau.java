package com.noticket.noticketv6;

import java.util.Date;

/**
 * Created by Nicolas Sabourin 1068459
 *            Tommy Côté  1056362
 *            Charles-Frédéric Amringer
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

    // Prend le mois en String et le retourne en int
    private int moisActuel(String mois) {
        switch (mois){
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case "Apr": return 4;
            case "May": return 5;
            case "Jun": return 6;
            case "Jul": return 7;
            case "Aug": return 8;
            case "Sep": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            case "Dec": return 12;
            default: return 1;
        }
    }

    // Prend le jour en String et le retourne en int
    private int jourActuel(String mois) {
        switch (mois){
            case "Mon": return 1;
            case "Tue": return 2;
            case "Wed": return 3;
            case "Thu": return 4;
            case "Fri": return 5;
            case "Sat": return 6;
            case "Sun": return 7;
            default: return 1;
        }
    }

    // Analyse les 5 pancartes
    // et retourne:  [-1,-1] si impossible de se stationner
    //               [heure, minute] l'heure de départ pour éviter une
    //                               contravention, à partir de minuit le jour actuel
    //
    // Les jour vont de 1=lun à 7=dim  0 =aucune journée
    // et=2   à=1  0=rien
    // correspondance entre jour 1à3 et heure 1à3 Toutes les heures s'appilquent à toutes les journées
    // type de flèche: 0 pas de flèche
    //                 1 <-
    //                 2 ->
    //                 3 <->
    public int[] analyse() {
        Date dateActuelle = new Date();
        String maintenant = dateActuelle.toString();

        // Date actuelle
        int mois = moisActuel(maintenant.substring(4, 7));
        int jour = jourActuel(maintenant.substring(0, 3));
        int heure = Integer.parseInt(maintenant.substring(11, 13));
        int minute = Integer.parseInt(maintenant.substring(14, 16));
        int[] now = {mois, jour, heure, minute};
        // TODO vérifier le changement de mois
        int[] tomorrow = {mois, jour+1, heure, minute};
        int[] resultat = {24, 0, 0, 1};

        // 2 Arrays de 24h en bloc de 30min initialisé à true
        boolean[] jour1 = new boolean[48];
        boolean[] jour2 = new boolean[48];
        for(int i=0;i<jour1.length;i++) {
            jour1[i] = true;
            jour2[i] = true;
        }

        // Modifie le tableau horaire selon les 5 pancartes
        traitement(p1, p1_active, position, jour1, now);
        traitement(p2, p2_active, position, jour1, now);
        traitement(p3, p3_active, position, jour1, now);
        traitement(p4, p4_active, position, jour1, now);
        traitement(p5, p5_active, position, jour1, now);
        traitement(p1, p1_active, position, jour2, tomorrow);
        traitement(p2, p2_active, position, jour2, tomorrow);
        traitement(p3, p3_active, position, jour2, tomorrow);
        traitement(p4, p4_active, position, jour2, tomorrow);
        traitement(p5, p5_active, position, jour2, tomorrow);

        // Merge les 2 talbeau d'horaire
        boolean[] horaire = mergeTab(jour1, jour2);

        // Heure actuelle en terme de l'indice dans le tableau horaire
        int horaireNow = heure_indice(heure, minute);
        int i = horaireNow;

        // Si le stationnement est actuellement possible
        if (horaire[horaireNow]) {
            while (horaire[i]) {
                i++;
                if (i==horaire.length) {
                    resultat[0] = 24;
                    resultat[1] = 0;
                    resultat[2] = 1;
                    resultat[3] = 1;
                    return resultat;
                }
            }

            if (indice_heure(i)[0] >=24) {
                resultat[0] = indice_heure(i)[0]-24;
                resultat[3] = 1;
            }
            else {
                resultat[0] = indice_heure(i)[0];
                resultat[3] = 0;
            }
            resultat[1] = indice_heure(i)[1];
            resultat[2] = 1;
            return resultat;
        } else
        // Si le stationnement est impossible actuellement
            while (!horaire[i]) {
                i++;
                if (i==horaire.length) {
                    resultat[0] = 24;
                    resultat[1] = 0;
                    resultat[2] = 0;
                    resultat[3] = 1;
                    return resultat;
                }
            }
            if (indice_heure(i)[0] >=24) {
                resultat[0] = indice_heure(i)[0]-24;
                resultat[3] = 1;
            }
            else {
                resultat[0] = indice_heure(i)[0];
                resultat[3] = 0;
            }
            resultat[1] = indice_heure(i)[1];
            resultat[2] = 0;
            return resultat;
    }

    // Retourne booléen vrai si la pancarte s'applique dans ce contexte,
    //                          considérant la position de la voiture.
    // type de flèche: 0 pas de flèche
    //                 1 <-
    //                 2 <->
    //                 3 ->
    private boolean applicable(Pancarte p, boolean p_active, int pos) {
        if (!p_active){
            return false;
        }
        if (pos==1 && p.getFleche()!=3) {
            return true;
        }
        if (pos==2 && p.getFleche()!=1) {
            return true;
        }
        if (pos==3 && p.getFleche()!=1) {
            return true;
        }
        if (pos==4 && p.getFleche()!=3) {
            return true;
        }
        return false;
    }

    // Retourne booléen vrai si la pancarte s'applique à ce mois
    private boolean applicableMois(Pancarte p, int[] n) {
        // Si il y a des mois sur la pancarte
        if (p.moisIsActive()) {
            if (p.getMois()[3] >= p.getMois()[1]) {
                return n[0] >= p.getMois()[1]
                        && n[0] <= p.getMois()[3]
                        && n[1] >= p.getMois()[0]
                        && n[1] <= p.getMois()[2];
            }else {
                return (n[0] <= p.getMois()[1]
                        || n[0] >= p.getMois()[3])
                        && (n[1] <= p.getMois()[0]
                        || n[1] >= p.getMois()[2]);
            }
        }
        // Sinon
        else {
            return true;
        }
    }

    // Retourne booléen vrai si la pancarte s'applique à ce jour
    private boolean applicableJour(Pancarte p, int[] n, int j) {
        // S'il y a des jours sur la ligne j de la pancarte
        if (p.jourIsActive(j)) {
            // S'il y a un seul jour sur la ligne
            if (p.getJour(j)[1]==0) {
                // Est-ce que ce jour est le jour actuel?
                return p.getJour(j)[0]==n[1];
            }

            // Si on a jour1 à jour2
            if (p.getJour(j)[2]==1) {
                if (p.getJour(j)[0] <= p.getJour(j)[1]) {
                    return n[1] >= p.getJour(j)[0] && n[1] <= p.getJour(j)[1];
                } else {
                    return n[1] <= p.getJour(j)[0] || n[1] >= p.getJour(j)[1];
                }
            }

            // Si on a jour1 et jour2
            if (p.getJour(j)[2]==2 || p.getJour(j)[2]==0) {
                // Est-ce qu'un de ces 2 jours est le jour actuel?
                return p.getJour(j)[0]==n[1] || p.getJour(j)[1]==n[1];
            }

            // Si rien ne s'applique
            return false;
        }
        // Sinon
        else {
            return false;
        }
    }

    // Fait les modifications dans horaire
    private void traitement(Pancarte p, boolean p_active, int pos, boolean[] h, int[] n) {
        // Si la flèche et les mois sur la pancarte s'applique
        if (applicable(p, p_active, pos) && applicableMois(p, n)){
            // Si les jours sur la pancarte s'apliquent
            if ((!p.jourIsActive(1) && !p.jourIsActive(2) && !p.jourIsActive(3)) ||
                (applicableJour(p, n, 1) || applicableJour(p, n, 2) || applicableJour(p, n, 3))){
                // Met les valeurs à false dans le tableau horaire pour les heures sur le panneau
                traitementHeure(p, h, 1);
                traitementHeure(p, h, 2);
                traitementHeure(p, h, 3);
                // S'il n'y a pas d'heures sur la pancarte, l'interdiction s'applique tout le temps
                if (!p.heureIsActive(1) && !p.heureIsActive(2) && !p.heureIsActive(3)) {
                    for(int i=0;i<h.length;i++) {
                        h[i] = false;
                    }
                }
            }
        }
    }

    // Met les valeurs à false dans le tableau horaire pour les heures sur le panneau
    private void traitementHeure(Pancarte p, boolean[] h, int ligne) {
        if (p.heureIsActive(ligne)) {
            int heure1 = p.getHeure(ligne)[0];
            int minute1 = p.getHeure(ligne)[1];
            int heure2 = p.getHeure(ligne)[2];
            int minute2 = p.getHeure(ligne)[3];

            if (minute1==1) minute1=30;
            if (minute2==1) minute2=30;

            int indiceHeureDepart = heure_indice(heure1, minute1);
            int indiceHeureFin = heure_indice(heure2, minute2);

            // Si les heures sont normales. Ex: 12h-15h30
            if (indiceHeureDepart<=indiceHeureFin) {
                // Met false dans le tableau horaire pour toutes les heures sur le panneau
                for(int i=indiceHeureDepart; i<indiceHeureFin; i++){
                    h[i] = false;
                }
            }
            // Sinon, les heures dépassent dans les autres jours. Ex: 23h à 4h
            else {
                for(int i=0; i<indiceHeureFin; i++){
                    h[i] = false;
                }
                for(int i=indiceHeureDepart; i<h.length; i++){
                    h[i] = false;
                }
            }
        }
    }

    // Convertie l'heure en indice dans le tableau horaire
    private int heure_indice(int h, int m) {
        int resultat = 2*h;
        if (m>=30) {
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

    // Merge 2 arrays et retourne le résultat
    private boolean[] mergeTab(boolean[] tab1, boolean[] tab2) {
        boolean[] resultat = new boolean[tab1.length+tab2.length];
        for(int i=0;i<tab1.length;i++)
            resultat[i] = tab1[i];
        for(int i=0;i<tab2.length;i++)
            resultat[i+tab1.length] = tab2[i];
        return resultat;
    }
}
