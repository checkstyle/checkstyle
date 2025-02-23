/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadTryWithResources {
    private void tryWithResources() throws Exception {
        try (AutoCloseable a = null) {}
        try (AutoCloseable a = null; AutoCloseable b = null) {}
        try (AutoCloseable a = null; AutoCloseable b = null; ) {}
        try (AutoCloseable a = null; AutoCloseable b = null; ) {}
        try (AutoCloseable a = null ) {} // violation
        try (AutoCloseable a = null; AutoCloseable b = null ) {} // violation
        try ( AutoCloseable a = null) {} // violation
    }
}
