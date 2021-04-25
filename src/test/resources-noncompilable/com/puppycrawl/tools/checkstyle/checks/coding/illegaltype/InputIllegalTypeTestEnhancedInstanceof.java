//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

/*
 * Config: default
 */
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class InputIllegalTypeTestEnhancedInstanceof {
    public InputIllegalTypeEnhancedInstanceof() {
        LinkedHashMap<Integer, Integer> lhm // violation
                = new LinkedHashMap<Integer, Integer>();

        if (lhm instanceof LinkedHashMap<Integer, Integer> linkedHashMap) { // violation
            System.out.println(linkedHashMap);
        } else if (lhm instanceof Map<Integer, Integer> map) {
            System.out.println(map);
        } else if (lhm instanceof HashMap<Integer, Integer> hashMap) { // violation
            System.out.println(hashMap);
        }
    }

    public InputIllegalTypeEnhancedInstanceof(TreeSet treeSet) {
        if (treeSet instanceof TreeSet t) { // violation
            System.out.println(t);
        }
    }
}
