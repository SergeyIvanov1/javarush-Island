package ru.javarush.sergeyivanov.island.multithreading;

import ru.javarush.sergeyivanov.island.content_of_island.Nature;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class Consumer extends Thread{
    private final BlockingQueue<Queue<? extends Nature>> synchronousQueue;
    ExecutorService executorService;

    public Consumer (BlockingQueue<Queue<? extends Nature>> queue, ExecutorService executorService) {
        this.synchronousQueue = queue;
        this.executorService = executorService;
    }

    public void run() {
        try {
            Queue<? extends Nature> queueAnimals = synchronousQueue.take();
            int count = 1;
            for (Nature nature: queueAnimals) {
                new Thread((Runnable) nature, "Thread № " + count +" for queue" + nature.getClass().getSimpleName()).start();
//                executorService.submit((Runnable) nature);
                count++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
