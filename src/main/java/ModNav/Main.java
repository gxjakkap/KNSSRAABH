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
        System.out.println("""
                
                
                  _  ___   _  _____ _____ _____                      ____  _    _\s
                 | |/ / \\ | |/ ____/ ____|  __ \\     /\\        /\\   |  _ \\| |  | |
                 | ' /|  \\| | (___| (___ | |__) |   /  \\      /  \\  | |_) | |__| |
                 |  < | . ` |\\___ \\\\___ \\|  _  /   / /\\ \\    / /\\ \\ |  _ <|  __  |
                 | . \\| |\\  |____) |___) | | \\ \\  / ____ \\  / ____ \\| |_) | |  | |
                 |_|\\_\\_| \\_|_____/_____/|_|  \\_\\/_/    \\_\\/_/    \\_\\____/|_|  |_|
                                                                                 \s
                                                                                 \s
                
                """);
        System.out.println("(KMUTTNavigationSuperSmartRetroAncientAutomaticButHandtomatic)");
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("================== Main Menu ==================");
            System.out.println("* Search");
            System.out.println("[1] Search with BID");
            System.out.println("[2] Search with name");
            System.out.println("[3] Search with subject ID\n");
            System.out.println("* Add and Edit");
            System.out.println("[4] Add new place");
            System.out.println("[5] Add new path\n");
            System.out.print("[6] Exit\n");

            int opts = UserInputs.getIntegerInput(sc, 1, 6, "> Option: ");

            if (opts == 1) {
                MainMenuFunctions.searchByBID(sc, g);
            }
            else if (opts == 2) {
                MainMenuFunctions.searchByName(sc, g);
            }
            else if (opts == 3){
                MainMenuFunctions.searchBySubject(sc, g);
            }
            else if (opts == 4) {
                MainMenuFunctions.addNewPlace(sc, g);
            }
            else if (opts == 5) {
                MainMenuFunctions.addPath(sc, g);
            }
            else if (opts == 6) {
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
