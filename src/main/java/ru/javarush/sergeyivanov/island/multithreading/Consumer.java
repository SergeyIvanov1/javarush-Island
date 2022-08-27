package ru.javarush.sergeyivanov.island.multithreading;

import ru.javarush.sergeyivanov.island.content_of_island.Nature;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class Consumer extends Thread{
    private final BlockingQueue<Queue<? extends Nature>> synchronousQueue;

    public Consumer (BlockingQueue<Queue<? extends Nature>> queue) {
        this.synchronousQueue = queue;
    }

    public void run() {
        try {
            Queue<? extends Nature> queueAnimals = synchronousQueue.take();
            int count = 1;
            for (Nature nature: queueAnimals) {
                new Thread((Runnable) nature, "Thread № " + count +" for queue" + nature.getClass().getSimpleName()).start();
                count++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
