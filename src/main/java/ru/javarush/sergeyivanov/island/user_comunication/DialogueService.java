package ru.javarush.sergeyivanov.island.user_comunication;

import ru.javarush.sergeyivanov.island.content_of_island.Nature;
import ru.javarush.sergeyivanov.island.content_of_island.exceptions.ValueInvalidException;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;

import java.util.*;

public class DialogueService {
    private static final int MAX_VALUE = 100;
    private static final String MESSAGE = "Invalid value: ";
    private final Scanner scanner = new Scanner(System.in);

    private final List<Class<? extends Nature>> listClasses;
    private final Parameters parameters;

    public DialogueService(Parameters parameters, List<Class<? extends Nature>> listClasses) {
        this.parameters = parameters;
        this.listClasses = listClasses;
    }

    protected void changingField() {
        System.out.println("\nInput amount of cells: width \nand in the new string - height");

        int width = getValueOfParameter(MAX_VALUE);
        int height = getValueOfParameter(MAX_VALUE);
        changeSizeOfIsland(width, height);
    }

    protected void changeSizeOfIsland(int width, int height) {
        parameters.getIsland().setWidthSize(width);
        parameters.getIsland().setHeightSize(height);
        parameters.getIsland().setField(width, height);
    }

    protected void fillListOfClasses() {
        Set<Class<? extends Nature>> classes = parameters.getCacheNatureObj().keySet();
        listClasses.addAll(classes);
    }

    protected void changeAmount(Class<? extends Nature> aClass) {
        System.out.println("You selected to change amount " + aClass.getSimpleName() +
                ". Input value");
        Map<Class<? extends Nature>, Integer> cacheNatureObj = parameters.getCacheNatureObj();
        int sizeIsland = parameters.getIsland().getHeightOfField() * parameters.getIsland().getWidthOfField();
        int maxObjInCell = (int) parameters.getCacheSettings().get(aClass.getSimpleName()).get("maxObjInCell");
        int valueOfParameter = getValueOfParameter(sizeIsland * maxObjInCell);
        cacheNatureObj.put(aClass, valueOfParameter);
    }

    protected void changeWeight(Class<? extends Nature> aClass) {
        String weight = "weight";
        inputParameter(weight, aClass);
    }

    protected void changeMaximalAmountInCells(Class<? extends Nature> aClass) {
        String maxObjInCell = "maxObjInCell";
        inputParameter(maxObjInCell, aClass);
    }

    protected void changeRangeOfMove(Class<? extends Nature> aClass) {
        String rangeMove = "rangeMove";
        inputParameter(rangeMove, aClass);
    }

    protected void changeAmountOfNeedFood(Class<? extends Nature> aClass) {
        String amountNeedFood = "amountNeedFood";
        inputParameter(amountNeedFood, aClass);
    }

    protected void changeAmountOfChildren(Class<? extends Nature> aClass) {
        String amountChildren = "amountChildren";
        inputParameter(amountChildren, aClass);
    }

    protected void changeAmountCyclesOfLife(Class<? extends Nature> aClass) {
        String amountCycleLive = "amountCycleLive";
        inputParameter(amountCycleLive, aClass);
    }

    protected void changeRationAndProbability(Class<? extends Nature> aClass) {
        Map<String, Map<Class<? extends Nature>, Integer>> cacheRations = parameters.getCacheRations();
        String nameObject = aClass.getSimpleName();
        Map<Class<? extends Nature>, Integer> mapClassesAndProbability = cacheRations.get(nameObject);
        System.out.println("\nYou selected to change object from ration " + aClass.getSimpleName() +
                " and probability it's eating");

        while (true) {
            System.out.println("Choose number of object for it's adding into ration:");
            printListClasses();

            int maxValue = listClasses.size();
            int selectedRationObject = getValueOfParameter(maxValue);
            if (selectedRationObject == maxValue) {
                return;
            }
            System.out.println("Input probability of eating " + nameObject);
            int probability = getValueOfParameter(MAX_VALUE);
            mapClassesAndProbability.put(listClasses.get(selectedRationObject), probability);
        }
    }

    protected void printListClasses() {
        for (int i = 0; i < listClasses.size(); i++) {
            System.out.println(i + ". " + listClasses.get(i).getSimpleName());
        }
        System.out.println(listClasses.size() + ". for exit");
    }

    protected int getValueOfParameter(int maxValue) {
        while (true) {
            String value = null;
            try {
                value = scanner.nextLine();
                int number = getNumber(value);
                if (number >= 0 && number <= maxValue) {
                    return number;
                } else {
                    System.err.println("input value from 0 to " + maxValue);
                }
            } catch (ValueInvalidException ex) {
                messageToUserAboutError(ex, value);
            }
        }
    }

    protected void inputParameter(String parameter, Class<? extends Nature> aClass) {
        System.out.println("You selected to change " + parameter + " " + aClass.getSimpleName() +
                ". Input value");
        Map<String, Map<String, Number>> cacheSettings = parameters.getCacheSettings();
        Map<String, Number> mapOfParameters = cacheSettings.get(aClass.getSimpleName());
        while (true) {
            String value = null;
            try {
                value = scanner.nextLine();
                int number = getNumber(value);
                if (number >= 0) {
                    mapOfParameters.put(parameter, number);
                    System.out.println("Value of " + parameter + " changed. New value " + number);
                    break;
                }
                System.err.println("input positive number");
            } catch (ValueInvalidException ex) {
                messageToUserAboutError(ex, value);
            }
        }
    }

    protected int getNumber(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValueInvalidException("String does not contain a parsable integer", e);
        }
    }

    protected void messageToUserAboutError(Exception exception, String value) {
        System.out.println(MESSAGE + value);
        System.err.println(exception.getMessage());
    }
}
