package ModNav.ModNavUtils;

import ModNav.ModNavExceptions.InputOutOfRangeException;

import java.util.Scanner;

public class UserInputs {
    public static int getIntegerInput(Scanner sc, int min, int max){
        int inp = sc.nextInt();

        if (inp < min || inp > max){
            throw new InputOutOfRangeException("Input " + String.valueOf(inp) + " is out of range");
        }

        return inp;
    }

    public static int getIntegerInput(Scanner sc, int min, int max,  String prefix){
        System.out.print(prefix);
        return getIntegerInput(sc, min, max);
    }
}
