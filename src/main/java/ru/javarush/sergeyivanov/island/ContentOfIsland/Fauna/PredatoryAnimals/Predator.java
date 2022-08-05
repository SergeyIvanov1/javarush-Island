package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public abstract class Predator extends Animal {
    public Predator(int weight, int maxCountIntoCell, int speedMove, int amountNeedFood) {
        super(weight, maxCountIntoCell, speedMove, amountNeedFood);
    }

    @Override
    public void eat() {
        while (satiety < amountNeedFood) {
            Optional<Double> optional = findFood(getLocation().getHerbivores());
            if (optional.isPresent()) {
                double food = optional.get();
            }
        }
    }

    @Override
    public Optional<Double> findFood(BlockingQueue<? extends Animal> animals) {
        for (Animal food: animals) {
            if (ration.containsKey(food.getClass())){
                int probability = ration.get(food.getClass());
                boolean catchFood = new Random().nextInt(100) < probability;
                double kiloFood = food.getWeight();
               if (catchFood && animals.remove(food)) return Optional.of(kiloFood);
            }
        }
        return Optional.empty();
    }

}
