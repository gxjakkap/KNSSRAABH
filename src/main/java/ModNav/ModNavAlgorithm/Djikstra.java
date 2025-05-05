package ModNav.ModNavAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Path;
import ModNav.ModNavStructure.Place;

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
        pq.add(new NodeDistance<>(src, 0));

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
                    pq.add(new NodeDistance<>(V, Distance.get(V))); //go to the next node
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

    private Map<Place, Path> trackPath(List<Place> path, Map<Place, List<Path>> AdjL)
    {
        Map<Place, Path> pathAdjList = new HashMap<>();
        for(int i = 0; i < path.size() - 1; i++)
        {
            Place current = path.get(i);
            Place next = path.get(i+1);
            
            if(next != null && AdjL.containsKey(current))
            {
                    List<Path> allDest = AdjL.get(current);
                    for(Path p : allDest)
                    {
                        if(p.getDest().equals(next))
                        {
                            pathAdjList.put(current, p);
                            break;
                        }
                    }
            }
        }
        return pathAdjList;
    }

    public List<Integer> getAllWeight(List<Place> path, Map<Place, List<Path>> AdjL) //call this when user select route breakdown
    {
        List<Integer> allWeight = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) 
        {
            Place current = path.get(i);
            Place next = path.get(i + 1);
            for (Path p : AdjL.getOrDefault(current, new ArrayList<>())) 
            {
                if (p.getDest().equals(next)) 
                {
                    allWeight.add(p.getWeight());
                    break;
                }
            }
        }
        return allWeight;
    }

    public void printPath(Place Dest)
    {
        List<Place> Path = getPath(Dest, this.previous);
        String result = Path.stream()
            .map(Place::getId)
            .collect(Collectors.joining(" > "));
        System.out.print(result);
    }

    public void pathBreakdown(Place dest)
    {
        List<Place> path = getPath(dest, this.previous);
        List<Integer> allWeight = getAllWeight(path, AdjL);
        Place start = path.removeFirst();
        if(start.equals(dest))
        {
            System.out.println("You're at the same place idiot.\n");
            return;
        }
        path.removeLast();
        System.out.println("\nPath breakdown: ");
        System.out.print("\nStart\n\n");
        System.out.printf("* [%s] %s\n", start.getId(), start.getPrimaryName());
        for (int i = 0; i < path.size(); i++)
        {
            Place current = path.get(i);
            Integer currWeight = allWeight.get(i);
            System.out.printf("|\n|[%d m]\n|\n", currWeight);
            System.out.printf("* [%s] %s\n", current.getId(), current.getPrimaryName());
        }
        System.out.printf("|\n|[%d m]\n|\n", allWeight.getLast());
        System.out.printf("* [%s] %s\n\n", dest.getId(), dest.getPrimaryName());
        System.out.print("Destination<\n");
    }

    public int getWeight(Map<Place, Integer> weightMap, Place dest)
    {
        Integer weight = weightMap.get(dest);
        if(weight == null)
        {
            return -1;
        }
        else if(weight == Integer.MAX_VALUE)
        {
            return 99999;
        }
        else
        {
            return weight;
        }
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