package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.Main.LiveCycle;

public abstract class Herbivore extends Animal {

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

    @Override
    public void run() {
        eat(getLocation().getPlants());
        if (satiety < amountNeedFood) {
            eat(getLocation().getHerbivores());
        }

        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}
