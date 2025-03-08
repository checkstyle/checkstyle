/*
IllegalIdentifierName
format = (default)^(?!var$|\\S*\\$)\\S+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public class InputIllegalIdentifierNameUnnamedVariables {
    void m(Object o) {
        int _ = sideEffect();
        int __ = sideEffect();
        for (Integer _ : new int[0]) {

        }
        for (Integer BAD_ : new int[0]) {

        }
        for (Integer _BAD : new int[0]) {

        }
        try (var _ = lock()) {
        }
        catch (Exception _) {
        }

        switch(o) {
            case Integer _ : {}
            default: {}
        }
        if (o instanceof Integer _) {}

        switch (o) {
            case R(int _ ,int _): {}
            default : {}
        }
        if (o instanceof R(int _ , _)) {}
    }

    int sideEffect() {
        return 0;
    }

    AutoCloseable lock() {
        return null;
    }

    record R(int x, int y) {}
}
