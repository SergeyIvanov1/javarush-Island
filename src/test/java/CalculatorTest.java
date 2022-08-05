import org.junit.jupiter.api.Test;
import ru.javarush.sergeyivanov.island.Main.Calculator;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    @Test
    public void add() {
        Calculator calculator = new Calculator();
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }
    @Test
    public void sub() {
        Calculator calculator = new Calculator();
        int result = calculator.sub(10,10);
        assertEquals(0, result);
    }
    @Test
    public void mul () {
        Calculator calculator = new Calculator();
        int result = calculator.mul(-5,-3);
        assertEquals(15, result);
    }
    @Test
    public void div() {
        Calculator calculator = new Calculator();
        int result = calculator.div(2,3);
        assertEquals(0, result);
    }
}
