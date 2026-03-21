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
    private AbstractClass a = null; // violation, 'Usage of type AbstractClass is not allowed'.
    private NotAnAbstractClass b = null; /*another comment*/

    private java.util.AbstractList c = null;
    // violation above, 'Usage of type 'java.util.AbstractList' is not allowed'
    private java.util.List d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.TreeSet<Object> table1() { return null; }
    // violation above, 'Usage of type 'java.util.TreeSet' is not allowed'
    private TreeSet<Object> table2() { return null; }
    // violation above, 'Usage of type 'TreeSet' is not allowed'
    static class SomeStaticClass {

    }

    //WARNING if memberModifiers is set and contains TokenTypes.LITERAL_PROTECTED
    protected java.util.AbstractList c1 = null;
    // violation above, 'Usage of type 'java.util.AbstractList' is not allowed'
    //NO WARNING if memberModifiers is set and does not contain TokenTypes.LITERAL_PUBLIC
    public final static java.util.TreeSet<Object> table3() { return null; }
    // violation above, 'Usage of type 'java.util.TreeSet' is not allowed'

    java.util.TreeSet<Object> table4() { java.util.TreeSet<Object> treeSet = null; return null; }

    private class Some {
        java.util.TreeSet<Object> treeSet = null;
    }
    //WARNING if memberModifiers is set and contains TokenTypes.LITERAL_PROTECTED
    protected AbstractClass a1 = null; // violation, 'Usage of type AbstractClass is not allowed'.
    public AbstractClass a2 = null;

    //NO WARNING if memberModifiers is set and does not contain TokenTypes.LITERAL_PUBLIC
    public void table5(java.util.TreeSet<Object> arg) { }
}
