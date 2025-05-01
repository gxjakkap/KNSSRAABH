package ModNav.ModNavMainFunctions;

import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.UserInputs;

import java.util.Optional;
import java.util.Scanner;

public class SearchByBID {
    public static void initiate(Scanner sc, ModNavGraph g){
        System.out.print("Input Building ID: ");
        String id = sc.next();

        Optional<Place> res = g.getPlaceById(id);

        if (res.isEmpty()){
            System.out.printf("Place with ID '%s' not found!", id);
            return;
        }

        Place p = res.get();

        System.out.println("Place found!");
        System.out.printf("ID: %s\n", p.getId());
        System.out.printf("Name: %s\n", p.getPrimaryName());

        System.out.printf("================== Options for %s ==================\n", p.getId());
        System.out.print("[1] Get directions\t\t\t[2] Edit place info\n");
        System.out.print("[3] Delete place\t\t\t[4] Back to main menu\n");

        int opts = UserInputs.getIntegerInput(sc, 1, 4, "> Option: ");
        switch (opts){
            case 1:

                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
