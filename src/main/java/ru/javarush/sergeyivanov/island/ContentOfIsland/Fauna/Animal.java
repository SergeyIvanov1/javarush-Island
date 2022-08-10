package ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna;

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

    private Optional<Double> findFood(BlockingQueue<? extends Animal> animals) {
        for (Animal food : animals) {
            if (ration.containsKey(food.getClass())) {
                int probability = ration.get(food.getClass());
                boolean catchFood = random.nextInt(BOUND) < probability;
                double foodWeight = food.getWeight();
                if (catchFood && animals.remove(food)) return Optional.of(foodWeight);
            }
        }
        return Optional.empty();
    }

    public void multiply() throws InstantiationException, IllegalAccessException {
        if (this.isNotMultiplied) {
            BlockingQueue<? extends Nature> storageAnimals = processor.getStorageNature(getLocation(), this.getClass());

            Optional<Animal> pair = findPair(this, storageAnimals);
            if (pair.isPresent()) {
                Animal partner = pair.get();
                try {
                    Constructor<? extends Animal> constructor = this.getClass().getConstructor();
                    Animal child = constructor.newInstance();

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
        for (Nature pair : animals) {
            if (animal.getClass() == pair.getClass()
                    && animal.isMale != ((Animal) pair).isMale
                    && ((Animal) pair).isNotMultiplied) {

                Animal result = (Animal) pair;
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    public void move() {
        if (rangeMove == 0) {
            return;
        }

        int width = InitParameters.getWidthField();
        int height = InitParameters.getHeightField();

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
        BlockingQueue<Animal> storageCurrentAnimal = (BlockingQueue<Animal>) processor.getStorageNature(getLocation(), this.getClass());
        if (storageCurrentAnimal.remove(this)) {
            processor.transferObjToNewLocation(newIndexLine, newIndexColumn, this);
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

    public abstract void die();
}
