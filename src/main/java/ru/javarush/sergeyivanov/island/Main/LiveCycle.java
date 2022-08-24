package ru.javarush.sergeyivanov.island.Main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LiveCycle {
    static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger cycleLogger = LogManager.getLogger(LiveCycle.class);

    public void launch() {
        ExecutorService service = Executors.newWorkStealingPool();

        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                Location currentLocation = Island.getInstance().getField()[i][j];

                for (Map.Entry<Class<? extends Nature>, BlockingQueue<? extends Nature>> entry :
                        currentLocation.getMapQueuesNatureObj().entrySet()) {

                    if (Animal.class.isAssignableFrom(entry.getKey())) {
                        for (Nature object : entry.getValue()) {
                            service.submit((Runnable) object);
                        }
                    }
                }
            }
        }
        try {
            service.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

                for (Map.Entry<Class<? extends Nature>, BlockingQueue<? extends Nature>> entry :
                        currentLocation.getMapQueuesNatureObj().entrySet()) {

                    if (Animal.class.isAssignableFrom(entry.getKey())) {
                        for (Nature object : entry.getValue()) {
                            Animal animal = (Animal) object;
                            animal.updateParamForNewCycle();
                        }
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
