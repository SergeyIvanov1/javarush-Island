package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Caterpillar;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;
import ru.javarush.sergeyivanov.island.Inicialization.ProcessorParam;
import ru.javarush.sergeyivanov.island.Main.DBProcessor;
import ru.javarush.sergeyivanov.island.Main.Statistic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Nature implements Runnable {
    static final Logger log = LogManager.getRootLogger();
    public boolean markerOfEndedCycle = false;
    String nameAnimal;
    public Map<Class<? extends Animal>, Integer> ration;
    protected double satiety;
    protected boolean isMale;
    protected boolean isNotMultiplied = true;
    protected ThreadLocalRandom random;
    ProcessorParam processor = new ProcessorParam();
    protected List<Class<? extends Animal>> listRation = new ArrayList<>();
    protected static final int ZERO = 0;
    private static final int BOUND = 100;
    private static final int MIN_INDEX = 0;
    private static final int INCLUDING_NUMBER = 1;
    private static final int OUT_BOUND = 1;
    private static final int HALF = 2;

    {
        random = ThreadLocalRandom.current();
        isMale = random.nextBoolean();
        nameAnimal = this.getClass().getSimpleName();
        ration = InitParameters.cacheRations.get(nameAnimal);
        satiety = amountNeedFood / HALF;
        fillListRation(listRation);
    }

    public void eat() {
        if (amountNeedFood == 0) {
            log.debug(nameAnimal + inputIndexes() + " eats very little, insignificant\n");
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
                satiety = DBProcessor.roundNumber(satiety + foodWeight);
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
        BlockingQueue<Nature> randomQueueOfFood = (BlockingQueue<Nature>) getLocation().getQueueOfNatureObjects(classFood);
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
                randomQueueOfFood.put(food);
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
            BlockingQueue<Animal> storageAnimals =
                    (BlockingQueue<Animal>) getLocation().getQueueOfNatureObjects(this.getClass());
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

    private int createChildren(int amount, BlockingQueue<Animal> storageAnimals) {
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

    private Optional<Animal> findPair(Animal animal, BlockingQueue<? extends Nature> animals) {
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
                (BlockingQueue<? extends Animal>) getLocation().getQueueOfNatureObjects(this.getClass());
        log.debug("\tCurrent location " + nameAnimal + "is " + inputIndexes());

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
        BlockingQueue<? extends Nature> storageNatureObjs = getLocation().getQueueOfNatureObjects(this.getClass());
        storageNatureObjs.remove(this);
    }

    public void updateParamForNewCycle() {
        isNotMultiplied = true;
        markerOfEndedCycle = false;
        satiety = DBProcessor.reduceSatiety(satiety, amountNeedFood);
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
        Map<Class<? extends Animal>, Integer> rationThisAnimal = InitParameters.cacheRations.get(className);

        for (Map.Entry<Class<? extends Animal>, Integer> entry : rationThisAnimal.entrySet()) {
            listRation.add(entry.getKey());
        }
    }

    private boolean foodIsPresentInLocation (){
        Map<Class<? extends Nature>, BlockingQueue<? extends Nature>> mapQueuesNatureObj = getLocation().getMapQueuesNatureObj();
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
