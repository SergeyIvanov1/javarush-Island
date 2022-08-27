package ru.javarush.sergeyivanov.island.content_of_island.fauna;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.content_of_island.fauna.herbivore_animals.Caterpillar;
import ru.javarush.sergeyivanov.island.content_of_island.field.Island;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.content_of_island.flora.Plant;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;
import ru.javarush.sergeyivanov.island.inicialization.ProcessorParam;
import ru.javarush.sergeyivanov.island.main.Statistic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Nature implements Runnable {
    static final Logger log = LogManager.getRootLogger();
    private static final int ZERO = 0;
    private static final int BOUND = 100;
    private static final int MIN_INDEX = 0;
    private static final int INCLUDING_NUMBER = 1;
    private static final int OUT_BOUND = 1;
    private static final int HALF = 2;
    public boolean markerOfEndedCycle = false;
    public Map<Class<? extends Animal>, Integer> ration;
    protected boolean isMale;
    protected boolean isNotMultiplied = true;
    protected ThreadLocalRandom random = ThreadLocalRandom.current();
    protected List<Class<? extends Animal>> listRation = new ArrayList<>();
    protected double satiety = amountNeedFood/HALF;
    ProcessorParam processor = new ProcessorParam();

    public Animal() {
        isMale = random.nextBoolean();
        ration = Parameters.cacheRations.get(nameAnimal);
        fillListRation(listRation);
    }

    public void eat() {
        if (amountNeedFood == 0) {
            log.debug(nameAnimal + inputIndexes() + " is eating very little, values don't change\n");
        }
        if (satiety < amountNeedFood) {
            log.debug("Animal - " + nameAnimal + inputIndexes() + " wants EAT()");
            log.debug("\tsatiety" + nameAnimal + " = " + satiety + ", amountNeedFood = " + amountNeedFood);
        }

        while (satiety < amountNeedFood) {
            Optional<Double> food;
            try {
                food = findFood();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (food.isPresent()) {
                double foodWeight = food.get();
                satiety = ProcessorParam.roundNumber(satiety + foodWeight);
                log.debug("satiety = " + satiety + ", amountNeedFood = " + amountNeedFood);

                if (satiety > amountNeedFood) {
                    satiety = amountNeedFood;
                    break;
                }
            } else {
                if (!foodIsPresentInLocation()) {
                    break;
                }
            }
        }
    }

    private Optional<Double> findFood() throws InterruptedException {
        int randomIndex = random.nextInt(ZERO, listRation.size());
        Class<? extends Animal> classFood = listRation.get(randomIndex);
        String nameFood = classFood.getSimpleName();
        Queue<Nature> randomQueueOfFood = (Queue<Nature>) getLocation().getQueueOfNatureObjects(classFood);
        Nature food = randomQueueOfFood.poll();

        if (food != null) {
            int probability = ration.get(classFood);
            log.debug("\t" + nameAnimal + inputIndexes() + " found food - " + nameFood);
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
                log.debug("\t" + nameAnimal + inputIndexes() + " couldn't to catch food - " + nameFood + "\n");
                return Optional.empty();
            }
        } else {
            log.debug("\tInside queue finished " + nameFood + " from ration " + nameAnimal + inputIndexes() + "\n");
            return Optional.empty();
        }
    }

    public void multiply() {
        if (!this.isNotMultiplied) {
            log.debug(nameAnimal + inputIndexes() + " has already multiplied during this cycle\n");
        }

        if (this.isNotMultiplied) {
            Queue<Animal> storageAnimals =
                    (Queue<Animal>) getLocation().getQueueOfNatureObjects(this.getClass());
            log.debug("Animal - " + nameAnimal + inputIndexes() +" wants MULTIPLY()");

            Optional<Animal> pair = findPair(this, storageAnimals);
            if (pair.isPresent()) {
                Animal partner = pair.get();
                int amount = random.nextInt(ZERO, amountChildren + OUT_BOUND);
                int result = createChildren(amount, storageAnimals);

                log.debug("Amount born animals - " + nameAnimal + inputIndexes() +" consists " + result);
                partner.isNotMultiplied = false;
                this.isNotMultiplied = false;
            }
        }
    }

    private int createChildren(int amount, Queue<Animal> storageAnimals) {
        int amountBornAnimals = 0;
        for (int i = 0; i < amount; i++) {

            if (storageAnimals.size() >= maxObjInCell){
                break;
            } else {
                try {
                    Constructor<? extends Animal> constructor = this.getClass().getConstructor();
                    Animal child;
                    try {
                        child = constructor.newInstance();
                        log.debug("\tchild " + child.getClass().getSimpleName() + " was born\n");
                        Statistic.amountBorn.incrementAndGet();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                    child.setLocation(getLocation());
                    child.setIndexLineField(indexLineField);
                    child.setIndexColumnField(IndexColumnField);

                    storageAnimals.add(child);
                    amountBornAnimals++;
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return amountBornAnimals;
    }

    private Optional<Animal> findPair(Animal animal, Queue<? extends Nature> animals) {
        log.debug("\t" + nameAnimal + inputIndexes() + " is looking for a pair");

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
            log.debug(nameAnimal + inputIndexes() + " can't change location\n");
            return;
        }
        log.debug("Animal - " + nameAnimal + inputIndexes() + " can to CHANGE LOCATION()");

        int width = Island.getInstance().getWidthField();
        int height = Island.getInstance().getHeightField();

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
        Queue<? extends Animal> storageCurrentAnimal =
                (Queue<? extends Animal>) getLocation().getQueueOfNatureObjects(this.getClass());
        log.debug("\tCurrent location " + nameAnimal + " is " + inputIndexes());

        Location newLocation = Island.getInstance().getField()[newIndexLine][newIndexColumn];
        int sizeQueue = newLocation.getQueueOfNatureObjects(this.getClass()).size();

        if (sizeQueue < maxObjInCell && storageCurrentAnimal.remove(this)) {
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

    public void die() {
        Queue<? extends Nature> storageNatureObjs = getLocation().getQueueOfNatureObjects(this.getClass());
        storageNatureObjs.remove(this);
    }

    public void updateParamForNewCycle() {
        isNotMultiplied = true;
        markerOfEndedCycle = false;
        satiety = ProcessorParam.reduceSatiety(satiety, amountNeedFood);
        amountCyclesLife--;

        if (satiety <= 0 && this.getClass() != Caterpillar.class) {
            die();
            log.debug(nameAnimal + inputIndexes() + " has hungry death. Satiety = " + satiety);
            Statistic.amountHungryDeath.incrementAndGet();
        }

        if (amountCyclesLife == 0) {
            die();
            log.debug(nameAnimal + inputIndexes() + " died of old age");
            Statistic.amountDeathsOfOldAge.incrementAndGet();
        }
    }

    private void fillListRation(List<Class<? extends Animal>> listRation) {
        String className = this.getClass().getSimpleName();
        Map<Class<? extends Animal>, Integer> rationThisAnimal = Parameters.cacheRations.get(className);

        for (Map.Entry<Class<? extends Animal>, Integer> entry : rationThisAnimal.entrySet()) {
            listRation.add(entry.getKey());
        }
    }

    private boolean foodIsPresentInLocation (){
        Map<Class<? extends Nature>, Queue<? extends Nature>> mapQueuesNatureObj = getLocation().getMapQueuesNatureObj();
        for (Class<? extends Nature> classFood: listRation) {
            if (!mapQueuesNatureObj.get(classFood).isEmpty()){
                return true;
            }
        }
        return false;
    }

    private String inputIndexes(){
        return "[" + getIndexLineField() + "][" + getIndexColumnField() + "]";
    }
}
