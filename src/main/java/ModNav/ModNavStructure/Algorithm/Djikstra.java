package ModNav.ModNavStructure.Algorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import ModNav.Structure.Edge;
import ModNav.Structure.Graph;
import ModNav.Structure.Node;

public class Djikstra
{
    private Node Src;
    private Graph graph;
    private Map<Node, List<Edge>> AdjL;
    Map<Node, Node> previous = new HashMap<>();

    public Djikstra(Node Src, Graph graph)
    {
        this.Src = Src;
        this.graph = graph;
        this.AdjL = graph.getAdjacencyList();
    }

    public Map<Node, Integer> ShortestPath(Node src)
    {
        Map<Node, Integer> Distance = setDist(src);
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();
        pq.add(new NodeDistance(src, 0));

        while(!pq.isEmpty())
        {
            NodeDistance current = pq.poll();
            Node u = current.getNode();

            if(current.getDist() > Distance.get(u)) continue; //current path costs more than previous path

            for(Edge edge : AdjL.getOrDefault(u, List.of()))
            {
                Node V = edge.getDest();
                int weight = edge.getWeight();

                if(Distance.get(u) + weight < Distance.get(V))
                {
                    Distance.put(V, Distance.get(u) + weight); //update path cost
                    previous.put(V, u); //track path
                    pq.add(new NodeDistance(V, Distance.get(V))); //go to the next node
                }
            }
        }

        return Distance;
    }

    public List<Node> getPath(Node Dest, Map<Node, Node> previous)
    {
        List<Node> path = new ArrayList<>();
        Node current = Dest;

        while(current != null)
        {
            path.add(current);
            current = previous.get(current); //Back tracking
        }

        Collections.reverse(path);
        return path;
    }


    private Map<Node, Integer> setDist(Node src) 
    {
        Map<Node, Integer> Distance = new HashMap<>();
        Set<Node> places = new HashSet<>();

        for (Node node : AdjL.keySet()) 
        {
            places.add(node);

            for (Edge edge : AdjL.get(node)) 
            {
                places.add(edge.getDest());
            }
        }

        for(Node node: places)
        {
            Distance.put(node, Integer.MAX_VALUE);
        }
        
        Distance.put(src, 0);
        
        return Distance;
    }


}