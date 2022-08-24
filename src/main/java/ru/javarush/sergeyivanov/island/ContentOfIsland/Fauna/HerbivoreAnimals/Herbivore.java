package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

public abstract class Herbivore extends Animal {

    @Override
    public void run() {
        eat();
        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}
