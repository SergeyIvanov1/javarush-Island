package ru.javarush.sergeyivanov.island.content_of_island.field;

public class Island {
    private static Island instance;
    private static final Location[][] FIELD;
    private static int widthSize = 4;
    private static int heightSize = 4;

    static {
        FIELD = new Location[widthSize][heightSize];
    }

    private Island() {
    }

    public static Island getInstance(){
        if (instance == null){
            instance = new Island();
        }
        return instance;
    }

    public  Location[][] getField() {
        return FIELD;
    }

    public static int getWidthField() {
        return widthSize;
    }

    public static int getHeightField() {
        return heightSize;
    }
}
