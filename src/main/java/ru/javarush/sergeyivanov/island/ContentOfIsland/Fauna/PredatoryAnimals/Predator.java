package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

public abstract class Predator extends Animal {
    public boolean markerOfEndedCycle = false;
    public Predator(int weight, int maxCountIntoCell, int rangeMove, int amountNeedFood) {
        super(weight, maxCountIntoCell, rangeMove, amountNeedFood);
    }

    public void liveOneCycle() {
        eat(getLocation().getHerbivores());
        if (satiety < amountNeedFood) {
            eat(getLocation().getPredators());
        }

        multiply();
        changeLocation();

        markerOfEndedCycle = true;
    }
}
