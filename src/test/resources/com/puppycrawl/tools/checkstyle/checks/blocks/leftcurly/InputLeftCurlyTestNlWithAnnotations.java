/*
LeftCurly
option = NL
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

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

@TestClassAnnotation // violation below ''{' at column 55 should be on a new line'
class InputLeftCurlyAnnotations2TestNlWithAnnotations {
    private static final int X = 10;
    @Override
    public boolean equals(Object other) { // violation ''{' at column 41 should be on a new line'
        return false;
    }

    @Override
    @SuppressWarnings("unused")
    public int hashCode() { // violation ''{' at column 27 should be on a new line'
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
@interface TestClassTestNlWithAnnotation { // violation ''{' at column 42 should be on a new line'
}
