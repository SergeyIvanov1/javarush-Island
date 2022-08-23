package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

public abstract class Predator extends Animal {

//    public void liveOneCycle() {
//        eat(getLocation().getHerbivores());
//        if (satiety < amountNeedFood) {
//            eat(getLocation().getPredators());
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

        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}

