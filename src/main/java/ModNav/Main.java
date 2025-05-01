package ModNav;

import ModNav.ModNavMainFunctions.MainMenuFunctions;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavUtils.DatabaseInstance;
import ModNav.ModNavUtils.UserInputs;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //hi

        // Initiate
        DatabaseInstance db = new DatabaseInstance();
        ModNavGraph g = new ModNavGraph();

        // Main loop
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to KMUTTNavigationSuperSmartRetroAncientAutomaticButHandtomatic");
        System.out.println("(Or KNSSRAABH for short.)");

        System.out.println("================== Main Menu ==================");
        System.out.print("[1] Search with BID\t\t\t[2] Search with name\n");
        System.out.print("[3] Add new place\t\t\t[4] Add new path\n");

        int opts = UserInputs.getIntegerInput(sc, 1, 4,"> Option: ");

        switch (opts){
            case 1:
                MainMenuFunctions.searchByBID(sc, g);
                break;
            case 2:
                break;
            case 3:
                MainMenuFunctions.addNewPlace(sc, g);
                break;
            case 4:
                MainMenuFunctions.addPath(sc, g);
                break;
            default:
                break;
        }

    }
}
