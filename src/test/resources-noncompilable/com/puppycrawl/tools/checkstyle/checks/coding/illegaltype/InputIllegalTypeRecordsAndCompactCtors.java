//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeSet;

/*
 * Config:
 * illegalClassNames = { HashMap, HashSet, LinkedHashMap,LinkedHashSet, TreeMap, TreeSet,
                         java.util.HashMap, java.util.HashSet, java.util.TreeMap,
                         java.util.TreeSet, Cloneable }
 */
public class InputIllegalTypeRecordsAndCompactCtors {
    record MyTestRecord
            (LinkedHashMap<Integer, Integer> linkedHashMap) { // violation

    }

    record MyTestRecord2(String string) implements Cloneable { // violation
        static LinkedHashMap<Integer, Integer> lhm // violation
                = new LinkedHashMap<>();
        public MyTestRecord2 {
            TreeSet<String> treeSet = new TreeSet<>(); // violation
        }
    }

    record MyTestRecord3(String str, TreeSet treeSet) { // violation
        void foo(HashMap<Integer, Integer> hashMap) { // violation

        }
    }

    record MyTestRecord4(int x, int y) {
        public MyTestRecord4(TreeSet treeSet) { // does not check constructor params
            this(4, 5);
            LinkedHashMap<Integer, Integer> linkedHashMap // violation
                    = new LinkedHashMap<>();

        }
    }
}
