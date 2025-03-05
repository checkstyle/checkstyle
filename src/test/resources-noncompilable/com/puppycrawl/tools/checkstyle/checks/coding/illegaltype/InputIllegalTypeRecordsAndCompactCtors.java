/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet, Cloneable
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeSet;

public class InputIllegalTypeRecordsAndCompactCtors {
    record MyTestRecord
            (LinkedHashMap<Integer, Integer> linkedHashMap) { // violation, 'Usage of type LinkedHashMap is not allowed'.

    }

    record MyTestRecord2(String string) implements Cloneable { // violation, 'Usage of type Cloneable is not allowed'.
        static LinkedHashMap<Integer, Integer> lhm // violation, 'Usage of type LinkedHashMap is not allowed'.
                = new LinkedHashMap<>();
        public MyTestRecord2 {
            TreeSet<String> treeSet = new TreeSet<>(); // violation, 'Usage of type TreeSet is not allowed'.
        }
    }

    record MyTestRecord3(String str, TreeSet treeSet) { // violation, 'Usage of type TreeSet is not allowed'.
        void foo(HashMap<Integer, Integer> hashMap) { // violation, 'Usage of type HashMap is not allowed'.

        }
    }

    record MyTestRecord4(int x, int y) {
        public MyTestRecord4(TreeSet treeSet) { // does not check constructor params
            this(4, 5);
            LinkedHashMap<Integer, Integer> linkedHashMap // violation, 'Usage of type LinkedHashMap is not allowed'.
                    = new LinkedHashMap<>();

        }
    }
}
