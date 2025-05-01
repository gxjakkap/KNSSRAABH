package ModNav.ModNavAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Path;
import ModNav.ModNavStructure.Place;
import ModNav.Structure.Graph;

public class Djikstra
{
    private Map<Place, List<Path>> AdjL;
    Map<Place, Place> previous = new HashMap<>();

    public Djikstra(ModNavGraph graph)
    {
        this.AdjL = graph.getAllPaths();
    }

    public Map<Place, Integer> ShortestPath(Place src)
    {
        Map<Place, Integer> Distance = setDist(src);
        PriorityQueue<NodeDistance<Place>> pq = new PriorityQueue<>();
        pq.add(new NodeDistance<Place>(src, 0));

        while(!pq.isEmpty())
        {
            NodeDistance<Place> current = pq.poll();
            Place u = current.getNode();

            if(current.getDist() > Distance.get(u)) continue; //current path costs more than previous path

            for(Path edge : AdjL.getOrDefault(u, List.of()))
            {
                Place V = edge.getDest();
                int weight = edge.getWeight();

                if(Distance.get(u) + weight < Distance.get(V))
                {
                    Distance.put(V, Distance.get(u) + weight); //update path cost
                    previous.put(V, u); //track path
                    pq.add(new NodeDistance<Place>(V, Distance.get(V))); //go to the next node
                }
            }
        }

        return Distance;
    }

    public List<Place> getPath(Place Dest, Map<Place, Place> previous)
    {
        List<Place> path = new ArrayList<>();
        Place current = Dest;

        while(current != null)
        {
            path.add(current);
            current = previous.get(current); //Back tracking
        }

        Collections.reverse(path);
        return path;
    }


    private Map<Place, Integer> setDist(Place src)
    {
        Map<Place, Integer> Distance = new HashMap<>();
        Set<Place> places = new HashSet<>();

        for (Place node : AdjL.keySet())
        {
            places.add(node);

            for (Path edge : AdjL.get(node))
            {
                places.add(edge.getDest());
            }
        }

        for(Place node: places)
        {
            Distance.put(node, Integer.MAX_VALUE);
        }
        
        Distance.put(src, 0);
        
        return Distance;
    }

    public Map<Place, Place> getPrevious(){
        return this.previous;
    }

}