package ru.javarush.sergeyivanov.island.main;

import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.inicialization.InitParameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBProcessor {

    private static final double PERCENTAGE_OF_SATIETY_REDUCTION = 25;
    private static final int ZERO = 0;
    private static final int ONE_HUNDRED_PERCENTS = 100;

    private static String userName;
    private static String password;
    private static String URL;

    static {
        Properties properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream("src/main/resources/settings.xml"));
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            URL = properties.getProperty("URL");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double reduceSatiety(double satiety, double amountNeedFood) {
        double hunger = PERCENTAGE_OF_SATIETY_REDUCTION * amountNeedFood / ONE_HUNDRED_PERCENTS;
        return roundNumber(satiety - hunger);
    }

    public static double roundNumber(double calculate) {
        double scale = Math.pow(10, 3);
        return Math.floor(calculate * scale) / scale;
    }

    public static Map<String, Map<Class<? extends Animal>, Integer>> getCacheRationsFromDataBase() {
        Map<String, Map<Class<? extends Animal>, Integer>> cacheRation = new HashMap<>();

        try {
            Connection connection = DriverManager.getConnection(URL, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            for (Map.Entry<Class<? extends Nature>, Integer> entry : InitParameters.cacheNatureObj.entrySet()) {
                if (Animal.class.isAssignableFrom(entry.getKey())) {
                    Map<Class<? extends Animal>, Integer> ration = new HashMap<>();
                    String nameAnimal = entry.getKey().getSimpleName();
                    resultSet = statement.executeQuery(
                            "SELECT ration_animal, " + nameAnimal + " FROM table_of_probability WHERE " +
                                    nameAnimal + " > " + ZERO);

                    while (resultSet.next()) {
                        String class_name = resultSet.getString("ration_animal");
                        Class<? extends Animal> classObj = (Class<? extends Animal>) Class.forName(class_name);
                        int probability = resultSet.getInt(nameAnimal);
                        ration.put(classObj, probability);
                    }
                    cacheRation.put(nameAnimal, ration);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return cacheRation;
    }

    public static Map <String, Map<String, Number>> getCacheSettingsFromDataBase() {
        Map <String, Map<String, Number>> cacheSettingsFromDataBase = new HashMap<>();

        try {
            Connection connection = DriverManager.getConnection(URL, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM settings");

            while (resultSet.next()) {
                Map<String, Number> settingsObj = new HashMap<>();
                String className = resultSet.getString("class_name");
                Class<? extends Nature> classObj = (Class<? extends Nature>) Class.forName(className);
                String nameObj = classObj.getSimpleName();

                settingsObj.put("weight", resultSet.getDouble("weight"));
                settingsObj.put("maxObjInCell", resultSet.getInt("max_obj_in_cell"));
                settingsObj.put("rangeMove", resultSet.getInt("range_move"));
                settingsObj.put("amountNeedFood", resultSet.getDouble("amount_need_food"));
                settingsObj.put("amountChildren", resultSet.getInt("amount_children"));
                settingsObj.put("amountCycleLive", resultSet.getInt("amount_cycle_live"));
                cacheSettingsFromDataBase.put(nameObj, settingsObj);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return cacheSettingsFromDataBase;
    }

    public static Map<Class<? extends Nature>, Integer> getCacheNatureObjectsFromDB(){
        Map<Class<? extends Nature>, Integer> cacheNatureObj = new HashMap<>();

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
        return cacheNatureObj;
    }
}
