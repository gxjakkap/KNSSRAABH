package ModNav.Structure;

public abstract class Node {
    protected String id;

    public Node(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
}
