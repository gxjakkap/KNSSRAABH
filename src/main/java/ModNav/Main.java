package ModNav;

import ModNav.ModNavStructure.Path;
import ModNav.ModNavStructure.Place;
import ModNav.Structure.Graph;

public class Main {
    public static void main(String[] args) {
        //hi
        System.out.println("Hello World!");

        Place s4 = new Place("S4");

        System.out.printf("We're at %s", s4.getId());

        Graph g = new Graph();

        g.addVertex(s4);

        Place lg = new Place("LG");

        g.addVertex(lg);

        Path s4ToLg = new Path();
    }
}
