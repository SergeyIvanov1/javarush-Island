package ru.javarush.sergeyivanov.island.inicialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.content_of_island.fauna.Animal;
import ru.javarush.sergeyivanov.island.content_of_island.flora.Plant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SourceParamProcessor {

    private final int ZERO = 0;
    private String userName;
    private String password;
    private String URL;
    private final Parameters parameters;

    public SourceParamProcessor(Parameters parameters) {
        this.parameters = parameters;
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

    public Map<String, Map<Class<? extends Nature>, Integer>> getCacheRationsFromDataBase() {
        Map<String, Map<Class<? extends Nature>, Integer>> cacheRation = new HashMap<>();

        try {
            Connection connection = DriverManager.getConnection(URL, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            for (Map.Entry<Class<? extends Nature>, Integer> entry : parameters.getCacheNatureObj().entrySet()) {
                if (Animal.class.isAssignableFrom(entry.getKey())) {
                    Map<Class<? extends Nature>, Integer> ration = new HashMap<>();
                    String nameAnimal = entry.getKey().getSimpleName();
                    resultSet = statement.executeQuery(
                            "SELECT ration_animal, " + nameAnimal + " FROM table_of_probability WHERE " +
                                    nameAnimal + " > " + ZERO);

                    while (resultSet.next()) {
                        String class_name = resultSet.getString("ration_animal");
                        Class<? extends Nature> classObj = (Class<? extends Nature>) Class.forName(class_name);
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

    public Map<String, Map<String, Number>> getCacheSettingsFromDataBase() {
        Map<String, Map<String, Number>> cacheSettingsFromDataBase = new HashMap<>();

        try {
            Connection connection = DriverManager.getConnection(URL, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM settings");

            while (resultSet.next()) {
                Map<String, Number> settingsObj = new HashMap<>();
                String className = resultSet.getString("class_name");
                Class<? extends Nature> classObj = (Class<? extends Nature>) Class.forName(className);
                String nameObj = classObj.getSimpleName();

                if (Plant.class.isAssignableFrom(classObj)) {
                    settingsObj.put("weight", resultSet.getDouble("weight"));
                    settingsObj.put("maxObjInCell", resultSet.getInt("max_obj_in_cell"));
                    cacheSettingsFromDataBase.put(nameObj, settingsObj);
                } else {
                    settingsObj.put("weight", resultSet.getDouble("weight"));
                    settingsObj.put("maxObjInCell", resultSet.getInt("max_obj_in_cell"));
                    settingsObj.put("rangeMove", resultSet.getInt("range_move"));
                    settingsObj.put("amountNeedFood", resultSet.getDouble("amount_need_food"));
                    settingsObj.put("amountChildren", resultSet.getInt("amount_children"));
                    settingsObj.put("amountCycleLive", resultSet.getInt("amount_cycle_live"));
                    cacheSettingsFromDataBase.put(nameObj, settingsObj);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return cacheSettingsFromDataBase;
    }

    public Map<Class<? extends Nature>, Integer> getCacheNatureObjectsFromDB() {
        Map<Class<? extends Nature>, Integer> cacheNatureObj = new HashMap<>();

        try {
            Connection connection = DriverManager.getConnection(URL, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT class_name, amount_objects FROM settings");

            while (resultSet.next()) {
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

    public Map<String, Map<Class<? extends Nature>, Integer>> getCacheRationsFromJSON() {
        Map<String, Map<Class<? extends Nature>, Integer>> cacheRation = new HashMap<>();
        String path = "src/main/resources/rations.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Map<Class<? extends Nature>, Integer>> temporaryMap = objectMapper.readValue(new File(path),
                    new TypeReference<>(){
                    });
            cacheRation.putAll(temporaryMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cacheRation;
    }

    public Map<String, Map<String, Number>> getCacheSettingsFromJSON() {
        Map<String, Map<String, Number>> cacheSettingsFromDataBase = new HashMap<>();
        String path = "src/main/resources/parameters.json";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Map<String, Number>> temporaryMap = objectMapper.readValue(
                    new File(path), new TypeReference<>() {
                    });

            cacheSettingsFromDataBase.putAll(temporaryMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cacheSettingsFromDataBase;
    }

    public Map<Class<? extends Nature>, Integer> getCacheNatureObjectsFromJSON() {
        Map<Class<? extends Nature>, Integer> cacheNatureObj = new HashMap<>();
        String path = "src/main/resources/objects_nature.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<Class<? extends Nature>, Integer> temporaryMap = objectMapper.readValue(
                    new File(path), new TypeReference<>() {
                    });
            cacheNatureObj.putAll(temporaryMap);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cacheNatureObj;
    }
}
