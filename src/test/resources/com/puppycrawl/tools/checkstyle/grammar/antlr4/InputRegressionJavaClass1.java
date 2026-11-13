package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputRegressionJavaClass1 {
    //comments

    // single-line
    /* multi on single-line */
    /* multi
     line */
    /** javadoc */
    // tab ->	<-

    // fields
    // modifiers
    public int f1;
    private int f2;
    protected int f3;
    int f4;
    static int f5;
    final int f6;
    volatile int f7;
    transient int f8;
    Object f9;

    // constructors
    static {}
    public InputRegressionJavaClass1() {f6 = 0;}
    public InputRegressionJavaClass1(int i) {this.f6 = i;}
    public InputRegressionJavaClass1(float f) {this((int)f);}
    InputRegressionJavaClass1(double a) throws Exception {f6 = 0;}

    // methods
    // modifiers
    native void m1();
    void m2() {}
    synchronized void m4() {}
    strictfp void m5() {}

    // returns
    public int[] m6() {return null;}
    public int m7() [] {return null;}

    // parameters
    public void m10(String p1) {}
    public void m11(final String p1) {}
    public void m12(String[] p1) {}
    public void m13(String[][] p1) {}
    public void m14(String p1, String p2) {}
    public void m15(String... p1) {}
    public void m16(String[]... p1) {}
    public void m17(int p1, String[]... p2) {}

    // throws
    public void m18() throws Exception {}
    public void m19() throws IOException, Exception {}

    // types
    public <T_$> T_$ m20() {return null;}
    public <$_T> $_T m21() {return null;}
    public <T extends Enum<T>> void m22() {}
    public <T> void m23() {};
    public <T extends RuntimeException & java.io.Serializable> void m24() {}

    // annotations
    @SuppressWarnings({})
    public void m50() {}
    @SuppressWarnings({"1"})
    public void m51() {}
    @SuppressWarnings({"1","2"})
    public void m52() {}
    @SuppressWarnings(value={"1"})
    public void m53() {}
    @SuppressWarnings(value={"1",})
    public void m54() {}
    @SuppressWarnings(value={"1","2"})
    public void m55() {}
    @InputRegressionJavaAnnotation1(m1="1", m2="2")
    public void m56() {}
    @ComplexAnnotation({
            @InputRegressionJavaAnnotation1(m1 = "1", m2 = ""),
            @InputRegressionJavaAnnotation1(m1 = "1", m2 = "")
    })
    public void m57() {}
    public void m58(@Deprecated String s) {}
    public void m59(final @Deprecated List l) {}

    // extra
    {}
    ;

    public @interface innerAnnotation {}
}
