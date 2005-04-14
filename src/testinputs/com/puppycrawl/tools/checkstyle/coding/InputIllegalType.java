package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.HashTable;

public class InputIllegalType {
    private AbstractClass a = null;
    private NotAnAbstractClass b = null;

    private au.com.redhillconsulting.jamaica.tools.checkstyle.InputIllegalType.AbstractClass c = null;
    private au.com.redhillconsulting.jamaica.tools.checkstyle.InputIllegalType.NotAnAbstractClass d = null;

    private abstract class AbstractClass {}

    private class NotAnAbstractClass {}

    private java.util.Hashtable table1() { return null; }
    private Hashtable table2() { return null; }
}
