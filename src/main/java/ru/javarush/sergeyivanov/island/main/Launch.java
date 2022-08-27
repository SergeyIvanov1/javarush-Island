package ru.javarush.sergeyivanov.island.main;

import ru.javarush.sergeyivanov.island.inicialization.Parameters;

public class Launch {

    public static final int AMOUNT = 3;

    public void start() {
        Parameters parameters = new Parameters();
        parameters.printSettings();
        parameters.printRations();
        Statistic.printParametersOfField();

        LiveCycle cycle = new LiveCycle();
        cycle.repeatCycle(AMOUNT);
    }
}
