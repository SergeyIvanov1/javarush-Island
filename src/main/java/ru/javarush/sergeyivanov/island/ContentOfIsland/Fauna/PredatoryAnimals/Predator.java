package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public abstract class Predator extends Animal {
    private static final int BOUND = 100;
    public Predator(int weight, int maxCountIntoCell, int speedMove, int amountNeedFood) {
        super(weight, maxCountIntoCell, speedMove, amountNeedFood);
    }

    @Override
    public void eat(BlockingQueue<? extends Animal> natureObj) {
        while (satiety < amountNeedFood) {
            Optional<Double> food = findFood(natureObj);
            if (food.isPresent()) {
                double foodWeight = food.get();
                satiety += foodWeight;
                if (satiety > amountNeedFood) {
                    satiety = amountNeedFood;
                    break;
                }
            } else {
                break;
            }
        }
    }

    @Override
    public Optional<Double> findFood(BlockingQueue<? extends Animal> animals) {
        for (Animal food: animals) {
            if (ration.containsKey(food.getClass())){
                int probability = ration.get(food.getClass());
                boolean catchFood = new Random().nextInt(BOUND) < probability;
                double foodWeight = food.getWeight();
               if (catchFood && animals.remove(food)) return Optional.of(foodWeight);
            }
        }
        return Optional.empty();
    }
}
