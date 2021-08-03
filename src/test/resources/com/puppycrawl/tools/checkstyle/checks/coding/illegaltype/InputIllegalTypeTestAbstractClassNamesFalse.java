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

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

public class InputIllegalTypeTestAbstractClassNamesFalse {

    abstract class AbstractClass {
        abstract String getClassInfo();
        abstract boolean isPerfectClass();
    }

    class MyNonAbstractClass extends AbstractClass { // ok

        boolean perfect = true;

        private MyNonAbstractClass() {}

        @Override
        String getClassInfo() {
            return "This is my non abstract class.";
        }

        @Override
        boolean isPerfectClass() {
            return perfect;
        }
    }

    AbstractClass a = new MyNonAbstractClass(); // ok

    public String getInnerClassInfo(AbstractClass clazz) { // ok
        return clazz.getClassInfo();
    }

    public AbstractClass newInnerClassInstance() { // ok
        return new MyNonAbstractClass();
    }
}
