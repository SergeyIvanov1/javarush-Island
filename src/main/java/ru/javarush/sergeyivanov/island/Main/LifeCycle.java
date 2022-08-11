package ru.javarush.sergeyivanov.island.Main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LifeCycle implements Runnable{

    @Override
    public void run() {
        ExecutorService stealingPool = Executors.newWorkStealingPool();
        stealingPool.invokeAll()
    }
}
