package ModNav.ModNavMainFunctions;

import ModNav.ModNavExceptions.EdgeAlreadyExistedException;
import ModNav.ModNavExceptions.KeyAlreadyExistedException;
import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.ConsoleUtils;
import ModNav.ModNavUtils.UserInputs;

import java.io.Console;
import java.util.Optional;
import java.util.Scanner;

public class MainMenuFunctions {
    public static void searchByBID(Scanner sc, ModNavGraph g){
        ConsoleUtils.clearScreen();
        System.out.println("================== Search by BID ==================");
        String id = UserInputs.getLineInput(sc, "> Input Building ID: ");

        Optional<Place> res = g.getPlaceById(id);

        if (res.isEmpty()){
            System.out.printf("Place with ID '%s' not found!\n", id);
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

    public static void searchByName(Scanner sc, ModNavGraph g){
        ConsoleUtils.clearScreen();
        System.out.println("================== Search by Place Name ==================");
        String name = "";

        while (name.isBlank()){
            name = UserInputs.getLineInput(sc, "> Input Name: ");

            if (name.isBlank()){
                System.out.println("Invalid Name!");
            }
        }
    }

    public static void addNewPlace(Scanner sc, ModNavGraph g){
        ConsoleUtils.clearScreen();
        System.out.println("================== Add new place ==================");
        String id = "";

        while (id.isBlank()){
            id = UserInputs.getLineInput(sc, "> Input Building ID: ");
            if (id.length() < 2){
                System.out.println("Invalid Building ID");
                id = "";
            }
        }

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

        try {
            g.addPlace(p);
        }
        catch (KeyAlreadyExistedException e) {
            System.out.print(e.getMessage());
            return;
        }

        System.out.printf("Place %s added!\n", p.getPrimaryName());
    }

    public static void addPath(Scanner sc, ModNavGraph g){
        ConsoleUtils.clearScreen();
        System.out.println("================== Add new path ==================");
        String oid ;
        Optional<Place> oo = Optional.empty();

        while (true){
            oid = UserInputs.getLineInput(sc, "> Origin Building ID: ");
            oo = g.getPlaceById(oid);
            if (oo.isEmpty()){
                System.out.printf("Place with ID %s does not exist!\n", oid);
            }
            else{
                break;
            }
        }

        Place og = oo.get();

        String did;
        Optional<Place> od = Optional.empty();

        while (true){
            did = UserInputs.getLineInput(sc, "> Destination Building ID: ");
            od = g.getPlaceById(did);

            if (od.isEmpty()){
                System.out.printf("Place with ID %s does not exist!\n", did);
            }
            else {
                break;
            }
        }

        Place dest = od.get();

        Integer distance = null;

        while (true){
            distance = UserInputs.getIntegerInput(sc, 1, 10000, "> Destination Building ID: ");

            if (distance < 1 || distance > 10000){
                System.out.println("Invalid distance!");
            }
            else {
                break;
            }
        }

        try {
            g.addPath(og, dest, distance);
        }
        catch (EdgeAlreadyExistedException e){
            System.out.println(e.getMessage());
            return;
        }


        System.out.printf("Path from %s to %s with the distance of %d m added!\n", og.getId(), dest.getId(), distance);
    }
}
