import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    public void test() {
        String input = "10 IMG\n15 FLAC\n13 VID";

        String result = new Calculator().calculate(input);

        System.out.println(result);

        assertEquals("10 IMG $800.0\n" +
                "\t1 x 10 $800.0\n" +
                "15 FLAC $1957.5\n" +
                "\t1 x 9 $1147.5\n" +
                "\t1 x 6 $810.0\n" +
                "13 VID $2670.0\n" +
                "\t1 x 9 $1530.0\n" +
                "\t2 x 3 $1140.0", result);
    }

}