package ru.javarush.sergeyivanov.island.content_of_island.fauna;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.content_of_island.Fauna;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.content_of_island.exceptions.CreateOfNatureObjectException;
import ru.javarush.sergeyivanov.island.content_of_island.exceptions.ValueInvalidException;
import ru.javarush.sergeyivanov.island.content_of_island.fauna.herbivore_animals.Caterpillar;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.content_of_island.flora.Plant;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;
import ru.javarush.sergeyivanov.island.user_comunication.Statistic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Nature implements Runnable, Fauna {
    static final Logger log = LogManager.getRootLogger();
    private static final int BOUND = 100;
    private static final int MIN_INDEX = 0;
    private static final int MAKE_INCLUSIVE = 1;
    private static final int OUT_BOUND = 1;
    private static final int HALF = 2;
    protected boolean markerOfEndedCycle = false;
    protected boolean isNotMultiplied = true;
    protected ThreadLocalRandom random = ThreadLocalRandom.current();
    protected String nameAnimal = this.getClass().getSimpleName();
    protected Map<Class<? extends Nature>, Integer> mapProbabilities;
    protected List<Class<? extends Nature>> listRation;
    protected double amountNeedFood;
    protected int amountChildren;
    protected int amountCyclesLife;
    protected double satiety;
    protected int rangeMove;
    protected boolean isMale;


    public Animal(Parameters parameters) {
        this.parameters = parameters;
        isMale = random.nextBoolean();
        initFieldsClass();
        this.satiety = amountNeedFood / HALF;
    }

    @Override
    public void run() {
        eat();
        multiply();
        changeLocation();
        markerOfEndedCycle = true;
    }

    public void eat() {
        if (amountNeedFood == 0) {
            log.debug(nameAnimal + getIndexes() + " is eating very little, values don't change\n");
        }
        if (satiety < amountNeedFood) {
            log.debug("Animal - " + nameAnimal + getIndexes() + " wants EAT()");
            log.debug("\tsatiety" + nameAnimal + " = " + satiety + ", amountNeedFood = " + amountNeedFood);
        }

        while (satiety < amountNeedFood) {
            Optional<Double> food;
            food = findFood();

            if (food.isPresent()) {
                double foodWeight = food.get();
                satiety = parameters.getProcessor().roundResult(satiety + foodWeight);
                log.debug("satiety = " + satiety + ", amountNeedFood = " + amountNeedFood);

                if (satiety > amountNeedFood) {
                    satiety = amountNeedFood;
                    break;
                }
            } else {
                if (!foodIsPresentInLocation()) {
                    log.debug("Food for " + nameAnimal + " finished in current location");
                    break;
                }
            }
        }
    }

    public void multiply() throws ValueInvalidException {
        if (!this.isNotMultiplied) {
            log.debug(nameAnimal + getIndexes() + " has already multiplied during this cycle\n");
        }

        if (this.isNotMultiplied) {
            Queue<Animal> storageAnimalsThisType =
                    (Queue<Animal>) getLocation().getQueueOfNatureObjects(this.getClass());
            log.debug("Animal - " + nameAnimal + getIndexes() + " wants MULTIPLY()");

            Optional<Animal> pair = findPair(this, storageAnimalsThisType);
            if (pair.isPresent()) {
                Animal partner = pair.get();
                try {
                    int amountChildren = random.nextInt( this.amountChildren + OUT_BOUND);
                    int result = createChildren(amountChildren, storageAnimalsThisType);
                    log.debug("Amount born animals - " + nameAnimal + getIndexes() + " consists " + result);
                } catch (IllegalArgumentException ex) {
                    throw new ValueInvalidException("Error, value of amount children " +
                            nameAnimal + " is not positive. ", ex);
                }

                partner.isNotMultiplied = false;
                this.isNotMultiplied = false;
            }
        }
    }

    public void changeLocation() throws ValueInvalidException {
        if (rangeMove == 0) {
            log.debug(nameAnimal + getIndexes() + " can't change location\n");
            return;
        }
        log.debug("Animal - " + nameAnimal + getIndexes() + " can to CHANGE LOCATION()");

        try {
            int widthOfField = parameters.getIsland().getWidthOfField();
            int heightOfField = parameters.getIsland().getHeightOfField();

            int movesCountInLine = random.nextInt(rangeMove + MAKE_INCLUSIVE);
            int movesCountInColumn = rangeMove - movesCountInLine;

            int newIndexLine = calculateNewIndex(indexLineField, widthOfField, movesCountInLine);
            int newIndexColumn;

            if (movesCountInColumn > 0) {
                newIndexColumn = calculateNewIndex(IndexColumnField, heightOfField, movesCountInColumn);
            } else {
                newIndexColumn = IndexColumnField;
            }
            Queue<? extends Animal> storageCurrentAnimal =
                    (Queue<? extends Animal>) getLocation().getQueueOfNatureObjects(this.getClass());
            log.debug("\tCurrent location " + nameAnimal + " is " + getIndexes());

            Location newLocation = parameters.getIsland().getField()[newIndexLine][newIndexColumn];
            int sizeQueue = newLocation.getQueueOfNatureObjects(this.getClass()).size();

            if (sizeQueue < maxObjInCell && storageCurrentAnimal.remove(this)) {
                parameters.getProcessor().transferObjToNewLocation(newIndexLine, newIndexColumn, this);
                log.debug("\tNew location[" + newIndexLine + "][" + newIndexColumn + "]\n");
            }
        } catch (IllegalArgumentException ex) {
                throw new ValueInvalidException("Error, value of amount moves " +
                        nameAnimal + " is not positive. ", ex);
        }
    }

    public void die() {
        Queue<? extends Nature> storageNatureObjs = getLocation().getQueueOfNatureObjects(this.getClass());
        storageNatureObjs.remove(this);
    }

    public void updateParamForAnimal() {
        isNotMultiplied = true;
        markerOfEndedCycle = false;
        satiety = parameters.getProcessor().reduceSatiety(satiety, amountNeedFood);
        amountCyclesLife--;

        if (satiety <= 0 && this.getClass() != Caterpillar.class) {
            die();
            log.debug(nameAnimal + getIndexes() + " has hungry death. Satiety = " + satiety);
            Statistic.amountHungryDeath.incrementAndGet();
        }

        if (amountCyclesLife == 0) {
            die();
            log.debug(nameAnimal + getIndexes() + " died of old age");
            Statistic.amountDeathsOfOldAge.incrementAndGet();
        }
    }

    private Optional<Double> findFood() throws ValueInvalidException{
        try {
            int randomIndex = random.nextInt(listRation.size());
            Class<? extends Nature> classFood = listRation.get(randomIndex);
            String nameFood = classFood.getSimpleName();
            Queue<Nature> randomQueueOfFood = (Queue<Nature>) getLocation().getQueueOfNatureObjects(classFood);
            Nature food = randomQueueOfFood.poll();

            if (food != null) {
                int probability = mapProbabilities.get(classFood);
                log.debug("\t" + nameAnimal + getIndexes() + " found food - " + nameFood);
                boolean catchFood = random.nextInt(BOUND) < probability;

                if (catchFood) {
                    double foodWeight = food.getWeight();
                    log.debug("\tWeight of found " + nameFood + " consists - " + foodWeight + " kg");
                    log.debug("\t" + nameFood + " eaten and deleted from queue\n");

                    if (Plant.class.isAssignableFrom(classFood)) {
                        Statistic.amountEatenPlants.incrementAndGet();
                    } else {
                        Statistic.amountEatenAnimals.incrementAndGet();
                    }
                    return Optional.of(foodWeight);
                } else {
                    randomQueueOfFood.add(food);
                    log.debug("\t" + nameAnimal + getIndexes() + " couldn't to catch food - " + nameFood + "\n");
                    return Optional.empty();
                }
            } else {
                log.debug("\tInside queue finished " + nameFood + " from ration " + nameAnimal + getIndexes() + "\n");
                return Optional.empty();
            }
        } catch (IllegalArgumentException ex) {
            throw new ValueInvalidException("Error, size of list ration " + nameAnimal +
                    " equals 0", ex);
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

    private int createChildren(int amountChildren, Queue<Animal> storageAnimalsThisType)
            throws CreateOfNatureObjectException {
        int amountBornAnimals = 0;
        for (int i = 0; i < amountChildren; i++) {

            if (storageAnimalsThisType.size() >= maxObjInCell) {
                break;
            } else {
                try {
                    Constructor<? extends Animal> constructor = this.getClass().getConstructor(Parameters.class);
                    Animal child;
                        child = constructor.newInstance(parameters);
                        log.debug("\tchild " + child.getClass().getSimpleName() + " was born\n");
                        Statistic.amountBorn.incrementAndGet();

                    child.setLocation(getLocation());
                    child.setIndexLineField(indexLineField);
                    child.setIndexColumnField(IndexColumnField);

                    storageAnimalsThisType.add(child);
                    amountBornAnimals++;
                } catch (NoSuchMethodException | InvocationTargetException
                         | InstantiationException | IllegalAccessException e) {
                    throw new CreateOfNatureObjectException("Error by creating of baby animal " + nameAnimal, e);
                }
            }
        }
        return amountBornAnimals;
    }

    private Optional<Animal> findPair(Animal animal, Queue<? extends Nature> storageAnimalsThisType) {
        log.debug("\t" + nameAnimal + getIndexes() + " is looking for a pair");

        for (Nature pair : storageAnimalsThisType) {
            if (animal.isMale != ((Animal) pair).isMale && ((Animal) pair).isNotMultiplied) {
                Animal result = (Animal) pair;
                log.debug("\t" + nameAnimal + " found pair - another " + result.getClass().getSimpleName());
                return Optional.of(result);
            }
        }
        log.debug("\tThe pair is not found inside this location\n");
        return Optional.empty();
    }

    private boolean foodIsPresentInLocation() {
        Map<Class<? extends Nature>, Queue<? extends Nature>> mapQueuesNatureObj = getLocation().getMapQueuesNatureObj();
        for (Class<? extends Nature> classFood : listRation) {
            if (!mapQueuesNatureObj.get(classFood).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private String getIndexes() {
        return "[" + getIndexLineField() + "][" + getIndexColumnField() + "]";
    }

    private void initFieldsClass() {
        mapProbabilities = parameters.getMapProbabilities(nameAnimal);
        listRation = parameters.getListRation(nameAnimal);
        Map<String, Number> cacheSettings = parameters.getCacheSettings().get(nameAnimal);

        weight = (double) cacheSettings.get("weight");
        maxObjInCell = (int) cacheSettings.get("maxObjInCell");
        rangeMove = (int) cacheSettings.get("rangeMove");
        amountNeedFood = (double) cacheSettings.get("amountNeedFood");
        amountChildren = (int) cacheSettings.get("amountChildren");
        amountCyclesLife = (int) cacheSettings.get("amountCycleLive");
    }
}
