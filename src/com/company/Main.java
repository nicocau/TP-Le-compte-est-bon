package com.company;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static volatile String sol;
    private static volatile ArrayList<Thread> threads = new ArrayList<Thread>();
    private static volatile boolean fini = false;
    private static volatile int bestResult = -1;
    private static volatile int ntThradTerminer = 0;

    private static final int[] chifre = new int[]{2, 4, 6, 8, 15, 75};
    private static final int but = 987;
    private static final int nbIteration = 3000;
    private static final int ntThrad = 5;

    public static void main(String[] args) {
        ThreadGroup groupe = new ThreadGroup("groupe");
        for (int i = 0; i < Main.ntThrad; i++) {
            Thread p = new Thread(groupe, () -> {
                Solution solution = new Solution();
                solution.init(Main.chifre, Main.but);
                String solutionRes = solution.testDesSols(Main.nbIteration);
                Pattern pattern = Pattern.compile("[0-9]+$");
                Matcher matcher = pattern.matcher(solutionRes);
                matcher.find();
                int res = Integer.parseInt(matcher.group());
                if (res == Main.but) {
                    Main.sol = solutionRes;
                    Main.stopThrad();
                } else {
                    Main.compareSolution(solutionRes, res);
                }
            });
            Main.threads.add(p);
        }
        Main.threads.forEach(Thread::start);
        while (!Main.fini && groupe.activeCount()!=0) {
        }
        System.out.println(Main.sol);
    }

    private static void compareSolution(String solutionRes, int res) {
        if (Main.bestResult == -1) {
            Main.bestResult = res;
            Main.sol = solutionRes;
        } else {
            if (Math.abs(res - Main.but) < Math.abs(Main.bestResult - Main.but)) {
                Main.sol = solutionRes;
                Main.bestResult = res;
            }
        }
        if (Main.ntThrad == Main.ntThradTerminer){
            Main.stopThrad();
        }
    }

    private static void stopThrad() {
        Main.threads.forEach(Thread::interrupt);
        Main.fini = true;
    }
}
