package ModNav.Structure;

public class Edge <N extends Node> {
    protected N dest;
    protected int weight;

    public Edge(N dest, int weight){
        this.dest = dest;
        this.weight = weight;
    }

    public N getDest(){
        return this.dest;
    }

    public int getWeight(){
        return this.weight;
    }
}
