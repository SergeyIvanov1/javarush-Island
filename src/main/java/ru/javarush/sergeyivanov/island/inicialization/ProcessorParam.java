package ru.javarush.sergeyivanov.island.inicialization;

import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ProcessorParam {
    private static final int ONE_HUNDRED_PERCENTS = 100;
    private static final double PERCENTAGE_OF_SATIETY_REDUCTION = 25;
    private static final int TEN = 10;
    private static final int DEGREE = 3;
    Parameters parameters;

    public ProcessorParam(Parameters parameters) {
        this.parameters = parameters;
    }

    public double reduceSatiety(double satiety, double amountNeedFood) {
        double hunger = PERCENTAGE_OF_SATIETY_REDUCTION * amountNeedFood / ONE_HUNDRED_PERCENTS;
        return roundResult(satiety - hunger);
    }

    public double roundResult(double calculate) {
        double scale = Math.pow(TEN, DEGREE);
        return Math.floor(calculate * scale) / scale;
    }

    public void transferObjToNewLocation(int newIndexLine, int newIndexColumn, Nature object) {
        Location nextLocation = parameters.getFieldOfIsland()[newIndexLine][newIndexColumn];
        Queue< Nature> nextStorageObj = (Queue<Nature>) nextLocation.getQueueOfNatureObjects(object.getClass());

        object.setLocation(nextLocation);
        object.setIndexLineField(newIndexLine);
        object.setIndexColumnField(newIndexColumn);

        nextStorageObj.add(object);
    }

    public void inputCellsToTheField() {
        for (int i = 0; i < parameters.getIsland().getWidthOfField(); i++) {
            for (int j = 0; j < parameters.getIsland().getHeightOfField(); j++) {
                parameters.getFieldOfIsland()[i][j] = new Location(parameters);
            }
        }
    }

    List<Queue<? extends Nature>> createListQueuesByObjects(Map<Class<? extends Nature>, Integer> map) {
        List<Queue<? extends Nature>> listQueues = new ArrayList<>();

        for (Map.Entry<Class<? extends Nature>, Integer> pair : map.entrySet()) {
            Queue<Nature> queue = new LinkedList<>();

            var classNatureObj = pair.getKey();
            Integer amountNatureObj = pair.getValue();
            try {
                Queue<? extends Nature> natures = fillQueueObjsAndGet(queue, classNatureObj, amountNatureObj);
                System.out.println("Queue of " + classNatureObj.getSimpleName() + " equals - " + natures.size());
                listQueues.add(natures);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Size listQueues of Natures = " + listQueues.size());
        return listQueues;
    }

    void allocateObjsIntoField(List<Queue<? extends Nature>> listQueues) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (Queue<? extends Nature> queue : listQueues) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int randomLine = random.nextInt(0, parameters.getIsland().getWidthOfField());
                int randomColumn = random.nextInt(0, parameters.getIsland().getHeightOfField());

                Nature object = queue.poll();
                transferObjToNewLocation(randomLine, randomColumn, object);
            }
        }
    }

    private Queue<? extends Nature> fillQueueObjsAndGet(
            Queue<Nature> queue, Class<? extends Nature> classNatureObj, int amountObj)
            throws InstantiationException, IllegalAccessException
    {
        for (int i = 0; i < amountObj; i++) {
            try {
//                if (Plant.class.isAssignableFrom(classNatureObj)) {
//                    Constructor<? extends Nature> constructor = classNatureObj.getConstructor();
//                    Nature natureObj = constructor.newInstance();
//                    queue.add(natureObj);
//                } else {
                    Constructor<? extends Nature> constructor = classNatureObj.getConstructor(Parameters.class);
                    Nature natureObj = constructor.newInstance(parameters);
                    queue.add(natureObj);
//                }
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return queue;
    }

//    private void fillListRation(List<Class<? extends Animal>> listRation) {
//        String className = this.getClass().getSimpleName();
//        Map<Class<? extends Animal>, Integer> rationThisAnimal = Parameters.cacheRations.get(className);
//
//        for (Map.Entry<Class<? extends Animal>, Integer> entry : rationThisAnimal.entrySet()) {
//            listRation.add(entry.getKey());
//        }
//    }
}
