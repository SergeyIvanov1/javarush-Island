package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

public class Statistic {
    public static void printParametersOfField(){
        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                System.out.println("Location[" + i +"][" + j +"]");
                System.out.println("Herbivores queue. initSize = "
                        + Island.getInstance().getField()[i][j].getHerbivores().size());
                System.out.println("Predators queue. initSize = "
                        + Island.getInstance().getField()[i][j].getPredators().size());
                System.out.println("Plants queue. initSize = "
                        + Island.getInstance().getField()[i][j].getPlants().size());
                System.out.println("____________________");
            }
        }
    }
}
