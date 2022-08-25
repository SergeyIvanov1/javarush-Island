package ru.javarush.sergeyivanov.island.multithreading;

import ru.javarush.sergeyivanov.island.content_of_island.Nature;

import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class ThreadsAllocator implements Runnable{

    ExecutorService service;
    Queue<? extends Nature> queueAnimal;

    public ThreadsAllocator(ExecutorService service, Queue<? extends Nature> queueAnimal) {
        this.service = service;
        this.queueAnimal = queueAnimal;
    }

    @Override
    public void run() {
        for (Nature natureObject : queueAnimal) {
            service.submit((Runnable) natureObject);
        }
    }
}
