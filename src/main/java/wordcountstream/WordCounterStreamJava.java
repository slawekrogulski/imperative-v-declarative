package wordcountstream;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class WordCounterStreamJava {
    private static Integer ZERO = Integer.valueOf(0);
    private static Integer ONE = Integer.valueOf(1);
    public static Iterator<WordCounterStream.WordCount> count(Iterator<String> words) {

        return new Iterator<WordCounterStream.WordCount>() {
            Map<String, Integer> wordCounts = new HashMap<>();

            @Override
            public boolean hasNext() {
                return words.hasNext();
            }

            @Override
            public WordCounterStream.WordCount next() {
                String word = words.next();
                Integer count = wordCounts.getOrDefault(word, ZERO) + ONE;
                wordCounts.put(word, count);
                return WordCounterStream.WordCount.apply(word, count);
            }
        };
    }
}
