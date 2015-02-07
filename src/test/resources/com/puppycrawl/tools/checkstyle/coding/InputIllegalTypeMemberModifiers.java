package com.puppycrawl.tools.checkstyle.coding;
import java.util.TreeSet;
import java.util.Hashtable;
//configuration: default
public class InputIllegalTypeMemberModifiers {
    private AbstractClass a = null; //WARNING
    private NotAnAbstractClass b = null; /*another comment*/

    private com.puppycrawl.tools.checkstyle.coding.InputIllegalTypeMemberModifiers.AbstractClass c = null; //WARNING
    private com.puppycrawl.tools.checkstyle.coding.InputIllegalTypeMemberModifiers.NotAnAbstractClass d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.TreeSet table1() { return null; } //WARNING
    private TreeSet table2() { return null; } //WARNING
    static class SomeStaticClass {
        
    }
    
    //WARNING if memberModifiers is set and contains TokenTypes.LITERAL_PROTECTED
    protected com.puppycrawl.tools.checkstyle.coding.InputIllegalTypeMemberModifiers.AbstractClass c1 = null;
    //NO WARNING if memberModifiers is set and does not contain TokenTypes.LITERAL_PUBLIC
    public final static java.util.TreeSet table3() { return null; }
    
    java.util.TreeSet table4() { java.util.TreeSet treeSet = null; return null; }
    
    private class Some {
        java.util.TreeSet treeSet = null;
    }
    //WARNING if memberModifiers is set and contains TokenTypes.LITERAL_PROTECTED
    protected AbstractClass a1 = null;
}
