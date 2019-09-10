package com.company;

import java.util.*;

public class Solution extends SolutionAbstraite {
    @Override
    void init(int[] _nombres, int _but) {
        this.but = _but;
        this.nombres = _nombres;
        this.valeursLibres = new Stack<Integer>();
        for (int i : nombres) {
            this.valeursLibres.add(i);
        }
        this.etapes = new StringBuilder();
    }

    @Override
    void reinit() {
        this.valeursLibres.removeAllElements();
        for (int i : nombres) {
            this.valeursLibres.add(i);
        }
        this.etapes.setLength(0);
    }

    @Override
    int calcule() {
        int res = -1;
        for (int i = 0; i < 5; i++) {
            this.etapes.append("Etape ").append(i+1).append("\n");
            this.etapes.append("tab = ").append(this.valeursLibres).append("\n");
            Collections.shuffle(this.valeursLibres);
            int nb1 = this.valeursLibres.pop();
            Collections.shuffle(this.valeursLibres);
            int nb2 = this.valeursLibres.pop();
            this.etapes.append(" tirage de a et b (a=").append(nb1).append(", b=").append(nb2).append(")\n");
            this.etapes.append("tab = ").append(this.valeursLibres).append("\n");

            Operateur operateur = this.choixOperateurApplicable(nb1, nb2);

            res = operateur.calcul.apply(nb1, nb2);
            this.valeursLibres.add(operateur.calcul.apply(nb1, nb2));
            this.etapes.append("tirage de op (x) ->  tab = ").append(this.valeursLibres).append("\n");
        }
        return res;
    }

    @Override
    Operateur choixOperateurApplicable(int nb1, int nb2) {
        ArrayList<Operateur> liste = new ArrayList<Operateur>();
        for (Operateur operateur : Operateur.values()) {
            if (operateur.test.apply(nb1, nb2)) {
                liste.add(operateur);
            }
        }
        Random random = new Random();
        return liste.get(random.nextInt(liste.size()));
    }

    @Override
    String testDesSols(int nbCalculsMax) {
        StringBuilder res = new StringBuilder();
        res.append("A partir de ").append(this.nombres).append(", tenter de trouver ").append(this.but).append("\n");
        String restmp = "";
        int nbProche = -1;
        int i;
        for (i = 0; i < nbCalculsMax; i++) {
            int calcul = this.calcule();
            if (calcul == this.but) {
                nbProche = calcul;
                restmp = this.etapes.toString();
                break;
            } else {
                if (nbProche == -1) {
                    restmp = this.etapes.toString();
                    nbProche = calcul;
                } else if (Math.abs(calcul - this.but) < Math.abs(nbProche - this.but)) {
                    restmp = this.etapes.toString();
                    nbProche = calcul;
                }
            }
            this.reinit();
        }
        res.append(restmp);
        res.append("La solution donne : ").append(nbProche).append(" au bout de ").append(i).append(" iteration(s)");
        return res.toString();
    }

    public Solution() {
    }
}
