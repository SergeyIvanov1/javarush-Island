package ru.javarush.sergeyivanov.island.content_of_island.field;

public class Island {
    private final Location[][] FIELD;
    private int widthSize = 4;
    private int heightSize = 4;

    public Island() {
        FIELD = new Location[widthSize][heightSize];
    }

    public Location[][] getField() {
        return FIELD;
    }

    public int getWidthOfField() {
        return widthSize;
    }

    public int getHeightOfField() {
        return heightSize;
    }
}
