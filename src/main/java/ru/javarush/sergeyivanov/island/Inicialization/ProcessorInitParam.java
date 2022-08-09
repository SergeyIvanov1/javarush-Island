package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ProcessorInitParam {

    static List<Queue<? extends Nature>> createListQueuesByObjects(Map<Class<? extends Nature>, Integer> map) {
        List<Queue<? extends Nature>> listQueue = new ArrayList<>();

        for (Map.Entry<Class<? extends Nature>, Integer> pair : map.entrySet()) {
           Queue<Nature> queue = new LinkedList<>();

            var aClass = pair.getKey();
            Integer amount = pair.getValue();
            try {
                listQueue.add(fillQueueAndGet(queue, aClass, amount));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return listQueue;
    }

    static void allocateObjsIntoField(List<Queue<? extends Nature>> listQueues) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (Queue<? extends Nature> queue : listQueues) {
            if (queue != null) {
                Class<? extends Nature> gotClass = queue.peek().getClass();

                for (int i = 0; i < queue.size(); i++) {
                    int randomLine = random.nextInt(0, InitParameters.getWidthField());
                    int randomColumn = random.nextInt(0, InitParameters.getHeightField());

                    Location randomLocation = Island.getInstance().getField()[randomLine][randomColumn];
                    try {
                        BlockingQueue<Nature> locationQueue =
                                (BlockingQueue<Nature>) randomLocation.getTargetQueue(gotClass);
                        Nature object = queue.poll();
                        if (object != null) {
                            object.setLocation(randomLocation);
                            object.setIndexLineLocation(randomLine);
                            object.setIndexColumnLocation(randomColumn);

                            locationQueue.put(object);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static Queue<? extends Nature> fillQueueAndGet(
            Queue<Nature> queue, Class<? extends Nature> obj, int amountAnimals)
            throws InstantiationException, IllegalAccessException
    {
        for (int i = 0; i < amountAnimals; i++) {
            try {
                Constructor<? extends Nature> constructor = obj.getConstructor();
                Nature natureObj = constructor.newInstance();
                queue.add(natureObj);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return queue;
    }
}
