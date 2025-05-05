package ModNav.ModNavMainFunctions;

import ModNav.ModNavExceptions.InputOutOfRangeException;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.UserInputs;

import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class PlaceOperations {
    public static void editPlaceInfo(Scanner sc, Place target){
        System.out.printf("================== Editing %s ==================\n", target.getId());
        System.out.print("[1] Edit ID\t\t\t[2] Add name\n");
        System.out.print("[3] Edit name\t\t\t[4] Back to main menu\n");

        Integer opts = null;

        while (opts == null){
            try {
                opts = UserInputs.getIntegerInput(sc, 1, 4, "> Option: ");
            }
            catch (NumberFormatException | InputOutOfRangeException e){
                System.out.println("Invalid Option!");
            }
        }

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
        String newID = UserInputs.getLineInput(sc, "> New ID (leave blank to go back): ");

        if (newID.isBlank()) return;

        if (!UserInputs.getYesNoAnswer(sc, false, String.format("> Do you really want to change '%s' to '%s' [y/N]: ", target.getId(), newID))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        target.setNewID(newID);

        System.out.printf("New id of '%s' is set to '%s'\n", target.getPrimaryName(), target.getId());
    }

    private static void addName(Scanner sc, Place target){
        System.out.printf("================== Add new name for %s ==================\n", target.getId());
        String newName = "";

        while (newName.isBlank()){
            newName = UserInputs.getLineInput(sc, "> Input New Name: ");

            if (newName.isBlank()){
                System.out.println("Invalid Name!");
            }
        }

        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to add '%s' to %s name list? [Y/n]: ", newName, target.getId()))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        boolean asPrimary = UserInputs.getYesNoAnswer(sc, false, String.format("> Do you want to set '%s' as %s primary display name? [y/N]: ", newName, target.getId()));

        target.addName(newName, asPrimary);

        System.out.printf("Added '%s' to %s name list%s", newName, target.getId(), (asPrimary ? " as primary display name\n" : "\n"));
    }

    private static void editName(Scanner sc, Place target){
        System.out.printf("================== Edit %s name list ==================\n", target.getId());
        List<String> nl = target.getNames();
        ListIterator<String> i = nl.listIterator();

        while (i.hasNext()){
            int idx = i.nextIndex();
            String n = i.next();
            System.out.printf("* [%d] %s\n", idx, n);
        }
    }
}
