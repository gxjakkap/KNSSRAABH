package ModNav.ModNavMainFunctions;

import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.UserInputs;

import java.util.Optional;
import java.util.Scanner;

public class MainMenuFunctions {
    public static void searchByBID(Scanner sc, ModNavGraph g){
        String id = UserInputs.getLineInput(sc, "> Input Building ID: ");

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
                Directions.printOutDirection(sc, g, p);
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    public static void addNewPlace(Scanner sc, ModNavGraph g){
        String id = UserInputs.getLineInput(sc, "> Input Building ID: ");
        Place p = new Place(id);

        if (UserInputs.getYesNoAnswer(sc, true, "> Add place names [Y/n]: ")) {
            String name;
            do {
                name = UserInputs.getLineInput(sc, "> Name (leave blank to finish): ");
                if (!name.isBlank()) {
                    p.addName(name);
                }
            } while (!name.isBlank());
        }

        System.out.printf("Place %s added!\n", p.getPrimaryName());
    }

    public static void addPath(Scanner sc, ModNavGraph g){
        String oid = UserInputs.getLineInput(sc, "> Origin Building ID: ");
        Optional<Place> oo = g.getPlaceById(oid);

        if (oo.isEmpty()){
            throw new KeyDoesNotExistException();
        }

        Place og = oo.get();

        String did = UserInputs.getLineInput(sc, "> Destination Building ID: ");
        Optional<Place> od = g.getPlaceById(did);

        if (od.isEmpty()){
            throw new KeyDoesNotExistException();
        }

        Place dest = od.get();

        int distance = UserInputs.getIntegerInput(sc, 1, 1000, "> Distance (m): ");

        g.addPath(og, dest, distance);

        System.out.printf("Path from %s to %s with the distance of %d m added!\n", og.getId(), dest.getId(), distance);
    }
}
