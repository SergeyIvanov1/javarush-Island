package ru.javarush.sergeyivanov.island.user_comunication;

import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.content_of_island.flora.Plant;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;

import java.util.*;

public class Dialogue {
    private static final int MAX_VALUE = 100;
    private final Scanner scanner = new Scanner(System.in);
    private final List<Class<? extends Nature>> listClasses = new ArrayList<>();
    private final Parameters parameters;
    private final DialogueService service;

    public Dialogue(Parameters parameters) {
        this.parameters = parameters;
        this.service = new DialogueService(parameters, listClasses);
    }

    public void initialise() {
        System.out.println("*** This is application for simulation processes ***\n" +
                "\t\t\t of nature on an island.");

        chooseSourceOfParameters();
        service.fillListOfClasses();

        System.out.println("\tChoose menu item, write number to the console:\n" +
                "1. use default settings\n" +
                "2. input settings manually");

        while (true) {
            String item = scanner.nextLine();

            if ("1".equals(item)) {
                parameters.fillIsland();
                requestSettings();
                break;

            } else if ("2".equals(item)) {
                requestChangeSizeIsland();
                requestChangeAmountRepeatOfCycles();
                requestChangeSettings();
                parameters.fillIsland();
                requestSettings();
                break;
            } else {
                System.out.println("Choose number and input to the console");
            }
        }
    }

    private void requestSettings() {
        while (true) {

            System.out.println("""

                    \tWould you to get of parameters of animals and plants\nto the console?
                    1. values of parameters
                    2. rations and probability of eating
                    3. allocating animals and plants in locations
                    4. exit menu
                    """);
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> parameters.printSettings();
                case "2" -> parameters.printRations();
                case "3" -> parameters.printParametersOfField();
                case "4" -> {
                    return;
                }
                default -> System.out.println("Choose number and input to the console");
            }
        }
    }

    private void requestChangeSettings() {

        while (true) {
            System.out.println("\nWhat object animals or plants do you want change?\n" +
                    "Input number of list:");
            service.printListClasses();

            int resultOfRequest = service.getValueFromUser(listClasses.size());
            if (resultOfRequest == listClasses.size()) {
                return;
            }
            requestChangeParameters(listClasses.get(resultOfRequest));
        }
    }

    private void requestChangeParameters(Class<? extends Nature> aClass) {
        while (true) {
            System.out.println("\nYou selected: " + aClass.getSimpleName());
            if (Plant.class.isAssignableFrom(aClass)) {
                System.out.println("""
                        What kind of setting object do you want change. Choose menu item.
                        1. Amount of objects
                        2. Weight of objects
                        3. Maximal amount of objects in cells
                        4. exit menu
                        """);

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> service.changeAmount(aClass);
                    case "2" -> service.changeWeight(aClass);
                    case "3" -> service.changeMaximalAmountInCells(aClass);
                    case "4" -> {
                        return;
                    }
                    default -> System.out.println("Choose number and input to the console");
                }
            } else {
                System.out.println("""
                        What kind of setting object do you want change. Choose menu item.
                        1. Amount of objects
                        2. Weight of objects
                        3. Maximal amount of objects in cells
                        4. Range of move
                        5. Amount of need food
                        6. Amount of children
                        7. Amount cycles of life
                        8. To set probability of eating another objects by selected object
                        9. exit menu
                        """);

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> service.changeAmount(aClass);
                    case "2" -> service.changeWeight(aClass);
                    case "3" -> service.changeMaximalAmountInCells(aClass);
                    case "4" -> service.changeRangeOfMove(aClass);
                    case "5" -> service.changeAmountOfNeedFood(aClass);
                    case "6" -> service.changeAmountOfChildren(aClass);
                    case "7" -> service.changeAmountCyclesOfLife(aClass);
                    case "8" -> service.changeRationAndProbability(aClass);
                    case "9" -> {
                        return;
                    }
                    default -> System.out.println("Choose number and input to the console");
                }
            }
        }
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
                service.changingField();
                return;
            } else {
                System.out.println("On the first string input amount of cells - width\n" +
                        " and in the new string - height");
            }
        }
    }

    private void requestChangeAmountRepeatOfCycles() {
        System.out.println("\tDoes the amount repeat of cycles remain the default?\n" +
                "1. yes\n" +
                "2. change");
        while (true) {
            String result = scanner.nextLine();

            if ("1".equals(result)) {
                return;
            } else if ("2".equals(result)) {
                System.out.println("Input new value of amount cycles");
                int newValue = service.getValueFromUser(MAX_VALUE);

                parameters.setAmount(newValue);
                System.out.println("Value of amount repeat cycles changed. New value " + newValue);
                break;
            } else {
                System.out.println("Choose number and input to the console");
            }
        }
    }

    private void chooseSourceOfParameters() {
        System.out.println("""

                \tYou can choose source getting of default parameter values:
                1. Data base
                2. JSON file\s""");
        while (true) {
            String result = scanner.nextLine();
            switch (result){
                case "1" -> {
                    parameters.fillCachesFromDataBase();
                    return;
                }
                case "2" -> {
                    parameters.fillCachesFromJSON();
                    return;
                }
                default -> System.out.println("Choose number and input to the console");
            }
        }
    }
}
