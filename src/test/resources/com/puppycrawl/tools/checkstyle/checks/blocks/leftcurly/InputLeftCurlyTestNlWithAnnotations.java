package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/*
 * Config:
 * option = nl
 */
@TestClassAnnotation
class InputLeftCurlyTestNlWithAnnotations
{ // ok
    private static final int X = 10;
    @Override
    public boolean equals(Object other)
    { // ok
        return false;
    }

    @Override
    @SuppressWarnings("unused")
    public int hashCode()
    { // ok
        int a = 10;
        return 1;
    }

    @Override @SuppressWarnings({"unused", "unchecked", "static-access"}) public String toString()
    { // ok
        Integer i = this.X;
        List<String> l = new ArrayList();
        return "SomeString";
    }
}

@TestClassAnnotation
class InputLeftCurlyAnnotations2TestNlWithAnnotations { // violation
    private static final int X = 10;
    @Override
    public boolean equals(Object other) { // violation
        return false;
    }

    @Override
    @SuppressWarnings("unused")
    public int hashCode() { // violation
        int a = 10;
        return 1;
    }

    @Override @SuppressWarnings({"unused", "unchecked", "static-access"}) public String toString()
    { // ok
        Integer i = this.X;
        List<String> l = new ArrayList();
        return "SomeString";
    }

    @Deprecated
    @SuppressWarnings({"unused", "unchecked", "static-access"}) public String toString2()
    { // ok
        Integer i = this.X;
        List<String> l = new ArrayList();
        return "SomeString";
    }
}

@Target(ElementType.TYPE)
@interface TestClassTestNlWithAnnotation { // violation
}
