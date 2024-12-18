import java.util.Iterator;
import java.util.Set;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordFreq {
    // You are allowed to add private methods, or private members but you cannot
    // alter the return or parameter type of either the following public
    // methods.
    private Map<String, TreeMap<Integer, Integer>> wordData;
    /**
     * @param dict
     *             dict is an iterator of triplets which stores
     *             String = word
     *             Integer0 = year
     *             Integer1 = frequency
     */
    public WordFreq(Iterator<Triplet<String, Integer, Integer>> dictionary) {
        // TODO : Implement this should only happen once at construction,
        // during the wordFreq function call the dictionary should not
        // be needed. Feel free to add a private class member which organizes
        // the information in the dictionary for later queries.

        wordData = new HashMap<>();

        while (dictionary.hasNext()) {
            Triplet<String, Integer, Integer> entry = dictionary.next();
            String word = entry.getValue0();
            int year = entry.getValue1();
            int frequency = entry.getValue2();

            // Add the year-frequency pair to the word's TreeMap
            wordData.putIfAbsent(word, new TreeMap<>());
            wordData.get(word).put(year, frequency);
        }
    }

    /**
     * @param query
     * @param startYear
     * @return Set<Pair<year, frequency>>
     */
    public Set<Pair<Integer, Integer>> wordFreq(String query, int startYear) {
        // TODO : Implement
        Set<Pair<Integer, Integer>> result = new TreeSet<>();

        // Check if the word exists in the data
        if (!wordData.containsKey(query)) {
            return result; // Return an empty set if the word doesn't exist
        }

        // Retrieve the years and frequencies for the given word
        TreeMap<Integer, Integer> yearData = wordData.get(query);

        // Iterate through years >= startYear
        for (Map.Entry<Integer, Integer> entry : yearData.tailMap(startYear).entrySet()) {
            result.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        return result;
    }
}
