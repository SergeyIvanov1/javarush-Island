package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

public abstract class Herbivore extends Animal {
    public boolean markerOfEndedCycle = false;

    public Herbivore(double weight, int maxCountIntoCell, int rangeMove, double amountNeedFood) {
        super(weight, maxCountIntoCell, rangeMove, amountNeedFood);
    }

    public void liveOneCycle() {
        eat(getLocation().getPlants());
        if (satiety < amountNeedFood) {
            eat(getLocation().getHerbivores());
        }

        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}
