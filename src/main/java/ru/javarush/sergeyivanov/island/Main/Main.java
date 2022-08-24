package ru.javarush.sergeyivanov.island.Main;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {
        Thread thread = new Thread(new Launch());
        thread.start();

    }
}
