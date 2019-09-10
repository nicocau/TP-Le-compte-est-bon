package com.company;

public class Main {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.init(new int[]{2, 4, 6, 8, 15, 75},987);
        System.out.println(solution.testDesSols(10000));
    }
}
