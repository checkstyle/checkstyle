package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;
import java.util.TreeSet;
import java.util.Hashtable;
//configuration: default
public class InputIllegalType {
    private AbstractClass a = null; //WARNING
    private NotAnAbstractClass b = null; /*another comment*/

    private com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.AbstractClass c = null; //WARNING
    private com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.NotAnAbstractClass d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.TreeSet table1() { return null; } //WARNING
    private TreeSet table2() { return null; } //WARNING
    static class SomeStaticClass {
        
    }
    
    InputIllegalType(Integer i) {}
    private void table2(Integer i) {}

    private void getInitialContext(java.util.TreeSet v) {} // ignore method by default
}
