package com.puppycrawl.tools.checkstyle.bcel.checks;

public class SubClass extends SuperClass
{
    protected int reusedName;
    private int subClassPrivate;
    protected int superClassPrivate;
    protected int differentType;
    public static String S="S";
    public static int staticMethod() {
        return 1;
    }
    public int nonStaticMethod() {
        return 1;
    }
    public static int staticMethodSameParamName(int i) {
        return 1;
    }
    public static int staticMethodSameParamType(int x) {
        return 1;
    }
    public static int staticMethodDifferentParamNum(int i) {
        return 1;
    }
    public static int staticMethodDifferentParamType(String i) {
        return 1;
    }
    public static int staticMethodDifferentObjectType(String i) {
        return 1;
    }
    public static int staticMethodSuperParamType(String i) {
        return 1;
    }
    public void callStaticMethodWithObject() {
        SubClass s = new SubClass();
        s.staticMethod();
    }
    public void callStaticMethodWithClass() {
        SubClass.staticMethod();
    }
    public void callStaticMethodWithThis() {
        this.staticMethod();
    }
    public String callStaticFieldWithObject() {
        SubClass s = new SubClass();
        return s.S;
    }
    public String callStaticFieldWithClass() {
        return SubClass.S;
    }
    public String callStaticFieldWithThis() {
        return this.S;
    }
}
