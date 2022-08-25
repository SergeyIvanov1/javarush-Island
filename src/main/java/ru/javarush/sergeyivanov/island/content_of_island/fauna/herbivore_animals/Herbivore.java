package ru.javarush.sergeyivanov.island.content_of_island.fauna.herbivore_animals;

import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;

public abstract class Herbivore extends Animal {

    @Override
    public void run() {
        eat();
        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}
