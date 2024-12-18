import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Comparator;

import org.javatuples.Pair;

public class Climber {
    public static int climb(Pair<Integer, Integer> startPost, Pair<Integer, Integer> endPost,
            List<Pair<Integer, Integer>> posts) {

        // TODO : Implement, return the required length
        Map<Pair<Integer, Integer>, Double> minWeight = new HashMap<>();
        PriorityQueue<Pair<Double, Pair<Integer, Integer>>> pq = new PriorityQueue<>(Comparator.comparingDouble(Pair::getValue0));

        for (Pair<Integer, Integer> post : posts) {
            minWeight.put(post, Double.POSITIVE_INFINITY);
        }
        minWeight.put(startPost, 0.0);

        // Add the starting post to the priority queue with weight 0
        pq.offer(new Pair<>(0.0, startPost));

        while (!pq.isEmpty()) {
            Pair<Double, Pair<Integer, Integer>> current = pq.poll();
            double currentWeight = current.getValue0();
            Pair<Integer, Integer> currentPost = current.getValue1();

            if (currentWeight > minWeight.get(currentPost)) {
                continue;
            }

            // Relaxation step: Check all possible posts (neighbors)
            for (Pair<Integer, Integer> neighbor : posts) {
                if (neighbor.equals(currentPost)) {
                    continue;
                }

                // Calculate the rope weight (square of the distance)
                double distance = calculateDistance(currentPost, neighbor);
                double ropeWeight = distance * distance;

                // Relax the edge
                double newWeight = Math.max(currentWeight, ropeWeight); // Use the maximum rope weight so far
                if (newWeight < minWeight.get(neighbor)) {
                    minWeight.put(neighbor, newWeight);
                    pq.offer(new Pair<>(newWeight, neighbor));
                }
            }
        }

        // Return the minimal weight to reach the end post
        return (int) Math.round(minWeight.get(endPost));
    }

    // Helper method to calculate Euclidean distance
    private static double calculateDistance(Pair<Integer, Integer> post1, Pair<Integer, Integer> post2) {
        int x1 = post1.getValue0();
        int y1 = post1.getValue1();
        int x2 = post2.getValue0();
        int y2 = post2.getValue1();
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
