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

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.AbstractMap;

public class InputIllegalTypeTestEnhancedInstanceof {
    public void InputIllegalTypeEnhancedInstanceof(Map<Integer, Integer> lmm) {
        LinkedHashMap<Integer, Integer> lhm = new LinkedHashMap<Integer, Integer>();
        // violation above, 'Usage of type 'LinkedHashMap' is not allowed'

        if (lmm instanceof LinkedHashMap<Integer, Integer> linkedHashMap) {
            // violation above, 'Usage of type 'LinkedHashMap' is not allowed'
            System.out.println(linkedHashMap);
        } else if (lmm instanceof AbstractMap<Integer, Integer> map) {
            System.out.println(map);
        } else if (lmm instanceof HashMap<Integer, Integer> hashMap) {
            // violation above, 'Usage of type 'HashMap' is not allowed'
            System.out.println(hashMap);
        }
    }

    public void InputIllegalTypeEnhancedInstanceof(TreeSet treeSet) {
        // violation above, 'Usage of type 'TreeSet' is not allowed'
        Object set = new Object();
        if (set instanceof TreeSet t) { // violation, 'Usage of type TreeSet is not allowed'.
            System.out.println(t);
        }
    }
}
