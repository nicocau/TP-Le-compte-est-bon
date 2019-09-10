package com.company;

import java.util.function.BiFunction;

public enum Operateur {
    ADDITION("+", (a, b) -> (int) a + b, (a, b) -> (true)),
    SOUSTRACTION("-", (a, b) -> (int) a - b, (a, b) -> (a > b)),
    MULTIPLICATION("*", (a, b) -> (int) a * b, (a, b) -> (a != 1 && b != 1)),
    DIVISION("/", (a, b) -> (int) a / b, (a, b) -> (a > b && b != 1 && a % b == 0));

    public BiFunction<Integer, Integer, Integer> calcul;
    public BiFunction<Integer, Integer, Boolean> test;
    public String signe;

    Operateur(String _signe, BiFunction<Integer, Integer, Integer> _calcul, BiFunction<Integer, Integer, Boolean> _test) {
        this.calcul = _calcul;
        this.test = _test;
        this.signe = _signe;
    }

    public BiFunction<Integer, Integer, Integer> getCalcul() {
        return calcul;
    }

    public void setCalcul(BiFunction<Integer, Integer, Integer> calcul) {
        this.calcul = calcul;
    }

    public BiFunction<Integer, Integer, Boolean> getTest() {
        return test;
    }

    public void setTest(BiFunction<Integer, Integer, Boolean> test) {
        this.test = test;
    }

    public String getSigne() {
        return signe;
    }

    public void setSigne(String signe) {
        this.signe = signe;
    }

    @Override
    public String toString() {
        return this.signe;
    }
}
