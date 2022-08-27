package ru.javarush.sergeyivanov.island.content_of_island.field;

public class Island {
    private static Island instance;
    private final Location[][] FIELD;
    private int widthSize = 4;
    private int heightSize = 4;

    private Island() {
        FIELD = new Location[widthSize][heightSize];
    }

    public static Island getInstance(){
        if (instance == null){
            instance = new Island();
        }
        return instance;
    }

    public Location[][] getField() {
        return FIELD;
    }

    public int getWidthField() {
        return widthSize;
    }

    public int getHeightField() {
        return heightSize;
    }
}
