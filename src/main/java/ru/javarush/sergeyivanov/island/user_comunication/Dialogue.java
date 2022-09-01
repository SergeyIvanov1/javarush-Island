package ru.javarush.sergeyivanov.island.user_comunication;

import ru.javarush.sergeyivanov.island.content_of_island.exceptions.ValueInvalidException;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;

import java.util.Scanner;

public class Dialogue {
    private static final String MESSAGE = "Invalid value: ";
    private final Scanner scanner = new Scanner(System.in);
    Parameters parameters;

    public Dialogue(Parameters parameters) {
        this.parameters = parameters;
    }

    public void initialise() {
        System.out.println("""
                *** This is application for simulation processes ***
                \t\t\t of nature on an island.

                \tChoose menu item, write number to the console:
                1. use default settings
                2. input settings manually""");

        while (true) {
            String item = scanner.nextLine();

            if ("1".equals(item)) {
                parameters.fillIsland();
                requestSettings();
                break;
            } else if ("2".equals(item)) {
                requestChangeSizeIsland();
                parameters.fillIsland();
                requestSettings();
                break;
            } else {
                System.out.println("Choose number and input to the console");
            }
        }
    }

    public void requestSettings() {
        while (true) {

            System.out.println("""

                    \tWould you to get of parameters of animals and plants\nto the console?
                    1. values of parameters
                    2. rations and probability eating
                    3. allocating animals and plants in locations
                    4. exit menu
                    """);
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    parameters.printSettings();
                    break;
                case "2":
                    parameters.printRations();
                    break;
                case "3":
                    parameters.printParametersOfField();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Choose number and input to the console");
            }
        }
    }

    public void changeSettings() {
        System.out.println("What kind of setting objects (animals and plants) do you want change. Choose menu item.\n" +
                "1. Size of island\n" +
                "2. Amount of objects\n" +
                "3. Weight of objects\n" +
                "4. Maximal amount of objects in cells\n" +
                "5. Range of move\n" +
                "6. Amount of need food\n" +
                "7. Amount of children\n" +
                "8. Amount repeat of cycles\n" +
                "9. Amount cycles of life\n" +
                "10. To set probability of eating animals and plants by another animals\n");
    }

    private void requestChangeSizeIsland() {
        System.out.println("\tDoes the size of the island remain the default?\n" +
                "1. yes\n" +
                "2. change");
        while (true) {
            String result = scanner.nextLine();
            if ("1".equals(result)) {
                return;
            } else if ("2".equals(result)) {
                changingField();
                return;
            } else {
                System.out.println("On the first string input amount of cells - width\n" +
                        " and in the new string - height");
            }
        }
    }

    private void changingField() {
        System.out.println("\nInput amount of cells: width \nand in the new string - height");
        while (true) {
            String value = null;
            try {
                value = scanner.nextLine();
                int width = checkValue(value);
                value = scanner.nextLine();
                int height = checkValue(value);
                if (width > 0 && height > 0) {
                    changeSizeOfIsland(width, height);
                    break;
                }
                System.err.println("number can't be negative");
            } catch (ValueInvalidException ex) {
                messageToUserAboutError(ex, value);
            }
        }
    }

    private void changeSizeOfIsland(int width, int height) {
        parameters.getIsland().setWidthSize(width);
        parameters.getIsland().setHeightSize(height);
        parameters.getIsland().setField(width, height);
    }

    private int checkValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValueInvalidException("String does not contain a parsable integer", e);
        }
    }

    private void messageToUserAboutError(Exception exception, String value) {
        System.out.println(MESSAGE + value);
        System.err.println(exception.getMessage());
    }
}
