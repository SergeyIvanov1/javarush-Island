package ru.javarush.sergeyivanov.island.Main;

public class Calculations {

    private static final double PERCENTAGE_OF_SATIETY_REDUCTION = 25;
    private static final int ONE_HUNDRED_PERCENTS = 100;

    public static double reduceSatiety(double satiety, double amountNeedFood){
        double hunger = PERCENTAGE_OF_SATIETY_REDUCTION * amountNeedFood / ONE_HUNDRED_PERCENTS;
        return roundNumber(satiety - hunger);
    }

    public static double roundNumber(double calculate){
        double scale = Math.pow(10, 3);
        return Math.floor(calculate * scale) / scale;
    }

}
