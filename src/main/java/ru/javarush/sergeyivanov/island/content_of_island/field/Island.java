package ru.javarush.sergeyivanov.island.content_of_island.field;

public class Island {
    private Location[][] field;
    private int widthSize = 4;
    private int heightSize = 4;

    public Island() {
        field = new Location[widthSize][heightSize];
    }

    public Location[][] getField() {
        return field;
    }

    public void setField(int width, int height) {
        this.field = new Location[width][height];
    }

    public int getWidthOfField() {
        return widthSize;
    }

    public int getHeightOfField() {
        return heightSize;
    }

    public void setWidthSize(int widthSize) {
        this.widthSize = widthSize;
    }

    public void setHeightSize(int heightSize) {
        this.heightSize = heightSize;
    }
}
