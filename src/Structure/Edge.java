package Structure;

public abstract class Edge {
    private Node dest;
    private int weight;

    public Edge(Node dest, int weight){
        this.dest = dest;
        this.weight = weight;
    }

    public Node getDest(){
        return this.dest;
    }

    public int getWeight(){
        return this.weight;
    }
}
