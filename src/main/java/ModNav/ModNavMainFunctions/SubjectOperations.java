package ModNav.ModNavMainFunctions;

import ModNav.ModNavExceptions.InputOutOfRangeException;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.ModNavSubject;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.DatabaseInstance;
import ModNav.ModNavUtils.UserInputs;

import java.util.Optional;
import java.util.Scanner;

public class SubjectOperations {
    public static void editSubjectInfo(Scanner sc, ModNavSubject target, ModNavGraph g){
        System.out.printf("================== Editing %s ==================\n", target.getSubjectID());
        System.out.println("[1] Edit name");
        System.out.println("[2] Change Building");
        System.out.println("[3] Back to main menu");
        System.out.print("\n");

        Integer opts = null;

        while (opts == null){
            try {
                opts = UserInputs.getIntegerInput(sc, 1, 3, "> Option: ");
            }
            catch (NumberFormatException | InputOutOfRangeException e){
                System.out.println("Invalid Option!");
            }
        }

        switch (opts){
            case 1:
                editName(sc, target,g);
                break;
            case 2:
                changeBuilding(sc, target, g);
                break;
            default:
                break;
        }
    }

    private static void editName(Scanner sc, ModNavSubject target, ModNavGraph g){
        System.out.printf("Current name: %s\n", target.getSubjectName());
        String newName = "";

        newName = UserInputs.getLineInput(sc, "> Input New Name (leave blank to cancel): ");

        if (newName.isBlank()){
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to change '%s' name to %s ? [Y/n]: ", target.getSubjectID(), newName))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        target.setSubjectName(newName);

        DatabaseInstance db = new DatabaseInstance();
        db.saveSubject(target.getSubjectID(), target);
        db.close();

        System.out.printf("Set %s name to %s!\n", target.getSubjectID(), target.getSubjectName());
    }

    private static void changeBuilding(Scanner sc, ModNavSubject target, ModNavGraph g){
        Place p = target.getBuilding();
        System.out.printf("Current building details for %s:\n", target.getSubjectID());
        System.out.printf("Building ID: %s\n", p.getId());
        System.out.printf("Building Name: %s\n", p.getPrimaryName());
        System.out.print("\n");

        System.out.println("[1] Select place with BID");
        System.out.println("[2] Select place with name");
        System.out.println("[3] Abort and go back to main menu");

        Integer opts = null;

        while (opts == null){
            try {
                opts = UserInputs.getIntegerInput(sc, 1, 3, "> Option: ");
            }
            catch (NumberFormatException | InputOutOfRangeException e){
                System.out.println("Invalid Option!");
            }
        }

        switch (opts){
            case 1:
                selectNewBuildingWithID(sc, target, g);
                break;
            case 2:
                selectNewBuildingWithName(sc, target, g);
                break;
            default:
                break;
        }
    }

    private static void selectNewBuildingWithID(Scanner sc, ModNavSubject target, ModNavGraph g){
        String id = UserInputs.getLineInput(sc, "> Input Building ID: ");

        Optional<Place> res = g.getPlaceById(id);

        if (res.isEmpty()){
            System.out.printf("Place with ID '%s' not found!\n", id);
            return;
        }

        Place p = res.get();

        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to change '%s' building to %s ? [Y/n]: ", target.getSubjectID(), p.getId()))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        target.setNewBuilding(p);

        DatabaseInstance db = new DatabaseInstance();
        db.saveSubject(target.getSubjectID(), target);
        db.close();

        System.out.printf("Set %s building to %s!\n", target.getSubjectID(), target.getBuilding().getId());
    }

    private static void selectNewBuildingWithName(Scanner sc, ModNavSubject target, ModNavGraph g){
        String name = UserInputs.getLineInput(sc, "> Input Building Name: ");

        Optional<Place> res = g.findPlaceWithName(name);

        if (res.isEmpty()){
            System.out.printf("Place '%s' not found!\n", name);
            return;
        }

        Place p = res.get();

        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to change '%s' building to %s ? [Y/n]: ", target.getSubjectID(), p.getId()))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        target.setNewBuilding(p);

        DatabaseInstance db = new DatabaseInstance();
        db.saveSubject(target.getSubjectID(), target);
        db.close();

        System.out.printf("Set %s building to %s!\n", target.getSubjectID(), target.getBuilding().getId());
    }

    public static void removeSubject(Scanner sc, ModNavSubject target, ModNavGraph g){
        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to remove %s ? [Y/n]: ", target.getSubjectID()))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        String sid = target.getSubjectID();

        g.removeSubject(sid);

        DatabaseInstance db = new DatabaseInstance();
        db.removeSubjectRow(sid);
        db.close();

        System.out.printf("Removed %s!\n", sid);
    }
}
