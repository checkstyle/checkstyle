/*
IllegalIdentifierName
format = (default)(?i)^(?!(record|yield|var|permits|sealed|when|_)$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public class InputIllegalIdentifierNameWhen {

    int When = 10; // violation 'Name 'When' must match pattern .*'
    int when = 10; // violation 'Name 'when' must match pattern .*'

    int otherwhen = 11; // ok
}

class InputJava19PatternsTrickyWhenUsage {
    static String m1(Object when) { // violation 'Name 'when' must match pattern .*'
        when = new Object();
        return switch (when) {
            case Integer i when i >= 0 -> i.toString();
            default -> "2";
        };
    }

    static void m2(){
        int when = Integer.parseInt("when"); // violation 'Name 'when' must match pattern .*'
    }

    static String m3(Object x) {
        return switch (x) {
            // this is horrible, but it compiles
            case Integer when when when >= 0 -> when.toString();
            // violation above 'Name 'when' must match pattern .*'
            default -> "2";
        };
    }

    @when static class A {
        static class when{} // violation 'Name 'when' must match pattern .*'
    }

    static class B<when> {
        when getWhen() {
            return null;
        }
    }
}

@interface when{} // violation 'Name 'when' must match pattern .*'
