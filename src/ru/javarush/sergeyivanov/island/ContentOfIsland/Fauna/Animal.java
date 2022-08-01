package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

public abstract class Animal extends Nature {
    private double weight;
    private int maxCountIntoCell;
    private int speedMove;
    private double amountNeedFood;

    public Animal(double weight, int maxCountIntoCell, int speedMove, double amountNeedFood) {
        this.weight = weight;
        this.maxCountIntoCell = maxCountIntoCell;
        this.speedMove = speedMove;
        this.amountNeedFood = amountNeedFood;
    }

     public void eat(){
        if (this instanceof Predator){
            System.out.println(this.getClass().getSimpleName());
        } else if (this instanceof Herbivore) {
            System.out.println(this.getClass().getSimpleName());
        }
    }
    public abstract void multiply();
    public abstract void move();
    public abstract void die();
}
