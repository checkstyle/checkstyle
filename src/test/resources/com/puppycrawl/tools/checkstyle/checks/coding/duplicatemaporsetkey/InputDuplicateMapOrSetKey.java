/*
DuplicateMapOrSetKey


*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicatemaporsetkey;

import java.util.Map;
import java.util.Set;

public class InputDuplicateMapOrSetKey {

    void testMapOf() {
        Map<String, Integer> map1 = Map.of(
            "key1", 1,
            "key2", 2,
            // violation below 'Duplicate key or element argument'
            "key1", 3
        );
    }

    void testSetOf() {
        Set<String> set1 = Set.of(
            "A",
            "B",
            // violation below 'Duplicate key or element argument'
            "A"
        );
    }

    void testMapOfEntries() {
        Map<String, Integer> map2 = Map.ofEntries(
            Map.entry("k1", 1),
            Map.entry("k2", 2),
            // violation below 'Duplicate key or element argument'
            Map.entry("k1", 3)
        );
    }

    void testNoDuplicates() {
        Map<String, Integer> map = Map.of("a", 1, "b", 2);
        Set<String> set = Set.of("X", "Y", "Z");
    }

    void testDifferentExpressions() {
        Set.of(1, "1",
            // violation below 'Duplicate key or element argument'
            1);
        Map.ofEntries(
            Map.entry("a", 1),
            Map.entry("b", 2),
            // violation below 'Duplicate key or element argument'
            Map.entry("a", 3)
        );
    }

    void testUnsupportedCalls() {
        Map.Entry<String, Integer> entry = null;
        Map.ofEntries(entry, other());
        Map.entry("x", 1);
        other();
    }

    private static Map.Entry<String, Integer> other() {
        return null;
    }
}
