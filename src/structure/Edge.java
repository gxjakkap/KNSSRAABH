package structure;

public abstract class Edge {
    private String dest;
    private int weight;
    private Object data;

    public Edge(String dest, int weight){
        this.dest = dest;
        this.data = new Object();
    }

    public Object getData(){
        return this.data;
    }
}
