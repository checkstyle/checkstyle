/*
ModifierOrder
modifiersOrder = public, private, protected, abstract, static, final, transient, volatile,\
                 default, synchronized, native, strictfp

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

public class InputModifierOrderCustomOrder {
    public int x;
    public static int y;
    // violation below 'public' modifier out of order with the defined modifier order.
    static public int z;
    private static int a;
    // violation below 'private' modifier out of order with the defined modifier order.
    static private int b;
    protected static int c;
    // violation below 'protected' modifier out of order with the defined modifier order.
    static protected int d;
    public abstract static class E {}
    // violation below 'abstract' modifier out of order with the defined modifier order.
    public static abstract class F {}
    private transient volatile int g;
    // violation below 'transient' modifier out of order with the defined modifier order.
    private volatile transient int h;
}
