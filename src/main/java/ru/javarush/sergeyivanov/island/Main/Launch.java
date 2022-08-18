package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.Animal;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Buffalo;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.HerbivoreAnimals.Herbivore;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Predator;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Fauna.PredatoryAnimals.Wolf;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Island;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;
import ru.javarush.sergeyivanov.island.ContentOfIsland.Flora.Plants.Plant;
import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class Launch {
    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, SQLException {
//        Scanner scanner = new Scanner(System.in);

//        System.out.println("Does to run the program manually?");
//        boolean answer = scanner.nextBoolean();

//        new InitParameters(false);
//        Statistic.printParametersOfField();
//
//        LiveCycle cycle = new LiveCycle();
//        cycle.repeatCycle(3);
        String nameUser = "root";
        String password = "Fhgffv56764()()";
        String URL = "jdbc:mysql://localhost:3306/island_settings";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, nameUser, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM settings");
            while (resultSet.next()){
                String name = resultSet.getString("animal");
                System.out.println(name);
            }
        }
    }
}
