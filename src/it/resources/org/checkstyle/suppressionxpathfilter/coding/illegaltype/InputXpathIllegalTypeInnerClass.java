/*
IllegalType
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
illegalClassNames = (default)HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet
legalAbstractClassNames = (default)
memberModifiers = (default)
tokens = METHOD_DEF
validateAbstractClassNames = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.illegaltype;

public class InputXpathIllegalTypeInnerClass {
    class Inner {
        // violation below 'Declaring variables, return values or parameters of type 'java.util.HashMap' is not allowed.'
        public <T extends java.util.HashMap> void typeParam(T t) {}
    }
}
