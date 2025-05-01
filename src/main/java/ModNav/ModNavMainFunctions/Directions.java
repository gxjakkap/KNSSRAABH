package ModNav.ModNavMainFunctions;

import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Place;
import ModNav.ModNavAlgorithm.Djikstra;

import java.util.List;

public class Directions {
    public static void printOutDirection(ModNavGraph g, Place og, Place dest){
        Djikstra d = new Djikstra(g);

        d.ShortestPath(og);

        List<Place> lp = d.getPath(dest, d.getPrevious());

        System.out.printf("Shortest path to %s from %s is: ", dest.getPrimaryName(), og.getPrimaryName());
        
    }
}
