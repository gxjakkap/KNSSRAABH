package ModNav.ModNavStructure.Algorithm;

import ModNav.Structure.Node;

public class NodeDistance implements Comparable<NodeDistance>
{
    private Node node;
    private int Distance;

    public NodeDistance(Node node, int Distance)
    {
        this.node = node;
        this.Distance = Distance;
    }

    public Node getNode()
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