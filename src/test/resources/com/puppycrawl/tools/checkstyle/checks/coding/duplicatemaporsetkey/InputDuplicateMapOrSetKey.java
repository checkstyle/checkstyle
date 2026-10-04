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
            "key1", 3  // violation 'Duplicate key or element argument identical to the argument at line '16'.'
        );
    }

    void testSetOf() {
        Set<String> set1 = Set.of(
            "A",
            "B",
            "A" // violation 'Duplicate key or element argument identical to the argument at line '24'.'
        );
    }

    void testMapOfEntries() {
        Map<String, Integer> map2 = Map.ofEntries(
            Map.entry("k1", 1),
            Map.entry("k2", 2),
            Map.entry("k1", 3) // violation 'Duplicate key or element argument identical to the argument at line '32'.'
        );
    }

    void testNoDuplicates() {
        Map<String, Integer> map = Map.of("a", 1, "b", 2);
        Set<String> set = Set.of("X", "Y", "Z");
    }
}
