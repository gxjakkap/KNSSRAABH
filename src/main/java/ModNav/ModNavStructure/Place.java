package ModNav.ModNavStructure;

import ModNav.Structure.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Place extends Node {
    private List<String> names;

    public Place(String id){
        super(id);
        this.names = new ArrayList<>();
        this.names.add(id);
    }

    public Place(String id, String[] names){
        super(id);
        this.names = new ArrayList<>();
        this.names.addAll(Arrays.asList(names));
    }

    public void addName(String name){
        this.names.add(name);
    }

    public void setNames(List<String> names){
        this.names = names;
    }
}
