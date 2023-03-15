/*
IllegalType
validateAbstractClassNames = true
illegalClassNames = (default)HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet
legalAbstractClassNames = (default)
ignoredMethodNames = table2
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;
import java.util.HashMap;
import java.util.TreeSet;

public class InputIllegalTypeTestIgnoreMethodNames implements InputIllegalTypeSuper {
    private AbstractClass a = null; // violation
    private NotAnAbstractClass b = null; /*another comment*/

    private com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.AbstractClass
        c = null; // violation above
    private java.util.List d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.TreeSet table1() { return null; } // violation
    private TreeSet table2() { return null; } // ok
    static class SomeStaticClass {

    }

    InputIllegalTypeTestIgnoreMethodNames(Integer i) {}
    private void table2(Integer i) {}

    private void getInitialContext(java.util.TreeSet v) {} // violation

    @Override
    public void foo(HashMap<?, ?> buffer) {} // ignore

    @Override
    public HashMap<?, ?> foo() { //ignore
        return null;
    }

    @Override
    public HashMap<?, ?> bar() { //ignore
        return null;
    }
}

interface InputIllegalTypeSuperTestIgnoreMethodNames {
    void foo(HashMap<?, ?> buffer); // violation

    HashMap<?, ?> foo(); // violation

    Object bar();
}
