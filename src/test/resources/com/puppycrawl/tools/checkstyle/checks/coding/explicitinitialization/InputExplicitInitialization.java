/*
ExplicitInitialization
onlyObjectReferences = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.explicitinitialization;

public class InputExplicitInitialization {
// violation below 'Variable 'x' explicitly initialized to '0' (default value for its type)'
    private int x = 0;
// violation below 'Variable 'bar' explicitly initialized to 'null' (default value for its type)'
    private Object bar = /* comment test */null;
    private int y = 1;
    private long y1 = 1 - 1;
    private long y3;
// violation below 'Variable 'y4' explicitly initialized to '0' (default value for its type)'
    private long y4 = 0L;
// violation below 'Variable 'b1' explicitly initialized to 'false' (default value for its type)'
    private boolean b1 = false;
    private boolean b2 = true;
    private boolean b3;
    private String str = "";
    java.lang.String str1 = null, str3 = null; // 2 violations
// violation below 'Variable 'ar1' explicitly initialized to 'null' (default value for its type)'
    int ar1[] = null;
    int ar2[] = new int[1];
    int ar3[];
// violation below 'Variable 'f1' explicitly initialized to '0' (default value for its type)'
    float f1 = 0f;
// violation below 'Variable 'd1' explicitly initialized to '0' (default value for its type)'
    double d1 = 0.0;

    static char ch;
// violation below 'Variable 'ch1' explicitly initialized to '\\0' (default value for its type)'
    static char ch1 = 0;
// violation below 'Variable 'ch2' explicitly initialized to '\\0' (default value for its type)'
    static char ch2 = '\0';
    static char ch3 = '\1';

    void method() {
        int xx = 0;
        String s = null;
    }
}

interface interface1{
    int TOKEN_first = 0x00;
    int TOKEN_second = 0x01;
    int TOKEN_third = 0x02;
}

class InputExplicitInit2 {
// violation below 'Variable 'bar' explicitly initialized to 'null' (default value for its type)'
    private Bar<String> bar = null;
// violation below 'Variable 'barArr' explicitly initialized to 'null' (default value for its type)'
    private Bar<String>[] barArr = null;
}

enum InputExplicitInit3 {
    A,
    B
    {
// violation below 'Variable 'x' explicitly initialized to '0' (default value for its type)'
        private int x = 0;
// violation below 'Variable 'bar' explicitly initialized to 'null' (default value for its type)'
        private Bar<String> bar = null;
// violation below 'Variable 'barArr' explicitly initialized to 'null' (default value for its type)'
        private Bar<String>[] barArr = null;
        private int y = 1;
    };
// violation below 'Variable 'x' explicitly initialized to '0' (default value for its type)'
    private int x = 0;
// violation below 'Variable 'bar' explicitly initialized to 'null' (default value for its type)'
    private Bar<String> bar = null;
// violation below 'Variable 'barArr' explicitly initialized to 'null' (default value for its type)'
    private Bar<String>[] barArr = null;
    private int y = 1;
    private Boolean booleanAtt = false;
}

@interface annotation1{
    int TOKEN_first = 0x00;
    int TOKEN_second = 0x01;
    int TOKEN_third = 0x02;
}

class ForEach {
    public ForEach(java.util.Collection<String> strings)
    {
        for(String s : strings) //this should not even be checked
        {

        }
    }
}

class Bar<T> {
}

class Chars {
    char a;
    char b = a;
    byte c = 1;
    short d = 1;
    final long e = 0;
}

class Doubles {
    final double subZero = -0.0;
    final double nan = Double.NaN;
// violation below 'Variable 'shortVar' explicitly initialized to '0' (default value for its type)'
    private short shortVar = 0;
// violation below 'Variable 'bite' explicitly initialized to '0' (default value for its type)'
    private byte bite = 0;
// violation below 'Variable 'd' explicitly initialized to '0' (default value for its type)'
    double d = 0d;
}
