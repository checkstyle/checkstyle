package com.puppycrawl.tools.checkstyle.coding;

import java.util.Hashtable;

public class InputIllegalType {
    private AbstractClass a = null; // comment
    private NotAnAbstractClass b = null; /*another comment*/

    private com.puppycrawl.tools.checkstyle.coding.InputIllegalType.AbstractClass c = null;
    private com.puppycrawl.tools.checkstyle.coding.InputIllegalType.NotAnAbstractClass d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.Hashtable table1() { return null; }
    private Hashtable table2() { return null; }
}
