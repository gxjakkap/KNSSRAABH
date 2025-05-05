package ModNav.ModNavMainFunctions;

import ModNav.ModNavAlgorithm.Traverse;
import ModNav.ModNavExceptions.InputOutOfRangeException;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.DatabaseInstance;
import ModNav.ModNavUtils.UserInputs;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlaceOperations {
    public static void editPlaceInfo(Scanner sc, Place target, ModNavGraph g){
        System.out.printf("================== Editing %s ==================\n", target.getId());
        System.out.println("[1] Add name");
        System.out.println("[2] Edit name");
        System.out.printf("[3] Add subject to %s\n", target.getId());
        System.out.println("[4] Back to main menu");
        System.out.print("\n");

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
                addName(sc, target, g);
                break;
            case 2:
                editName(sc, target, g);
                break;
            case 3:
                break;
            default:
                break;
        }
    }


    private static void addName(Scanner sc, Place target, ModNavGraph g){
        System.out.printf("================== Add new name for %s ==================\n", target.getId());
        String newName = "";

        while (newName.isBlank()){
            newName = UserInputs.getLineInput(sc, "> Input New Name: ");

            if (newName.isBlank()){
                System.out.println("Invalid Name!");
            }
        }

        newName = newName.trim();

        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to add '%s' to %s name list? [Y/n]: ", newName, target.getId()))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        boolean asPrimary = UserInputs.getYesNoAnswer(sc, false, String.format("> Do you want to set '%s' as %s primary display name? [y/N]: ", newName, target.getId()));

        target.addName(newName, asPrimary);

        DatabaseInstance db = new DatabaseInstance();
        db.savePlaceToDB(target, g);

        db.close();

        System.out.printf("Added '%s' to %s name list%s", newName, target.getId(), (asPrimary ? " as primary display name\n" : "\n"));
    }

    private static void editName(Scanner sc, Place target, ModNavGraph g) {
        System.out.printf("================== Edit %s name list ==================\n", target.getId());
        List<String> nl = new ArrayList<>(target.getNames());

        if (nl.isEmpty()){
            System.out.printf("Current %s Name List:\n", target.getId());
            System.out.println("[empty]");
            return;
        }

        AtomicBoolean exit = new AtomicBoolean(false);

        while (!exit.get()) {
            System.out.printf("Current %s Name List:\n", target.getId());

            Integer opts = null;

            ListIterator<String> i = nl.listIterator();
            while (i.hasNext()) {
                int idx = i.nextIndex();
                String n = i.next();
                System.out.printf("* [%d] %s\n", idx, n);
            }


            while (opts == null) {
                try {
                    opts = UserInputs.getIntegerInput(sc, -1, nl.toArray().length, "> Select name to edit (put -1 to end or exit): ");
                } catch (NumberFormatException | InputOutOfRangeException e) {
                    System.out.println("Invalid Option!");
                }
            }

            if (opts == -1) {
                exit.set(true);
                break;
            }

            String selected = nl.get(opts);
            int selectedIndex = opts;
            System.out.printf("\nName selected: %s\n\n", selected);
            System.out.println("[1] Promote to primary name");
            System.out.println("[2] Edit name");
            System.out.println("[3] Remove name");
            System.out.println("[4] Back to main menu");

            opts = null;

            while (opts == null) {
                try {
                    opts = UserInputs.getIntegerInput(sc, 1, 4, "> Option: ");
                } catch (NumberFormatException | InputOutOfRangeException e) {
                    System.out.println("Invalid Option!");
                }
            }

            switch (opts) {
                case 1:
                    promoteNameToPrimary(nl, selectedIndex, target.getId());
                    break;
                case 2:
                    editNameInNamelist(sc, nl, selectedIndex, target.getId());
                    break;
                case 3:
                    removeNameFromNamelist(sc, nl, selectedIndex, target.getId());
                    break;
                case 4:
                    exit.set(true);
                    break;
                default:
                    break;
            }

            if (!exit.get()){
                target.setNames(nl);

                DatabaseInstance db = new DatabaseInstance();
                db.savePlaceToDB(target, g);
                db.close();
            }
        }
    }

    public static void removePlace(Scanner sc, Place target, ModNavGraph g){
        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to remove %s ? [Y/n]: ", target.getId()))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        ArrayList<Place> pl = Traverse.bfsList(g, target); // why not

        String pid = target.getId();
        DatabaseInstance db = new DatabaseInstance();
        db.removePlaceRow(target);
        pl.forEach(p -> {
            if (!p.equals(target)){
                g.removePath(target, p);
                db.savePlaceToDB(p, g);
            }
        });
        db.close();
        g.removePlace(target);

        System.out.printf("Removed %s!\n", pid);
    }

    private static void promoteNameToPrimary(List<String> nl, int sIdx, String bid){
        String selected = nl.get(sIdx);
        nl.remove(sIdx);
        nl.addFirst(selected);

        System.out.printf("Promoted %s to the new be primary name of %s\n", selected, bid);
    }

    private static void editNameInNamelist(Scanner sc, List<String> nl, int sIdx, String bid){
        String oldName = nl.get(sIdx);
        String newName = "";

        while (newName.isBlank()){
            newName = UserInputs.getLineInput(sc, "> Input New Name: ");

            if (newName.isBlank()){
                System.out.println("Invalid Name!");
            }
        }

        newName = newName.trim();

        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to replace '%s' with %s ? [Y/n]: ", oldName, newName))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        nl.set(sIdx, newName);
        System.out.printf("Replace %s with %s in %s name list!\n", oldName, newName, bid);
    }

    private static void removeNameFromNamelist(Scanner sc, List<String> nl, int sIdx, String bid){
        String selected = nl.get(sIdx);
        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to remove '%s' from %s name list ? [Y/n]: ", selected, bid))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        nl.remove(sIdx);
        System.out.printf("Removed %s from %s name list!\n", selected, bid);
    }

//    private static void addSubject(Scanner sc, ModNavGraph g, Place target){
//
//    }
}
