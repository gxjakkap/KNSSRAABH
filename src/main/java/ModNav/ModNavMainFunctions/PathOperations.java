package ModNav.ModNavMainFunctions;

import ModNav.ModNavExceptions.InputOutOfRangeException;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Path;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavUtils.DatabaseInstance;
import ModNav.ModNavUtils.UserInputs;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PathOperations {
    public static  void editPath(Scanner sc, ModNavGraph g, Place target){
        System.out.printf("================== Edit %s paths ==================\n", target.getId());
        List<Path> pl = g.getPathsFromPlace(target);

        if (pl.isEmpty()){
            System.out.printf("Current %s Path List:\n", target.getId());
            System.out.println("[empty]");
            return;
        }

        AtomicBoolean exit = new AtomicBoolean(false);

        while (!exit.get()) {
            pl = g.getPathsFromPlace(target);
            System.out.printf("Current %s Path List:\n", target.getId());

            Integer opts = null;

            ListIterator<Path> i = pl.listIterator();
            while (i.hasNext()) {
                int idx = i.nextIndex();
                Path path = i.next();
                System.out.printf("* [%d] %s <-> %s\n", idx, target.getId(), path.getDest().getId());
            }

            System.out.print("\n");

            while (opts == null) {
                try {
                    opts = UserInputs.getIntegerInput(sc, -1, pl.toArray().length, "> Select path to edit (put -1 to end or exit): ");
                } catch (NumberFormatException | InputOutOfRangeException e) {
                    System.out.println("Invalid Option!");
                }
            }

            if (opts == -1) {
                exit.set(true);
                break;
            }

            Path selected = pl.get(opts);
            int selectedIndex = opts;
            System.out.printf("\nPath selected: %s <-> %s\n\n", target.getId(), selected.getDest().getId());
            System.out.println("[1] Edit Distance");
            System.out.println("[2] Remove Path");
            System.out.println("[3] Back to main menu");

            opts = null;

            while (opts == null) {
                try {
                    opts = UserInputs.getIntegerInput(sc, 1, 3, "> Option: ");
                } catch (NumberFormatException | InputOutOfRangeException e) {
                    System.out.println("Invalid Option!");
                }
            }

            switch (opts) {
                case 1:
                    editWeight(sc, g, selected, target);
                    break;
                case 2:
                    removePath(sc, g, selected, target);
                    break;
                case 3:
                    exit.set(true);
                    break;
                default:
                    break;
            }
        }
    }
    private static void editWeight(Scanner sc, ModNavGraph g, Path target, Place origin){
        System.out.printf("Path Selected: %s <-> %s\n", origin.getId(), target.getDest().getId());
        System.out.printf("Current distance: %dm\n", target.getWeight());

        Integer newWeight = null;

        while (newWeight == null) {
            try {
                newWeight = UserInputs.getIntegerInput(sc, 0, Integer.MAX_VALUE, "> Input new distance (put 0 to cancel): ");
            }
            catch (NumberFormatException | InputOutOfRangeException e) {
                System.out.println("Invalid Option!");
            }
        }

        if (newWeight == 0){
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        System.out.printf("Staged change for %s <-> %s: %dm -> %dm\n", origin.getId(), target.getDest().getId(), target.getWeight(), newWeight);

        if (!UserInputs.getYesNoAnswer(sc, true, "Confirm changes? [Y/n]: ")) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        int oldWeight = target.getWeight();

        Place dest = target.getDest();
        g.setNewWeight(origin, target, newWeight);

        DatabaseInstance db = new DatabaseInstance();
        db.savePlaceToDB(origin, g);
        db.savePlaceToDB(dest, g);
        db.close();

        System.out.printf("Changed distance for %s <-> %s from %dm -> %dm", origin.getId(), dest.getId(), oldWeight, newWeight);
    }

    private static void removePath(Scanner sc, ModNavGraph g, Path target, Place origin){
        if (!UserInputs.getYesNoAnswer(sc, true, String.format("> Do you really want to remove %s <-> %s ? [Y/n]: ", origin.getId(), target.getDest().getId()))) {
            System.out.println("Operation aborted! Going back to main menu.");
            return;
        }

        Place dest = target.getDest();

        g.removePath(origin, dest);

        DatabaseInstance db = new DatabaseInstance();
        db.savePlaceToDB(origin, g);
        db.savePlaceToDB(dest, g);
        db.close();

        System.out.printf("Removed %s <-> %s!\n", origin.getId(), dest.getId());
    }
}
