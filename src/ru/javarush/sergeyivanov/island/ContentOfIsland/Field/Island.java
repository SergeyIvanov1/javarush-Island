package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

public class Island {
    private static Island instance;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 20 ;

    private static final Location[][] FIELD = new Location[DEFAULT_WIDTH][DEFAULT_HEIGHT];;

    private Island() {
    }

    private static Island getInstance(){
        if (instance == null){
            instance = new Island();
        }
        return instance;
    }

    public Location[][] getLocationOfField() {
        return FIELD;
    }
}
