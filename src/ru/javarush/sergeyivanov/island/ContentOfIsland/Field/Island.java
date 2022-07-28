package ru.javarush.sergeyivanov.island.ContentOfIsland.Field;

import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

public class Island {
    private static Island instance;
    private static final Location[][] FIELD;

    static {
        FIELD = new Location[InitParameters.getHeightSize()][InitParameters.getWidthSize()];
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
}
