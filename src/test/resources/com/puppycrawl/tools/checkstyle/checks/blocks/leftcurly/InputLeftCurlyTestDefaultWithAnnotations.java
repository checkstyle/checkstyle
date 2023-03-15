/*
LeftCurly
option = (default)eol
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
class InputLeftCurlyTestDefaultWithAnnotations
{ // violation ''{' at column 1 should be on the previous line'
    private static final int X = 10;
    @Override
    public boolean equals(Object other)
    { // violation ''{' at column 5 should be on the previous line'
        return false;
    }

    @Override
    @SuppressWarnings("unused")
    public int hashCode()
    { // violation ''{' at column 5 should be on the previous line'
        int a = 10;
        return 1;
    }

    @Override @SuppressWarnings({"unused", "unchecked", "static-access"}) public String toString()
    { // violation ''{' at column 5 should be on the previous line'
        Integer i = this.X;
        List<String> l = new ArrayList();
        return "SomeString";
    }
}

@TestClassAnnotation
class InputLeftCurlyAnnotations2 { // ok
    private static final int X = 10;
    @Override
    public boolean equals(Object other) { // ok
        return false;
    }

    @Override
    @SuppressWarnings("unused")
    public int hashCode() { // ok
        int a = 10;
        return 1;
    }

    @Override @SuppressWarnings({"unused", "unchecked", "static-access"}) public String toString()
    { // violation ''{' at column 5 should be on the previous line'
        Integer i = this.X;
        List<String> l = new ArrayList();
        return "SomeString";
    }

    @Deprecated
    @SuppressWarnings({"unused", "unchecked", "static-access"}) public String toString2()
    { // violation ''{' at column 5 should be on the previous line'
        Integer i = this.X;
        List<String> l = new ArrayList();
        return "SomeString";
    }
}

@Target(ElementType.TYPE)
@interface TestClassAnnotation { // ok
}
