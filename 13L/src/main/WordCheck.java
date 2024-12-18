import java.util.*;

public class WordCheck {
    // A map where the key is a word with one character replaced by a blank (e.g., "_est"),
    // and the value is a set of words that match this pattern.
    private Map<String, Set<String>> patternMap;

    /**
     * Constructor for WordCheck.
     *
     * @param dictionary An iterator of strings representing the dictionary words.
     */
    public WordCheck(Iterator<String> dictionary) {
        patternMap = new HashMap<>();

        while (dictionary.hasNext()) {
            String word = dictionary.next().toLowerCase();

            // Skip words that are not exactly 4 letters or contain non-alphabetic characters.
            if (word.length() != 4 || !word.matches("[a-zA-Z]+")) {
                continue;
            }

            // Generate patterns for the word.
            for (int i = 0; i < 4; i++) {
                String pattern = word.substring(0, i) + "_" + word.substring(i + 1);
                patternMap.putIfAbsent(pattern, new HashSet<>());
                patternMap.get(pattern).add(word);
            }
        }
    }

    /**
     * Query for words in the dictionary that differ by exactly one character.
     *
     * @param query The input word to check.
     * @return A set of words from the dictionary that differ by one character.
     */
    public Set<String> wordCheck(String query) {
        if (query.length() != 4) {
            throw new IllegalArgumentException("Query word must be exactly 4 letters.");
        }

        query = query.toLowerCase();

        Set<String> result = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            String pattern = query.substring(0, i) + "_" + query.substring(i + 1);

            // Add all words matching the pattern to the result, except the query itself.
            if (patternMap.containsKey(pattern)) {
                for (String word : patternMap.get(pattern)) {
                    if (!word.equals(query)) {
                        result.add(word);
                    }
                }
            }
        }

        return result;
    }
}
