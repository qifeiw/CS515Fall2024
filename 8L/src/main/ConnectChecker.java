import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/** 	CS515 Lab 8
 File: ConnectChecker.java
 Name: Qifei Wang
 Section: 2
 Date: 10/17/2024
 Collaboration Declaration: None
 Lab Partner: None
 Collaboration: None
 */

public class ConnectChecker {

    public StringBuilder printPath(List<Character> l, StringBuilder result) {
        // print in reverse order
        ListIterator<Character> iter = l.listIterator(l.size());
        while (iter.hasPrevious()) {
            result.append(iter.previous()).append(" ");
        }
        result.append("\n");
        return result;
    }

    public String check(BufferedReader is) throws IOException {
        // build the graph
        Map<Character, List<Character>> graph = new HashMap<Character,List<Character>>();
        char v1, v2, start, end;
        String line;


        // Read graph input lines
        boolean processingQueries = false;
        StringBuilder result = new StringBuilder();

        while ((line = is.readLine()) != null) {
            if (line.length() < 1) continue;
            String[] tokens = line.split(" ");

            if (tokens[0].equals("?")) {
                processingQueries = true;
            }

            if (!processingQueries) {
                v1 = tokens[0].charAt(0);
                v2 = tokens[1].charAt(0);

                graph.putIfAbsent(v1, new LinkedList<>());
                graph.get(v1).add(v2); // Store edge in graph
            }  else {
                // Process queries
                start = tokens[1].charAt(0);
                end = tokens[2].charAt(0);

                // Perform BFS to find the shortest path
                Queue<Character> queue = new LinkedList<>();
                Map<Character, Character> prev = new HashMap<>();
                Set<Character> visited = new HashSet<>();
                queue.add(start);
                visited.add(start);
                boolean pathFound = false;

                while (!queue.isEmpty()) {
                    char current = queue.poll();

                    if (current == end) {
                        pathFound = true;
                        break;
                    }

                    for (char neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                            prev.put(neighbor, current);
                        }
                    }
                }

                // Ensure result is appended correctly
                if (pathFound) {
                    result.append("From ").append(start).append(" to ").append(end).append(" : path found.\n");
                    List<Character> path = new ArrayList<>();
                    for (char at = end; at != start; at = prev.get(at)) {
                        path.add(at);
                    }
                    path.add(start);
                    printPath(path, result);  // Correctly print the path
                } else {
                    result.append("From ").append(start).append(" to ").append(end).append(" : Path not found!\n");
                }
            }
        }
        return result.toString();
    }
}
