/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = (default)HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class InputIllegalTypeTestEnhancedInstanceof {
    public void InputIllegalTypeEnhancedInstanceof() {
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

    public void InputIllegalTypeEnhancedInstanceof(TreeSet treeSet) { // violation
        if (treeSet instanceof TreeSet t) { // violation
            System.out.println(t);
        }
    }
}
