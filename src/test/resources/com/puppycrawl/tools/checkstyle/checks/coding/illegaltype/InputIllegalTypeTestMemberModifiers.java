/*
IllegalType
validateAbstractClassNames = true
illegalClassNames = (default)HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = LITERAL_PRIVATE, LITERAL_PROTECTED, LITERAL_STATIC
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;
import java.util.TreeSet;

public class InputIllegalTypeTestMemberModifiers {
    private AbstractClass a = null; // violation
    private NotAnAbstractClass b = null; /*another comment*/

    private java.util.AbstractList c = null; // violation
    private java.util.List d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.TreeSet<Object> table1() { return null; } // violation
    private TreeSet<Object> table2() { return null; } // violation
    static class SomeStaticClass {

    }

    //WARNING if memberModifiers is set and contains TokenTypes.LITERAL_PROTECTED
    protected java.util.AbstractList c1 = null; // violation
    //NO WARNING if memberModifiers is set and does not contain TokenTypes.LITERAL_PUBLIC
    public final static java.util.TreeSet<Object> table3() { return null; } // violation

    java.util.TreeSet<Object> table4() { java.util.TreeSet<Object> treeSet = null; return null; }

    private class Some {
        java.util.TreeSet<Object> treeSet = null;
    }
    //WARNING if memberModifiers is set and contains TokenTypes.LITERAL_PROTECTED
    protected AbstractClass a1 = null; // violation
    public AbstractClass a2 = null;

    //NO WARNING if memberModifiers is set and does not contain TokenTypes.LITERAL_PUBLIC
    public void table5(java.util.TreeSet<Object> arg) { }
}
