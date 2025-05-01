package ModNav.ModNavMainFunctions;

import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.UserInputs;

import java.util.Scanner;

public class PlaceOperations {
    public static void editPlaceInfo(Scanner sc, Place target){
        System.out.printf("================== Editing %s ==================\n", target.getId());
        System.out.print("[1] Edit ID\t\t\t[2] Add name\n");
        System.out.print("[3] Edit name\t\t\t[4] Back to main menu\n");

        int opts = UserInputs.getIntegerInput(sc, 1, 4, "> Option: ");

        switch (opts){
            case 1:
                editID(sc, target);
                break;
            case 2:
                addName(sc, target);
                break;
            case 3:
                editName(sc, target);
                break;
            default:
                break;
        }
    }

    private static void editID(Scanner sc, Place target){
        System.out.printf("Current ID: %s\n", target.getId());
        String newID = UserInputs.getLineInput(sc, "> New ID: ");
    }

    private static void addName(Scanner sc, Place target){

    }

    private static void editName(Scanner sc, Place target){

    }
}
