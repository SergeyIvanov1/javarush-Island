package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

import java.util.concurrent.BlockingQueue;

public class LiveCycle {
    public static boolean marker = true;

    public void start() {
        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                Location currentLocation = Island.getInstance().getField()[i][j];

                BlockingQueue<Predator> predators = currentLocation.getPredators();
                for (Predator predator: predators) {
                    if (!predator.markerOfEndedCycle) {
                        predator.liveOneCycle();
                        System.out.println("+++++++++++++++++++++++++++++");
                    }
                }

                BlockingQueue<Herbivore> herbivores = currentLocation.getHerbivores();
                for (Herbivore herbivore: herbivores) {
                    if (!herbivore.markerOfEndedCycle) {
                        herbivore.liveOneCycle();
                        System.out.println("+++++++++++++++++++++++++++++");
                    }
                }
            }
        }

    }

    public void repeatCycle(int amount){
        for (int i = 0; i < amount; i++) {
            start();
        }
    }
}
