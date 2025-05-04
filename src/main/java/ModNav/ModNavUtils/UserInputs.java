package ModNav.ModNavUtils;

import ModNav.ModNavExceptions.InputOutOfRangeException;

import java.util.Scanner;

public class UserInputs {
    public static int getIntegerInput(Scanner sc, int min, int max){
        String inp = sc.nextLine().trim();

        int pinp = Integer.parseInt(inp);

        if (pinp < min || pinp > max){
            throw new InputOutOfRangeException("Input " + inp + " is out of range");
        }

        return pinp;
    }

    public static int getIntegerInput(Scanner sc, int min, int max, String prefix){
        System.out.print(prefix);
        return getIntegerInput(sc, min, max);
    }

    public static String getLineInput(Scanner sc){
        return sc.nextLine();
    }

    public static String getLineInput(Scanner sc, String prefix){
        System.out.print(prefix);
        return getLineInput(sc);
    }

    public static boolean getYesNoAnswer(Scanner sc, boolean yesAsDefault){
        String inp = sc.nextLine();

        if (yesAsDefault){
            return !inp.equalsIgnoreCase("n");
        }
        else {
            return inp.equalsIgnoreCase("y");
        }
    }

    public static boolean getYesNoAnswer(Scanner sc, boolean yesAsDefault, String prefix){
        System.out.print(prefix);
        return getYesNoAnswer(sc, yesAsDefault);
    }
}
