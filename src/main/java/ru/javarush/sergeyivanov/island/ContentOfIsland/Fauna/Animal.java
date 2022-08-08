package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Nature {

    protected Map<Class<? extends Nature>, Integer> ration;
    protected double satiety;
    protected boolean isMale;
    protected boolean isNotMultiplied = true;
    private static final int BOUND = 100;

    {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        isMale = random.nextBoolean();
        if (amountNeedFood != 0){
            satiety = random.nextDouble(amountNeedFood/2, amountNeedFood);
        } else {
            satiety = 0;
        }
    }

    public Animal(double weight, int maxCountIntoCell, int speedMove, double amountNeedFood) {
        this.weight = weight;
        this.maxCountIntoCell = maxCountIntoCell;
        this.speedMove = speedMove;
        this.amountNeedFood = amountNeedFood;
    }

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

    public Optional<Double> findFood(BlockingQueue<? extends Animal> animals) {
        for (Animal food: animals) {
            if (ration.containsKey(food.getClass())){
                int probability = ration.get(food.getClass());
                boolean catchFood = ThreadLocalRandom.current().nextInt(BOUND) < probability;
                double foodWeight = food.getWeight();
                if (catchFood && animals.remove(food)) return Optional.of(foodWeight);
            }
        }
        return Optional.empty();
    }

    public void multiply() throws InstantiationException, IllegalAccessException {
        if (this.isNotMultiplied){
            BlockingQueue<Animal> animals = (BlockingQueue<Animal>) getLocation().getTargetQueue(this.getClass());

            Optional<Animal> pair = findPair(this, animals);
            if (pair.isPresent()){
                Animal partner = pair.get();
                try {
                    Constructor<? extends Animal> constructor = this.getClass().getConstructor();
                    Animal child = constructor.newInstance();
                    child.setLocation(getLocation());
                    animals.add(child);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                partner.isNotMultiplied = false;
                this.isNotMultiplied = false;
            }
        }
    }

    protected Optional<Animal> findPair(Animal animal, BlockingQueue<? extends Nature> animals)  {
        for (Nature pair: animals) {
            if (animal.getClass() == pair.getClass()
                    && animal.isMale != ((Animal) pair).isMale
                    && ((Animal) pair).isNotMultiplied) {

                Animal result = (Animal) pair;
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }
    public abstract void move();
    public abstract void die();
}
