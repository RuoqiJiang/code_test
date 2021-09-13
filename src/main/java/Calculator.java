import java.util.Scanner;

public class Calculator {



    public String calculate(String input) {
        if (input == null || input.length() == 0) {
            return "no result";
        }
        return new Processor().process(new InputReader().getInputItems(input));
    }

    public static void main (String[] args) {

        Scanner sc = new Scanner(System.in);

        String input = "10 IMG\n15 FLAC\n13 VID";

        String result = new Calculator().calculate(input);

        System.out.println(result);

    }


}

