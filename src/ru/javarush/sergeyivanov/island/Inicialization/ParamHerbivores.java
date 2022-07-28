package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ParamHerbivores {

    private static final BlockingQueue<Buffalo> buffaloes = new LinkedBlockingQueue<>();

    private static int buffaloAmount  = 300;
    private int caterpillarAmount = 30_000;
    private int deerAmount  = 600;
    private int duckAmount  = 6_000;
    private int goatAmount  = 4200;
    private int horseAmount = 600;
    private int mouseAmount = 15_000;
    private int rabbitAmount = 4500;
    private int sheepAmount = 4200;
    private int wildBoarAmount = 1500;

    public static BlockingQueue<Buffalo> getBuffaloes() {
        return buffaloes;
    }

   static  {
        for (int i = 0; i < buffaloAmount; i++) {
            try {
                buffaloes.put(new Buffalo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
