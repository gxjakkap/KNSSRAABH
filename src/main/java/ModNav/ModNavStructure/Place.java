package ModNav.ModNavStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ModNav.Structure.Node;

public class Place extends Node {
    private List<String> names;

    public Place(String id){
        super(id);
        this.names = new ArrayList<>();
    }

    public Place(String id, String[] names){
        super(id);
        this.names = new ArrayList<>();
        this.names.addAll(Arrays.asList(names));
    }

    // Workaround for Gunt's mistake on parsing the place list back from DB
    // and having different memory address so its incomparable

//    @Override
//    public boolean equals(Object o)
//    {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Place place = (Place) o;
//        return this.id.equals(place.id);
//    }
//
//    @Override
//    public int hashCode()
//    {
//        return this.id.hashCode();
//    }

    // endsection

    public void addName(String name, boolean asPrimaryName){
        if (asPrimaryName){
            this.names.addFirst(name);
        }
        else {
            this.names.add(name);
        }
    }

    public void setNames(List<String> names){
        this.names = names;
    }

    public String getPrimaryName(){
        if (this.names.isEmpty()) return this.id;
        return this.names.getFirst();
    }

    public List<String> getNames(){
        return Collections.unmodifiableList(this.names);
    }
}
