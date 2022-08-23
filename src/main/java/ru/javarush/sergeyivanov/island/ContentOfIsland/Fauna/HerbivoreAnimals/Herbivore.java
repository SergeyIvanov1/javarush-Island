package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Grass;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;
import ru.javarush.sergeyivanov.island.Main.LiveCycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {



//    public void liveOneCycle() {
//        eat(getLocation().getPlants());
//        if (satiety < amountNeedFood) {
//            eat(getLocation().getHerbivores());
//        }
//
//        multiply();
//        changeLocation();
//
//        markerOfEndedCycle = true;
//    }

    @Override
    public void run() {
        eat();

//        eat(getLocation().getPlants());
//        if (satiety < amountNeedFood) {
//            eat(getLocation().getHerbivores());
//        }

        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}
