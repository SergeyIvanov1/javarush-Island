package ru.javarush.sergeyivanov.island.Main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

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

                BlockingQueue<Predator> predators = currentLocation.getPredators();
                for (Predator predator : predators) {
                    if (!predator.markerOfEndedCycle) {
                        service.submit(predator);
//                        predator.liveOneCycle();
//                        rootLogger.debug("+++++++++++++++++++++++++++++\n");
                    }
                }

                BlockingQueue<Herbivore> herbivores = currentLocation.getHerbivores();
                for (Herbivore herbivore : herbivores) {
                    if (!herbivore.markerOfEndedCycle) {
                        service.submit(herbivore);
//                        herbivore.liveOneCycle();
//                        rootLogger.debug("+++++++++++++++++++++++++++++\n");
                    }
                }

                try {
                    service.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void repeatCycle(int amount) {
        for (int i = 0; i < amount; i++) {
            rootLogger.debug("**************** Launched cycle № " + i +" *******************");
            launch();
            updateNewCycle();
            Statistic.collectDataOfNewCycle();
            Statistic.printParamNewCycle();
        }
    }

    public void updateNewCycle() {
        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                Location currentLocation = Island.getInstance().getField()[i][j];

                BlockingQueue<Predator> predators = currentLocation.getPredators();
                for (Predator predator : predators) {
                predator.updateParamNewDay();
                }

                BlockingQueue<Herbivore> herbivores = currentLocation.getHerbivores();
                for (Herbivore herbivore : herbivores) {
                herbivore.updateParamNewDay();
                }
            }
        }
    }
}

