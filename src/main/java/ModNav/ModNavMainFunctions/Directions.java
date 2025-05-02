package ModNav.ModNavMainFunctions;

import ModNav.ModNavExceptions.InputOutOfRangeException;
import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavAlgorithm.Djikstra;
import ModNav.ModNavUtils.UserInputs;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Directions {
    public static void  printOutDirection(Scanner sc, ModNavGraph g, Place dest){
        System.out.printf("================== Destination: %s ==================\n", dest.getPrimaryName());
        System.out.print("[1] Select place with ID\t\t\t[2] Search place with names\n");

        Integer opts = null;

        while (opts == null){
            try {
                opts = UserInputs.getIntegerInput(sc, 1, 2, "> Option: ");
            }
            catch (NumberFormatException | InputOutOfRangeException e){
                System.out.println("Invalid Option!");
            }
        }

        Optional<Place> pog = (opts == 1) ? g.getPlaceById(promptForPlaceID(sc)) : g.findPlaceWithName(promptForName(sc));

        if (pog.isEmpty()){
            throw new KeyDoesNotExistException();
        }

        Place og = pog.get();

        Djikstra d = new Djikstra(g);

        Map<Place, Integer> wm = d.ShortestPath(og);

        List<Place> lp = d.getPath(dest, d.getPrevious());

        System.out.println("Path found!");
        System.out.printf("Shortest path from %s to %s is:\n", og.getPrimaryName(), dest.getPrimaryName());
        d.printPath(dest);
        System.out.printf(" (%d m)\n", d.getWeight(wm, dest));

        System.out.print("================== Options ==================\n");
        System.out.print("[1] View route breakdown\t\t\t[2] Back to main menu\n");

        opts = null;
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
                d.pathBreakdown(dest);
                break;
            default:
                break;
        }
    }

    public static String promptForPlaceID(Scanner sc){
        return UserInputs.getLineInput(sc, "> Building ID: ");
    }

    public static String promptForName(Scanner sc){
        return UserInputs.getLineInput(sc, "> Building Name: ");
    }
}
