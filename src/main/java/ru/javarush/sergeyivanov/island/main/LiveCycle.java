package ru.javarush.sergeyivanov.island.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.field.Island;
import ru.javarush.sergeyivanov.island.content_of_island.field.Location;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.multithreading.Consumer;
import ru.javarush.sergeyivanov.island.multithreading.Producer;
import ru.javarush.sergeyivanov.island.multithreading.ThreadsAllocator;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

public class LiveCycle {
    static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger cycleLogger = LogManager.getLogger(LiveCycle.class);

    public void launch() {
        ExecutorService executorService = Executors.newWorkStealingPool();
        BlockingQueue<Queue<? extends Nature>> synchronousQueue = new SynchronousQueue<>();

        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                Location currentLocation = Island.getInstance().getField()[i][j];
                Producer producer = new Producer(synchronousQueue, currentLocation);
                producer.start();

                Consumer[] consumers = new Consumer[15];
                for (int k = 0; k < 15; k++) {
                    consumers[k] = new Consumer(synchronousQueue, executorService);
                    consumers[k].start();
                }

//                for (Map.Entry<Class<? extends Nature>, Queue<? extends Nature>> entry :
//                        currentLocation.getMapQueuesNatureObj().entrySet()) {
//
//                    Class<? extends Nature> key = entry.getKey();
//                    if (Animal.class.isAssignableFrom(key)) {
//
//                            service.submit(new ThreadsAllocator(service1, entry.getValue()));
//
//                    }
                }
            }
//        }
//        try {
//            service.awaitTermination(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void repeatCycle(int amount) {
        for (int i = 0; i < amount; i++) {
            rootLogger.debug("**************** Launched cycle № " + i + " *******************");
            launch();
            updateCycle();
            Statistic.calculateAmountAnimals();
            Statistic.printParamCurrentCycle();
        }
        System.out.println("Detailed statistic are reflected in the log");
    }

    public void updateCycle() {
        ExecutorService serviceOfPlantGrowth = Executors.newWorkStealingPool();
        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                Location currentLocation = Island.getInstance().getField()[i][j];

                for (Map.Entry<Class<? extends Nature>, Queue<? extends Nature>> entry :
                        currentLocation.getMapQueuesNatureObj().entrySet()) {

                    if (Animal.class.isAssignableFrom(entry.getKey())) {
                        for(Iterator<? extends Nature> iterator = entry.getValue().iterator(); iterator.hasNext();){
                            Animal animal = (Animal) iterator.next();
                            animal.updateParamForNewCycle();
                        }
//                        for (Nature object : entry.getValue()) {
//                            Animal animal = (Animal) object;
//                            animal.updateParamForNewCycle();
//                        }
                    }
                }
                serviceOfPlantGrowth.submit(currentLocation);
            }
        }
        try {
            serviceOfPlantGrowth.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
