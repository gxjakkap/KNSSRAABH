package ModNav.ModNavStructure.Algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import ModNav.ModNavStructure.ModNavGraph;
import ModNav.ModNavStructure.Path;
import ModNav.ModNavStructure.Place;

public class Traverse {
    
    public String bfs(ModNavGraph graph, Place startNode){
        /* boolean[] visited = new boolean[graph.getVerticesCount()]; //check if visited */
        Map<Place, Boolean> visited = new HashMap<>();
        StringBuffer str = new StringBuffer(100);
        Queue<Place> q = new LinkedList<>(); //queue สำเร็จรูป

        q.add(startNode);
        visited.put(startNode, true);

        while(!q.isEmpty()){
            Place current = q.peek(); //access สมาชิกตัวหน้า q
            q.remove(); // pop q
            str.append(current).append(" ");

            for(Path i : graph.getAdjacencyList(current)){
                if(!visited.containsKey(i.getDest()) || !visited.get(i.getDest())){
                    q.add(i.getDest());
                    visited.put(i.getDest(), true);
                }
            }
        }

        return str.toString();

    }
}
