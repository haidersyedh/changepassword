package org.git.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Helper methods for string operations</h1>
 * Helper methods for string operations
 *
 * @author Syed Hasnain Haider
 * @version 1.0
 * @since 2019-07-19
 */
public class StringHelper {
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.isBlank())
            return true;
        return false;
    }

    public static Map<Character, Integer> buildCharacterFrequencyMap(String str) {
        Map<Character, Integer> charFrequencyMap = new HashMap<Character, Integer>();
        char ch[] = str.toCharArray();
        for (char c : ch) {
            if (charFrequencyMap.get(c) == null)
                charFrequencyMap.put(c, 1);
            else
                charFrequencyMap.put(c, charFrequencyMap.get(c) + 1);
        }
        return charFrequencyMap;
    }
}
