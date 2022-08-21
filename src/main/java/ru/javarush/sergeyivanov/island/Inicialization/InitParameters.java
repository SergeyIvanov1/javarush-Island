package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;
import ru.javarush.sergeyivanov.island.Main.DBProcessor;

import java.sql.*;
import java.util.*;

public class InitParameters {

    ProcessorParam processor = new ProcessorParam();

    private final Long durationCycle = 3_000L;
    private final int amountCycles = 3;
    private int startAmountAnimals;

    private boolean termForStopping;
    private int startAmountChildren;
    public static Statement statement;

    public static final Map<Class<? extends Nature>, Integer> cacheNatureObj = new HashMap<>();
    public static Map<String, Map<Class<? extends Animal>, Integer>> cacheRations;
    public static Map<String, Map<String, Number>> cacheSettings;

    {
        String userName = "root";
        String password = "Fhgffv56764()()";
        String URL = "jdbc:mysql://localhost:3306/island_settings";
        try {
            Connection connection = DriverManager.getConnection(URL, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT class_name, amount_objects FROM settings");

            while (resultSet.next()){
                String class_name = resultSet.getString("class_name");
                Class<? extends Nature> classObj = (Class<? extends Nature>) Class.forName(class_name);
                int amount_objects = resultSet.getInt("amount_objects");
                cacheNatureObj.put(classObj, amount_objects);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Size of Map cacheNatureObj = " + cacheNatureObj.size());

        initField();

        cacheSettings = DBProcessor.getCacheSettingsFromDataBase();
        cacheRations = DBProcessor.getCacheRationsFromDataBase();
        List<Queue<? extends Nature>> listQueuesByObjects = processor.createListQueuesByObjects(cacheNatureObj);
        processor.allocateObjsIntoField(listQueuesByObjects);
    }

    public InitParameters(boolean manual) {
        if (manual) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the width of the field");
        }
    }

    private void initField() {
        for (int i = 0; i < Island.getWidthField(); i++) {
            for (int j = 0; j < Island.getHeightField(); j++) {
                Island.getInstance().getField()[i][j] = new Location();
            }
        }
    }

    public static class Dialogue{

    }
}

