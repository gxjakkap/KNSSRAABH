package ModNav;

import ModNav.ModNavStructure.Place;

public class Main {
    public static void main(String[] args) {
        //hi
        System.out.println("Hello World!");

        Place p = new Place("S4");

        System.out.printf("We're at %s", p.getId());
    }
}
