package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;
import ru.javarush.sergeyivanov.island.Inicialization.ProcessorParam;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Nature {
    public boolean markerOfEndedCycle = false;

    protected Map<Class<? extends Nature>, Integer> ration = new ConcurrentHashMap<>();
    protected double satiety;
    protected boolean isMale;
    protected boolean isNotMultiplied = true;
    protected ThreadLocalRandom random;
    ProcessorParam processor = new ProcessorParam();
    private static final int BOUND = 100;
    private static final int MIN_INDEX = 0;
    private static final int INCLUDING_NUMBER = 1;
    private static final int OUT_BOUND = 1;

    {
        random = ThreadLocalRandom.current();
        isMale = random.nextBoolean();
        if (amountNeedFood != 0) {
            satiety = random.nextDouble(amountNeedFood / 2, amountNeedFood);
        } else {
            satiety = 0;
        }
    }

    public Animal(double weight, int maxCountIntoCell, int rangeMove, double amountNeedFood) {
        this.weight = weight;
        this.maxCountInsideCell = maxCountIntoCell;
        this.rangeMove = rangeMove;
        this.amountNeedFood = amountNeedFood;
    }

    public void eat(BlockingQueue<? extends Nature> natureObj) {
        System.out.print("\n");
        if (amountNeedFood == 0) {
            System.out.println(this.getClass().getSimpleName() + " eats very little, insignificant");
        }
        if (satiety < amountNeedFood) {
            System.out.println("Animal " + this.getClass().getSimpleName() + " wants EAT()");
            System.out.println("\tsatiety = " + satiety + ", amountNeedFood = " + amountNeedFood);
        }

        while (satiety < amountNeedFood) {
            Optional<Double> food = findFood(natureObj);
            if (food.isPresent()) {
                double foodWeight = food.get();
                satiety += foodWeight;
                System.out.println("satiety = " + satiety + ", amountNeedFood = " + amountNeedFood);

                if (satiety > amountNeedFood) {
                    satiety = amountNeedFood;
                    break;
                }
            } else {
                break;
            }
        }
    }

    private Optional<Double> findFood(BlockingQueue<? extends Nature> animals) {
        for (Nature food : animals) {
            if (ration.containsKey(food.getClass())) {
                int probability = ration.get(food.getClass());

                System.out.println("\t" + this.getClass().getSimpleName() + " found food - " + food.getClass().getSimpleName());

                boolean catchFood = random.nextInt(BOUND) < probability;
                if (catchFood) {
                    double foodWeight = food.getWeight();

                    System.out.println("\tWeight of " + food.getClass().getSimpleName() +
                            " consists - " + foodWeight + " kg");

                    if (animals.remove(food)) {
                        System.out.println("\t" + food.getClass().getSimpleName() + " eaten and deleted from queue");
                    }
                    return Optional.of(foodWeight);
                } else {
                    System.out.println("\t" + this.getClass().getSimpleName() + " can't to catch food - " + food.getClass().getSimpleName());
                    return Optional.empty();
                }
            }
        }
        System.out.println("\tInside location finished food from ration " + this.getClass().getSimpleName());
        return Optional.empty();
    }

    public void multiply() {
        System.out.print("\n");

        if (!this.isNotMultiplied){
            System.out.println(this.getClass().getSimpleName() + " has already multiplied during this cycle");
        }

        if (this.isNotMultiplied) {
            BlockingQueue<Animal> storageAnimals =
                    (BlockingQueue<Animal>) getLocation().getStorageNature(this.getClass());

            System.out.println("Animal " + this.getClass().getSimpleName() + " wants MULTIPLY()");


            Optional<Animal> pair = findPair(this, storageAnimals);
            if (pair.isPresent()) {
                Animal partner = pair.get();
                try {
                    Constructor<? extends Animal> constructor = this.getClass().getConstructor();
                    Animal child;
                    try {
                        child = constructor.newInstance();
                        System.out.println(" \tchild " + child.getClass().getSimpleName() + " was born");

                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                    child.setLocation(getLocation());
                    child.setIndexLineField(getIndexLineField());
                    child.setIndexColumnField(getIndexColumnField());

                    storageAnimals.add(child);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                partner.isNotMultiplied = false;
                this.isNotMultiplied = false;
            }
        }
    }

    private Optional<Animal> findPair(Animal animal, BlockingQueue<? extends Nature> animals) {
        System.out.println("\t" + this.getClass().getSimpleName() + " is looking for a pair");

        for (Nature pair : animals) {
            if (animal.getClass() == pair.getClass()
                    && animal.isMale != ((Animal) pair).isMale
                    && ((Animal) pair).isNotMultiplied) {

                Animal result = (Animal) pair;

                System.out.println("\t" + result.getClass().getSimpleName() + " is found - " + result.getClass().getSimpleName());

                return Optional.of(result);
            }
        }
        System.out.println("\tThe pair is not found inside this location");
        return Optional.empty();
    }

    public void changeLocation() {
        System.out.print("\n");

        if (rangeMove == 0) {
            System.out.println(this.getClass().getSimpleName() + " can't change location");
            System.out.print("\n");
            return;
        }
        System.out.println("Animal " + this.getClass().getSimpleName() + " can to CHANGE lOCATION()");

        int width = Island.getWidthField();
        int height = Island.getHeightField();

        int boundField = rangeMove + INCLUDING_NUMBER;
        int movesCountInLine = random.nextInt(MIN_INDEX, boundField);
        int movesCountInColumn = rangeMove - movesCountInLine;

        int newIndexLine = calculateNewIndex(getIndexLineField(), width, movesCountInLine);
        int newIndexColumn;
        if (movesCountInColumn > 0) {
            newIndexColumn = calculateNewIndex(getIndexColumnField(), height, movesCountInColumn);
        } else {
            newIndexColumn = getIndexColumnField();
        }
        BlockingQueue<? extends Animal> storageCurrentAnimal =
                (BlockingQueue<? extends Animal>) getLocation().getStorageNature(this.getClass());
        System.out.println("\tCurrent location [" + getIndexLineField() + "][" + getIndexColumnField()+ "]");

        if (storageCurrentAnimal.remove(this)) {
            processor.transferObjToNewLocation(newIndexLine, newIndexColumn, this);
            System.out.println("\tNew location [" + newIndexLine + "][" + newIndexColumn + "]");
        }
        System.out.print("\n");
    }

    private int calculateNewIndex(int oldIndex, int length, int movesCount) {

        boolean moveIsBack = random.nextBoolean();

        int newIndex;
        if (moveIsBack) {
            newIndex = oldIndex - movesCount;
            newIndex = Math.max(newIndex, MIN_INDEX);
        } else {
            newIndex = oldIndex + movesCount;
            newIndex = (newIndex >= length) ? length - OUT_BOUND : newIndex;
        }
        return newIndex;
    }

    public boolean die(){
        return getLocation().getStorageNature(this.getClass()).remove(this);
    }


    public void updateParamNewDay(){
        isNotMultiplied = true;
        satiety -= amountNeedFood/4;
        markerOfEndedCycle = false;
    }
}
