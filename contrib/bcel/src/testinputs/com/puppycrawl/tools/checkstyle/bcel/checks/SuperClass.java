package com.puppycrawl.tools.checkstyle.bcel.checks;

public class SuperClass
{
    // Name reused in subclass
    protected int reusedName;
    // Subclass uses private name, still conflict
    protected int subClassPrivate;
    // This is a private field, subclass does not shadow
    private int superClassPrivate;
    // Test for different data types
    protected String differentType;

    // Hidden
    public static int staticMethod() {
        return 0;
    }
    // Not hidden
    public int nonStaticMethod() {
        return 0;
    }
    // Hidden
    public static int staticMethodSameParamName(int i) {
        return 0;
    }
    // Hidden
    public static int staticMethodSameParamType(int i) {
        return 0;
    }
    // Not hidden
    public static int staticMethodDifferentParamNum(int i, int j) {
        return 0;
    }
    // Not hidden
    public static int staticMethodDifferentParamType(int i) {
        return 0;
    }
    // Not hidden
    public static int staticMethodDifferentObjectType(Integer i) {
        return 0;
    }
    // Not hidden (?)
    public static int staticMethodSuperParamType(Object i) {
        return 0;
    }
}
