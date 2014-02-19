package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Hashtable;

public class InputIllegalType {
    private AbstractClass a = null;
    private NotAnAbstractClass b = null;

    private com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.AbstractClass c = null;
    private com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.NotAnAbstractClass d = null;

    private abstract class AbstractClass {}

    private class NotAnAbstractClass {}

    private java.util.Hashtable table1() { return null; }
    private Hashtable table2() { return null; }
}
