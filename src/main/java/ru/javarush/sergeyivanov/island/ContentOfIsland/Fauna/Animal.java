package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Caterpillar;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.ProcessorParam;
import ru.javarush.sergeyivanov.island.Main.Calculations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Nature implements Runnable {
    static final Logger log = LogManager.getRootLogger();
    public boolean markerOfEndedCycle = false;
    protected Map<Class<? extends Nature>, Integer> ration = new ConcurrentHashMap<>();
    protected double satiety;
    protected boolean isMale;
    protected boolean isNotMultiplied = true;
    protected ThreadLocalRandom random;
    ProcessorParam processor = new ProcessorParam();
    String thisAnimal;
    private static final int BOUND = 100;
    private static final int MIN_INDEX = 0;
    private static final int INCLUDING_NUMBER = 1;
    private static final int OUT_BOUND = 1;

    {
        random = ThreadLocalRandom.current();
        isMale = random.nextBoolean();
        thisAnimal = this.getClass().getSimpleName();
    }

    public Animal(double weight, int maxCountIntoCell, int rangeMove, double amountNeedFood) {
        this.weight = weight;
        this.maxCountInsideCell = maxCountIntoCell;
        this.rangeMove = rangeMove;
        this.amountNeedFood = amountNeedFood;

        if (amountNeedFood != 0) {
            satiety = amountNeedFood / 2;
        } else {
            satiety = 0;
        }
    }

    public void eat(BlockingQueue<? extends Nature> natureObj) {
        if (amountNeedFood == 0) {
            log.debug(thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                    + " eats very little, insignificant\n");
        }
        if (satiety < amountNeedFood) {
            log.debug("Animal - " + thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                    + " wants EAT()");
            log.debug("\tsatiety" + thisAnimal + " = " + satiety + ", amountNeedFood = " + amountNeedFood);
        }

        while (satiety < amountNeedFood) {
            Optional<Double> food = findFood(natureObj);
            if (food.isPresent()) {
                double foodWeight = food.get();
                satiety = Calculations.roundNumber(satiety + foodWeight);
                log.debug("satiety = " + satiety + ", amountNeedFood = " + amountNeedFood);

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
                log.debug("\t" + thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                        + " found food - " + food.getClass().getSimpleName());
                boolean catchFood = random.nextInt(BOUND) < probability;

                if (catchFood) {
                    double foodWeight = food.getWeight();
                    log.debug("\tWeight of " + food.getClass().getSimpleName() +
                            " consists - " + foodWeight + " kg");

                    if (animals.remove(food)) {
                        log.debug("\t" + food.getClass().getSimpleName() + " eaten and deleted from queue\n");
                    }
                    return Optional.of(foodWeight);
                } else {
                    log.debug("\t" + thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                            + " couldn't to catch food - " + food.getClass().getSimpleName() + "\n");
                    return Optional.empty();
                }
            }
        }
        log.debug("\tInside location finished food from ration " + thisAnimal
                + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]" + "\n");
        return Optional.empty();
    }

    public void multiply() {
        if (!this.isNotMultiplied){
            log.debug(thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                    + " has already multiplied during this cycle\n");
        }

        if (this.isNotMultiplied) {
            BlockingQueue<Animal> storageAnimals =
                    (BlockingQueue<Animal>) getLocation().getStorageNature(this.getClass());
            log.debug("Animal - " + thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                    + " wants MULTIPLY()");

            Optional<Animal> pair = findPair(this, storageAnimals);
            if (pair.isPresent()) {
                Animal partner = pair.get();
                try {
                    Constructor<? extends Animal> constructor = this.getClass().getConstructor();
                    Animal child;
                    try {
                        child = constructor.newInstance();
                        log.debug("\tchild " + child.getClass().getSimpleName() + " was born\n");
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                    child.setLocation(getLocation());
                    child.setIndexLineField(indexLineField);
                    child.setIndexColumnField(IndexColumnField);

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
        log.debug("\t" + thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                + " is looking for a pair");

        for (Nature pair : animals) {
            if (animal.getClass() == pair.getClass()
                    && animal.isMale != ((Animal) pair).isMale
                    && ((Animal) pair).isNotMultiplied) {

                Animal result = (Animal) pair;
                log.debug("\t" + result.getClass().getSimpleName() + " is found - "
                        + result.getClass().getSimpleName());
                return Optional.of(result);
            }
        }
        log.debug("\tThe pair is not found inside this location\n");
        return Optional.empty();
    }

    public void changeLocation() {
        if (rangeMove == 0) {
            log.debug(thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                    + " can't change location\n");
            return;
        }
        log.debug("Animal - " + thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                + " can to CHANGE LOCATION()");

        int width = Island.getWidthField();
        int height = Island.getHeightField();

        int boundField = rangeMove + INCLUDING_NUMBER;
        int movesCountInLine = random.nextInt(MIN_INDEX, boundField);
        int movesCountInColumn = rangeMove - movesCountInLine;

        int newIndexLine = calculateNewIndex(indexLineField, width, movesCountInLine);
        int newIndexColumn;
        if (movesCountInColumn > 0) {
            newIndexColumn = calculateNewIndex(IndexColumnField, height, movesCountInColumn);
        } else {
            newIndexColumn = IndexColumnField;
        }
        BlockingQueue<? extends Animal> storageCurrentAnimal =
                (BlockingQueue<? extends Animal>) getLocation().getStorageNature(this.getClass());
        log.debug("\tCurrent location " + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]");

        if (storageCurrentAnimal.remove(this)) {
            processor.transferObjToNewLocation(newIndexLine, newIndexColumn, this);
            log.debug("\tNew location[" + newIndexLine + "][" + newIndexColumn + "]\n");
        }
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
        markerOfEndedCycle = false;
        satiety = Calculations.reduceSatiety(satiety, amountNeedFood);

        if (satiety <= 0 && this.getClass() != Caterpillar.class){
            die();
            log.debug(thisAnimal + "[" + getIndexLineField() + "][" + getIndexColumnField() + "]"
                    + " has hungry death. " + "Satiety = " + satiety);
        }
    }

}
