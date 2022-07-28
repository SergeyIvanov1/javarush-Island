package ru.javarush.sergeyivanov.island.Main;

import ru.javarush.sergeyivanov.island.Inicialization.InitParameters;

import java.util.Scanner;

public class Launch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        System.out.println("Does to run the program manually?");
//        boolean answer = scanner.nextBoolean();
        new InitParameters(false);
//        System.out.println(Arrays.deepToString(Island.getInstance().getField()));
    }
}
