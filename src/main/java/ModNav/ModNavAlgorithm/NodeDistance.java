package ModNav.ModNavAlgorithm;

import ModNav.Structure.Node;

public class NodeDistance <N extends  Node> implements Comparable<NodeDistance<Node>>
{
    private N node;
    private int Distance;

    public NodeDistance(N node, int Distance)
    {
        this.node = node;
        this.Distance = Distance;
    }

    public N getNode()
    {
        return this.node;
    }

    public int getDist()
    {
        return this.Distance;
    }

    @Override
    public int compareTo(NodeDistance otherNode)
    {
        return Integer.compare(this.Distance, otherNode.Distance);
    }

}