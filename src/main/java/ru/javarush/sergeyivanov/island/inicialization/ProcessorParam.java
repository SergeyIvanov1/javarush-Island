package ru.javarush.sergeyivanov.island.inicialization;

import ru.javarush.sergeyivanov.island.content_of_island.field.Island;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ProcessorParam {

    public void transferObjToNewLocation(int newIndexLine, int newIndexColumn, Nature object) {
        Location nextLocation = Island.getInstance().getField()[newIndexLine][newIndexColumn];
        Queue< Nature> nextStorageObj = (Queue<Nature>) nextLocation.getQueueOfNatureObjects(object.getClass());

        object.setLocation(nextLocation);
        object.setIndexLineField(newIndexLine);
        object.setIndexColumnField(newIndexColumn);

        nextStorageObj.add(object);
    }

    List<Queue<? extends Nature>> createListQueuesByObjects(Map<Class<? extends Nature>, Integer> map) {
        List<Queue<? extends Nature>> listQueues = new ArrayList<>();

        for (Map.Entry<Class<? extends Nature>, Integer> pair : map.entrySet()) {
            Queue<Nature> queue = new LinkedList<>();

            var classNatureObj = pair.getKey();
            Integer amountNatureObj = pair.getValue();
            try {
                Queue<? extends Nature> natures = fillQueueAndGet(queue, classNatureObj, amountNatureObj);
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
                int randomLine = random.nextInt(0, Island.getWidthField());
                int randomColumn = random.nextInt(0, Island.getHeightField());

                Nature object = queue.poll();
                transferObjToNewLocation(randomLine, randomColumn, object);
            }
        }
    }

    private Queue<? extends Nature> fillQueueAndGet(
            Queue<Nature> queue, Class<? extends Nature> classNatureObj, int amountObj)
            throws InstantiationException, IllegalAccessException
    {
        for (int i = 0; i < amountObj; i++) {
            try {
                Constructor<? extends Nature> constructor = classNatureObj.getConstructor();
                Nature natureObj = constructor.newInstance();
                queue.add(natureObj);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return queue;
    }
}