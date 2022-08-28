package ru.javarush.sergeyivanov.island.main;

import ru.javarush.sergeyivanov.island.content_of_island.field.Island;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Island island = new Island();
        Launch launch = new Launch(island);
        launch.start();
    }
}
