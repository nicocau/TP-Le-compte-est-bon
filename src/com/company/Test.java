package com.company;

public class Test {
    static void testOpe() {
        Operateur op = Operateur.SOUSTRACTION;
        int a=6, b=5;
        boolean ok = op.test.apply(a,b);
        System.out.println("faisabilite de " + a + " " + op.signe + " " + b + " = " + ok);
        if (ok)
            System.out.println("resultat de " + a + " " + op.signe + " " + b + " = " + op.calcul.apply(a,b));;
    }
    public static void main(String[] args) {
        testOpe();
    }
}