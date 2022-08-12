package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

public class Island {
    private static Island instance;
    private static final Location[][] FIELD;
    private static int widthSize = 5;
    private static int heightSize = 5;

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
