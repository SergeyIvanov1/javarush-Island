package ru.javarush.sergeyivanov.island.ContentOfIsland;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Nature {
    Location location;

    protected int indexLineField;
    protected int IndexColumnField;

    String nameObj;

    protected double weight;
    protected int maxObjInCell;
    protected int rangeMove;
    protected double amountNeedFood;
    protected int amountChildren;
    protected int amountCycleLive;

    {
        nameObj = this.getClass().getSimpleName();

//        String userName = "root";
//        String password = "Fhgffv56764()()";
//        String URL = "jdbc:mysql://localhost:3306/island_settings";
//        try {
//            Connection connection = DriverManager.getConnection(URL, userName, password);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT ration_animal, " + nameObj + " FROM table_of_probability WHERE " +
//                            nameObj + " > 0");
//
//            while (resultSet.next()) {
//                String class_name = resultSet.getString("ration_animal");
//                Class<? extends Nature> classObj = (Class<? extends Nature>) Class.forName(class_name);
//                int probability = resultSet.getInt(nameObj);
//                ration.put(classObj, probability);
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public double getWeight() {
        return weight;
    }

    public int getIndexLineField() {
        return indexLineField;
    }

    public void setIndexLineField(int indexLineField) {
        this.indexLineField = indexLineField;
    }

    public int getIndexColumnField() {
        return IndexColumnField;
    }

    public void setIndexColumnField(int indexColumnField) {
        this.IndexColumnField = indexColumnField;
    }
}
