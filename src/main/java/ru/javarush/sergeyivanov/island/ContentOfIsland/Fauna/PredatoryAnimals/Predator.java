package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

public abstract class Predator extends Animal {

    @Override
    public void run() {
        eat();

        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}

