package ru.javarush.sergeyivanov.island.Inicialization;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Nature;

import java.sql.*;
import java.util.*;

public class InitParameters {

    ProcessorParam processor = new ProcessorParam();

    private final Long durationCycle = 3_000L;
    private final int amountCycles = 3;
    private int startAmountAnimals;

    private boolean termForStopping;
    private int startAmountChildren;

//    List<Map<Class<? extends Nature>, Integer>> parameters = new ArrayList<>();
    private static final Map<Class<? extends Nature>, Integer> map = new HashMap<>();

    {
        String userName = "root";
        String password = "Fhgffv56764()()";
        String URL = "jdbc:mysql://localhost:3306/island_settings";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT class_name, amount_objects FROM settings");

            while (resultSet.next()){
                String class_name = resultSet.getString("class_name");
                Class<? extends Nature> classObj = (Class<? extends Nature>) Class.forName(class_name);
                int amount_objects = resultSet.getInt("amount_objects");
               map.put(classObj, amount_objects);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

//        parameters.add(ParamHerbivores.getMapOfAnimals());
//        parameters.add(ParamPredators.getMapOfAnimals());
//        parameters.add(ParamPlants.getMapOfPlants());
        System.out.println("parameters.size = " + map.size());

        initField();

//        for (Map<Class<? extends Nature>, Integer> map: parameters) {
            List<Queue<? extends Nature>> listQueuesByObjects = processor.createListQueuesByObjects(map);
            processor.allocateObjsIntoField(listQueuesByObjects);
//        }
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

