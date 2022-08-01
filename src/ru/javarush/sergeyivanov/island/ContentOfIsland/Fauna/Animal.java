package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.List;

public abstract class Animal extends Nature {

    List<Nature> ration;
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
         System.out.println(this.getClass().getSimpleName());
    }
    public abstract void multiply();
    public abstract void move();
    public abstract void die();
//    public abstract void findFood();
}
