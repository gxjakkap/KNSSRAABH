package ModNav;

import ModNav.ModNavMainFunctions.MainMenuFunctions;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavUtils.DBQueryResult;
import ModNav.ModNavUtils.DatabaseInstance;
import ModNav.ModNavUtils.UserInputs;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //hi

        // Initiate
        DatabaseInstance db = new DatabaseInstance();
        ModNavGraph g = new ModNavGraph();
        DBQueryResult qRes = db.loadMapFromDB();
        g.setPlaceMap(qRes.getPlaceList());
        g.setList(qRes.getMap());
        db.close();

        // Main loop
        System.out.println("Welcome to KMUTTNavigationSuperSmartRetroAncientAutomaticButHandtomatic");
        System.out.println("(Or KNSSRAABH for short.)");
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("================== Main Menu ==================");
            System.out.println("* Search");
            System.out.println("[1] Search with BID");
            System.out.println("[2] Search with name\n");
            System.out.println("* Add and Edit");
            System.out.println("[3] Add new place");
            System.out.println("[4] Add new path\n");
            System.out.print("[5] Exit\n");

            int opts = UserInputs.getIntegerInput(sc, 1, 5, "> Option: ");

            if (opts == 1) {
                MainMenuFunctions.searchByBID(sc, g);
            }
            else if (opts == 2) {
                MainMenuFunctions.searchByName(sc, g);
            }
            else if (opts == 3) {
                MainMenuFunctions.addNewPlace(sc, g);
            }
            else if (opts == 4) {
                MainMenuFunctions.addPath(sc, g);
            }
            else if (opts == 5) {
                break;
            }
        }
        while (true);

        sc.close();

        // Cleanup & Saving
        System.out.println("Cleaning up...");
        db = new DatabaseInstance();
        db.saveMapToDB(g);
        db.close();
        System.out.println("Goodbye!");

        System.exit(0);
    }
}
