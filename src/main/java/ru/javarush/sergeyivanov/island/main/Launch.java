package ru.javarush.sergeyivanov.island.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.field.Island;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;
import ru.javarush.sergeyivanov.island.multithreading.Consumer;
import ru.javarush.sergeyivanov.island.multithreading.Producer;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

public class Launch {
    private static final Logger rootLogger = LogManager.getRootLogger();
    private static final int AMOUNT_THREADS = 15;

    private static final int AMOUNT = 15;
    private final Island island;
    private final Parameters parameters;

    public Launch(Island island) {
        this.island = island;
        parameters = new Parameters(island);
    }

    public void start() {
        parameters.fillIsland();
        parameters.printSettings();
        parameters.printRations();
        parameters.printParametersOfField();

        repeatAmountCycle(AMOUNT);
    }

    public void launchThreads() {
        BlockingQueue<Queue<? extends Nature>> synchronousQueue = new SynchronousQueue<>();

        for (int i = 0; i < island.getWidthOfField(); i++) {
            for (int j = 0; j < island.getHeightOfField(); j++) {
                Location currentLocation = island.getField()[i][j];

                Producer producer = new Producer(synchronousQueue, currentLocation);
                producer.start();

                Consumer[] consumers = new Consumer[AMOUNT_THREADS];
                for (int k = 0; k < AMOUNT_THREADS; k++) {
                    consumers[k] = new Consumer(synchronousQueue);
                    consumers[k].start();
                }
            }
        }
    }

    private void repeatAmountCycle(int amount)  {
        for (int i = 0; i < amount; i++) {
            rootLogger.debug("**************** Launched cycle № " + i + " *******************");
            launchThreads();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateCycle();
            calculateAmountAnimals();
            Statistic.printParamCurrentCycle();
        }
        System.out.println("Detailed statistic are reflected in the log");
    }

    public void updateCycle() {
        ExecutorService serviceOfPlantGrowth = Executors.newWorkStealingPool();
        for (int i = 0; i < island.getWidthOfField(); i++) {
            for (int j = 0; j < island.getHeightOfField(); j++) {
                Location currentLocation = island.getField()[i][j];
                serviceOfPlantGrowth.submit(currentLocation);

                for (Map.Entry<Class<? extends Nature>, Queue<? extends Nature>> entry :
                        currentLocation.getMapQueuesNatureObj().entrySet()) {
                    if (Animal.class.isAssignableFrom(entry.getKey())) {
                        for (Nature object : entry.getValue()) {
                            Animal animal = (Animal) object;
                            animal.updateParamForAnimal();
                        }
                    }
                }
            }
        }
        try {
            serviceOfPlantGrowth.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void calculateAmountAnimals(){
        for (int i = 0; i < island.getWidthOfField(); i++) {
            for (int j = 0; j < island.getHeightOfField(); j++) {
                Location currentLocation = island.getField()[i][j];

                for (Map.Entry<Class<? extends Nature>, Integer> entry : parameters.getCacheNatureObj().entrySet()) {
                    Class<? extends Nature> classObj = entry.getKey();
                    Queue<? extends Nature> storage = currentLocation.getQueueOfNatureObjects(classObj);
                    int amountAnimals = storage.size();

                    if (Animal.class.isAssignableFrom(entry.getKey())) {
                        Statistic.amountAnimalsInNewCycle += amountAnimals;
                    }
                }
            }
        }
    }
}
