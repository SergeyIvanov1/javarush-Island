package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ParamProcessor {

    private static <T> BlockingQueue<T> sendObjToQueue(Class<T> obj, int amountAnimals) throws InstantiationException, IllegalAccessException {
        BlockingQueue<T> queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < amountAnimals; i++) {
            try {
                Constructor<T> constructor = obj.getConstructor();
                T t = constructor.newInstance();
                queue.put(t);
            } catch (NoSuchMethodException | InterruptedException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return queue;
    }

    //метод не проверен
    private <T> void allocateObjBetweenLocations(BlockingQueue<T> queue) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < queue.size(); i++) {
            int randomLine = random.nextInt(0, InitParameters.getWidthSize());
            int randomColumn = random.nextInt(0, InitParameters.getHeightSize());

            Location randomLocation = Island.getInstance().getField()[randomLine][randomColumn];
            try {
                randomLocation.getQueueForAllocate(queue.getClass()).put(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
