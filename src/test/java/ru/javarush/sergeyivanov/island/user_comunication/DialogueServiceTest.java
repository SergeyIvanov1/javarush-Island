package ru.javarush.sergeyivanov.island.user_comunication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.javarush.sergeyivanov.island.content_of_island.exceptions.ValueInvalidException;
import ru.javarush.sergeyivanov.island.content_of_island.fauna.predatory_animals.Wolf;
import ru.javarush.sergeyivanov.island.content_of_island.field.Island;
import ru.javarush.sergeyivanov.island.inicialization.Parameters;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DialogueServiceTest {

    public DialogueService dialogueService;

    @BeforeEach
    public void init(){
        dialogueService = new DialogueService(
                new Parameters(new Island()), new ArrayList<>());
    }

    @Test
    void changeSizeOfIsland() {
    }

    @Test
    void test_changeAmount_then_argument_is_null_throwNPE() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.changeAmount(null);
        });
    }

    @Test
    void test_changeWeight_then_argument_is_null_throwNPE() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.changeWeight(null);
        });
    }

    @Test
    void test_changeMaximalAmountInCells_then_argument_is_null_throwNPE() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.changeMaximalAmountInCells(null);
        });
    }

    @Test
    void test_changeRangeOfMove_then_argument_is_null_throwNPE() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.changeRangeOfMove(null);
        });
    }

    @Test
    void test_changeAmountOfNeedFood_then_argument_is_null_throwNPE() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.changeAmountOfNeedFood(null);
        });
    }

    @Test
    void test_changeAmountOfChildren_then_argument_is_null_throwNPE() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.changeAmountOfChildren(null);
        });
    }

    @Test
    void test_changeAmountCyclesOfLife_then_argument_is_null_throwNPE() {
        assertThrows(NullPointerException.class, () -> {
           dialogueService.changeAmountCyclesOfLife(null);
        });
    }

    @Test
    void test_inputParameter_throw_NPE_if_first_parameter_is_null() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.inputParameter(null, Wolf.class);
        });
    }

    @Test
    void test_inputParameter_throw_NPE_if_second_parameter_is_null() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.inputParameter("string", null);
        });
    }

    @Test
    void test_inputDoubleValue_throw_NPE_if_first_parameter_is_null() {
        assertThrows(NullPointerException.class, () -> {
           dialogueService.inputDoubleValue(null, Wolf.class);
        });
    }

    @Test
    void test_inputDoubleValue_throw_NPE_if_second_parameter_is_null() {
        assertThrows(NullPointerException.class, () -> {
            dialogueService.inputDoubleValue("string", null);
        });
    }

    @Nested
    class TestGetInt {

        @Test
        void throw_NullPointerException_if_parameter_is_null() {
            assertThrows(ValueInvalidException.class, () -> {
                dialogueService.getInt(null);
            });
        }

        @Test
        void throw_ValueInvalidException_if_parameter_does_not_contains_parsable_int() {
            assertThrows(ValueInvalidException.class, () -> {
               dialogueService.getInt("number");
            });
        }

        @Test
        void test_getInt_then_argument_is_positive_but_less_then_max() {
            int expected = 3;
            int actual = dialogueService.getInt("3");

            assertEquals(expected, actual);
        }
    }

    @Nested
    class TestGetDouble {

        @Test
        void test_getDouble_throw_NullPointerException_if_parameter_is_null() {
            assertThrows(ValueInvalidException.class, () -> {
                dialogueService.getDouble(null);
            });
        }

        @Test
        void test_getDouble_throw_ValueInvalidException_if_parameter_does_not_contains_parsable_int() {
            assertThrows(ValueInvalidException.class, () -> {
                dialogueService.getDouble("number");
            });
        }

        @Test
        void test_getDouble__then_argument_is_positive_but_less_then_max() {
            double expected = 3.5;
            double actual = dialogueService.getDouble("3.5");

            assertEquals(expected, actual);
        }
    }
}
