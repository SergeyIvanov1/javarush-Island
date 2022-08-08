package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public abstract class Animal extends Nature {

    protected Map<Class<Nature>, Integer> ration;
    protected double satiety;
    protected boolean isMale;
    protected boolean isNotMultiplied = true;

    public Animal(double weight, int maxCountIntoCell, int speedMove, double amountNeedFood) {
        this.weight = weight;
        this.maxCountIntoCell = maxCountIntoCell;
        this.speedMove = speedMove;
        this.amountNeedFood = amountNeedFood;

        Random random = new Random();
        satiety = random.nextDouble(amountNeedFood/2, amountNeedFood);
        isMale = random.nextBoolean();
    }

    public abstract void eat(BlockingQueue<? extends Animal> natureObj);
    public void multiply() throws InstantiationException, IllegalAccessException {
        if (this.isNotMultiplied){
            BlockingQueue<? extends Nature> animals = getLocation().getTargetQueue(this.getClass());

            Optional<? extends Nature> pair = findPair(this, animals);
            if (pair.isPresent()){
                Animal partner = (Animal) pair.get();
                try {
                    Constructor<? extends Nature> constructor = this.getClass().getConstructor();
                    Nature child = constructor.newInstance();
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

    protected Optional<? extends Nature> findPair(Animal animal, BlockingQueue<? extends Nature> animals)  {
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
    public abstract Optional<Double> findFood(BlockingQueue<? extends Animal> animals);


}
