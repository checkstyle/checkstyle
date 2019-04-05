package com.google.checkstyle.test.chapter7javadoc.rule731selfexplanatory;

/**
 * The following is a bad tag.
 * @mytag Hello
 */
public class InputJavadocMethodCheck extends OverrideClass
{
    //with comments

    /**
     * Some javadoc.
     * @return Some javadoc.
     */
    int foo1()
    {
        return 1;
    }

    /**
     * Some javadoc.
     *
     */
    String foo2()
    {
        return "Fooooooooooooooo"
                + "ooooo"
                + "ooo";
    }

    /**
     * Some javadoc.
     */
    void foo3()
    {
        foo2();
    }

    /**
     * Some javadoc.
     */
    void foo4() {}

    //without comments

    int foo5()
    {
        return 1;
    }

    String foo6()
    {
        return "Fooooooooooooooo"
                + "oooooooo";
    }

    public String foo7()
    {
        return "Fooooooooooooooo"
                + "ooooo"
                + "ooo";
    }

    void foo81()
    {
        foo2();
    }

    void foo82() {





    }

    @MyAnnotation
    String foo91()
    {
        return "Fooooooooooooooo"
                + "ooooo"
                + "ooo";
    }

    @Override
    public String foo92()
    {
        return "Fooooo"
                + "ooo"
                + "ooooooo"
                + "ooooo"
                + "ooo";
    }
}


class OverrideClass {

    public String foo92()
    {
        return "Fooooo"
                + "ooo"
                + "ooooooo"
                + "ooooo"
                + "ooo";
    }
}
@interface MyAnnotation {}
