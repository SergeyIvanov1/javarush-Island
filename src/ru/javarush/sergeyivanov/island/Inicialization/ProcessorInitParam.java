package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ProcessorInitParam {

    static <T> List<BlockingQueue<T>> createListQueuesByObjects(Map<Class, Integer> map) {
        List<BlockingQueue<T>> listQueue = new ArrayList<>();

        for (Map.Entry<Class, Integer> pair : map.entrySet()) {
            BlockingQueue<T> queue = new LinkedBlockingQueue<>();

            var aClass = pair.getKey();
            Integer amount = pair.getValue();
            try {
                listQueue.add(fillQueueObjectsAndGet(queue, aClass, amount));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return listQueue;
    }

    static <T> void allocateObjIntoField(List<BlockingQueue<T>> listQueue) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (BlockingQueue<T> queue : listQueue) {
            if (queue != null) {
                Class getClass = queue.peek().getClass();

                for (int i = 0; i < queue.size(); i++) {
                    int randomLine = random.nextInt(0, InitParameters.getWidthField());
                    int randomColumn = random.nextInt(0, InitParameters.getHeightField());

                    Location randomLocation = Island.getInstance().getField()[randomLine][randomColumn];
                    try {
                        randomLocation.getTargetQueue(getClass).put(queue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static <T> BlockingQueue<T> fillQueueObjectsAndGet(
            BlockingQueue<T> queue, Class<T> obj, int amountAnimals)
            throws InstantiationException, IllegalAccessException {

        for (int i = 0; i < amountAnimals; i++) {
            try {
                Constructor<T> constructor = obj.getConstructor();
                queue.put(constructor.newInstance());
            } catch (NoSuchMethodException | InterruptedException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return queue;
    }
}
