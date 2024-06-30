/*
MethodParamPad
allowLineBreaks = true
option = (default)nospace
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         CTOR_CALL, ENUM_CONSTANT_DEF, RECORD_DEF, RECORD_PATTERN_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

import java.util.ArrayList;

public class InputMethodParamPadCheckConstructors extends ArrayList {
    public InputMethodParamPadCheckConstructors() {
        super(); // ok, no whitespace
    }

    public InputMethodParamPadCheckConstructors(Object b) {
        super (10); // violation ''(' is preceded with whitespace.'
    }

    public InputMethodParamPadCheckConstructors(int a) {
        this (); // violation ''(' is preceded with whitespace.'
    }

    public InputMethodParamPadCheckConstructors(int a, String b) {
        this (a + b.length()); // violation ''(' is preceded with whitespace.'
    }

    public InputMethodParamPadCheckConstructors(String a, String b) {
        this
            (a.length() + b.length()); // line break is ok, no violation
    }

    public InputMethodParamPadCheckConstructors(String a, String b, Object c) {
        this(a.length() + b.length()); // ok, no whitespace
    }
}
