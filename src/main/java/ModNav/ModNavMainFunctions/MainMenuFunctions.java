package ModNav.ModNavMainFunctions;

import ModNav.ModNavExceptions.EdgeAlreadyExistedException;
import ModNav.ModNavExceptions.InputOutOfRangeException;
import ModNav.ModNavExceptions.KeyAlreadyExistedException;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.ModNavSubject;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.ConsoleUtils;
import ModNav.ModNavUtils.DatabaseInstance;
import ModNav.ModNavUtils.UserInputs;

import java.util.Optional;
import java.util.Scanner;

public class MainMenuFunctions {
    public static void placeFound(Scanner sc, ModNavGraph g, Place p){
        System.out.println("\nPlace found!");
        System.out.printf("ID: %s\n", p.getId());
        System.out.printf("Name: %s\n", p.getPrimaryName());

        System.out.printf("================== Options for %s ==================\n", p.getId());
        System.out.println("* Directions");
        System.out.println("[1] Get directions");
        System.out.print("\n");
        System.out.println("* Edit Place Information");
        System.out.println("[2] Edit place info");
        System.out.println("[3] Delete place");
        System.out.println("[4] Edit Path");
        System.out.print("\n");
        System.out.println("[5] Back to main menu");

        Integer opts = null;
        while (opts == null){
            try {
                opts = UserInputs.getIntegerInput(sc, 1, 5, "> Option: ");
            }
            catch (NumberFormatException | InputOutOfRangeException e){
                System.out.println("Invalid Option!");
            }
        }
        switch (opts){
            case 1:
                Directions.printOutDirection(sc, g, p);
                break;
            case 2:
                PlaceOperations.editPlaceInfo(sc, p, g);
                break;
            case 3:
                PlaceOperations.removePlace(sc, p, g);
                break;
            case 4:
                PathOperations.editPath(sc, g, p);
                break;
            default:
                break;
        }
    }

    public static void searchByBID(Scanner sc, ModNavGraph g){
        ConsoleUtils.clearScreen();
        System.out.println("================== Search by BID ==================");
        String id = UserInputs.getLineInput(sc, "> Input Building ID: ");

        id = id.toUpperCase().trim();

        Optional<Place> res = g.getPlaceById(id);

        if (res.isEmpty()){
            System.out.printf("Place with ID '%s' not found!\n", id);
            return;
        }

        Place p = res.get();

        placeFound(sc, g, p);
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

        Optional<Place> res = g.findPlaceWithName(name);

        if (res.isEmpty()){
            System.out.printf("Place '%s' not found!\n", name);
            return;
        }

        Place p = res.get();

        placeFound(sc, g, p);
    }

    public static void searchBySubject(Scanner sc, ModNavGraph g){
        ConsoleUtils.clearScreen();
        System.out.println("================== Search by Subject Name ==================");
        String sid = "";

        while (sid.isBlank()){
            sid = UserInputs.getLineInput(sc, "> Input Subject Name: ");

            if (sid.isBlank()){
                System.out.println("Invalid Subject!");
            }
        }

        sid = sid.toUpperCase().trim();

        Optional<ModNavSubject> sub = g.getSubjectData(sid);
        if (sub.isEmpty()){
            System.out.printf("Subject '%s' was not found in our record!\n", sid);
            return;
        }

        String sn = sub.get().getSubjectName();
        Place p = sub.get().getBuilding();

        System.out.println("\nSubject found!");
        System.out.printf("ID: %s\n", sid);
        System.out.printf("Name: %s\n", sn);
        System.out.printf("Building ID: %s\n", p.getId());
        System.out.printf("Building Name: %s\n\n", p.getPrimaryName());

        System.out.printf("================== Options for %s at %s ==================\n", sid, p.getId());
        System.out.println("* Directions");
        System.out.println("[1] Get directions");
        System.out.print("\n");
        System.out.println("* Edit Subject Information");
        System.out.printf("[2] Edit info for %s\n", sid);
        System.out.printf("[3] Remove %s from record\n", sid);
        System.out.print("\n");
        System.out.printf("* Edit Place Information (%s)\n", p.getId());
        System.out.println("[4] Edit place info");
        System.out.println("[5] Delete place");
        System.out.print("\n");
        System.out.println("[6] Back to main menu");

        Integer opts = null;
        while (opts == null){
            try {
                opts = UserInputs.getIntegerInput(sc, 1, 6, "> Option: ");
            }
            catch (NumberFormatException | InputOutOfRangeException e){
                System.out.println("Invalid Option!");
            }
        }
        switch (opts){
            case 1:
                Directions.printOutDirection(sc, g, p);
                break;
            case 2:
                SubjectOperations.editSubjectInfo(sc, sub.get(), g);
                break;
            case 3:
                SubjectOperations.removeSubject(sc, sub.get(), g);
                break;
            case 4:
                PlaceOperations.editPlaceInfo(sc, p, g);
                break;
            case 5:
                PlaceOperations.removePlace(sc, p, g);
            default:
                break;
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

        id = id.toUpperCase().trim();

        Place p = new Place(id);

        if (UserInputs.getYesNoAnswer(sc, true, "> Add place names [Y/n]: ")) {
            String name;
            do {
                name = UserInputs.getLineInput(sc, "> Name (leave blank to finish): ");
                if (!name.isBlank()) {
                    p.addName(name, false);
                }
            }
            while (!name.isBlank());
        }

        try {
            g.addPlace(p);
            DatabaseInstance db = new DatabaseInstance();
            db.savePlaceToDB(p, g);
            db.close();
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
        Optional<Place> oo;

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
        Optional<Place> od;

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

        int distance;

        while (true){
            distance = UserInputs.getIntegerInput(sc, 1, 10000, "> Distance between places: ");

            if (distance < 1 || distance > 10000){
                System.out.println("Invalid distance!");
            }
            else {
                break;
            }
        }

        try {
            g.addPath(og, dest, distance);
            DatabaseInstance db = new DatabaseInstance();
            db.savePlaceToDB(og, g);
            db.savePlaceToDB(dest, g);
            db.close();
        }
        catch (EdgeAlreadyExistedException e){
            System.out.println(e.getMessage());
            return;
        }


        System.out.printf("Path from %s to %s with the distance of %d m added!\n", og.getId(), dest.getId(), distance);
    }
}
