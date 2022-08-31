package ru.javarush.sergeyivanov.island.user_comunication;

import ru.javarush.sergeyivanov.island.content_of_island.exceptions.ValueInvalidException;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;

import java.util.Scanner;

public class Dialogue {
    private boolean settingsIsDefault = true;
    private final Scanner scanner = new Scanner(System.in);
    Parameters parameters;

    public Dialogue(Parameters parameters) {
        this.parameters = parameters;
    }

    public void initialise() {
        System.out.println("This is application for simulation processes of nature on an island.\n" +
                "Choose menu item, write number to the console:\n" +
                "1. use default settings\n" +
                "2. input settings manually");

        while (true) {
            String item = scanner.nextLine();

            if ("1".equals(item)) {
                parameters.fillIsland();
                requestSettings();
                break;
            } else if ("2".equals(item)) {
                settingsIsDefault = false;
                requestChangeSizeIsland();
                parameters.fillIsland();
                requestSettings();
            }
        }
    }

    public void requestSettings() {
        System.out.println("Do you want to get parameters (settings, ration)\n" +
                " of animals and plants to the console?\n" +
                "1. yes\n" +
                "2. no");
        String choice = scanner.nextLine();

        while (true) {
            if ("1".equals(choice)) {
                parameters.printSettings();
                parameters.printRations();
                break;
            } else if ("2".equals(choice)) {
                break;
            } else {
                System.out.println("Enter: \"1\" or \"2\"");
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

    public boolean isSettingsDefault() {
        return settingsIsDefault;
    }

    private void requestChangeSizeIsland() {
        System.out.println("Does the size of the island remain the default\n" +
                "1. yes\n" +
                "2. change");
        while (true) {
            String result = scanner.nextLine();
            if ("1".equals(result)) {
                break;
            } else if ("2".equals(result)) {
                System.out.println("Input number - width and in the new line - height");
                String line = scanner.nextLine();
                int width = parseToInt(line);
                String column = scanner.nextLine();
                int height = parseToInt(column);

                changeSizeOfIsland(width, height);
            }else {
                System.out.println("Enter: \"1\" or \"2\"");
            }
        }
    }

    private void changeSizeOfIsland(int width, int height) {
        parameters.getIsland().setWidthSize(width);
        parameters.getIsland().setHeightSize(height);
    }

    private int parseToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValueInvalidException("String does not contain a parsable integer", e);
        }
    }
}
