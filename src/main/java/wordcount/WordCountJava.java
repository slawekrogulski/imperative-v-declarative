package wordcount;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class WordCountJava {
    private static Integer ZERO = Integer.valueOf(0);
    private static Integer ONE = Integer.valueOf(1);

    public static Map<String, Integer> count (List<String> words) {
        var wordCounts = new HashMap<String, Integer>();
        words.forEach(word -> {
            var wordCount = wordCounts.getOrDefault(word, ZERO) + ONE;
            wordCounts.put(word, wordCount);
        });
        return wordCounts;
    }
}
