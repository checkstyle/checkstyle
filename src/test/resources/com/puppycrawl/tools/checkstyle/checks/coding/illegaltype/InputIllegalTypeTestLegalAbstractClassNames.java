/*
IllegalType
validateAbstractClassNames = true
illegalClassNames = (default)HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet
legalAbstractClassNames = AbstractClass
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;
import java.util.HashMap;
import java.util.TreeSet;

public class InputIllegalTypeTestLegalAbstractClassNames implements InputIllegalTypeSuper {
    private AbstractClass a = null;
    private NotAnAbstractClass b = null; /*another comment*/

    private com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.AbstractClass // violation, 'Usage of type com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.AbstractClass is not allowed'.
        c = null;
    private java.util.List d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.TreeSet table1() { return null; } // violation, 'Usage of type java.util.TreeSet is not allowed'.
    private TreeSet table2() { return null; } // violation, 'Usage of type TreeSet is not allowed'.
    static class SomeStaticClass {

    }

    InputIllegalTypeTestLegalAbstractClassNames(Integer i) {}
    private void table2(Integer i) {}

    private void getInitialContext(java.util.TreeSet v) {} // ignore method by default

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

interface InputIllegalTypeSuperTestLegalAbstractClassNames {
    void foo(HashMap<?, ?> buffer); // violation, 'Usage of type HashMap is not allowed'.

    HashMap<?, ?> foo(); // violation, 'Usage of type HashMap is not allowed'.

    Object bar();
}
