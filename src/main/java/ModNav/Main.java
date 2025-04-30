package ModNav;

import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Place;

public class Main {
    public static void main(String[] args) {
        //hi
        System.out.println("Hello World!");

        Place s4 = new Place("S4");

        System.out.printf("We're at %s", s4.getId());

        ModNavGraph g = new ModNavGraph();

        g.addPlace(s4);

        Place lg = new Place("LG");

        g.addPlace(lg);

        g.addPath(s4, lg, 63);

        System.out.printf("Distance from %s to %s is %s m.\n", s4.getId(), lg.getId(), g.getPathsFromPlace(s4));

    }
}
