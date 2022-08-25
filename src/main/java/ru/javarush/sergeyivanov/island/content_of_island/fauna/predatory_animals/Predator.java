package ru.javarush.sergeyivanov.island.content_of_island.fauna.predatory_animals;

import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;

public abstract class Predator extends Animal {

    @Override
    public void run() {
        eat();
        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}
