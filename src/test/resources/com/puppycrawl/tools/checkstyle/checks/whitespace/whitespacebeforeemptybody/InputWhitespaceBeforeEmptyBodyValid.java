/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, \
         CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF, \
         LITERAL_WHILE, LITERAL_FOR, LITERAL_DO, \
         STATIC_INIT, \
         LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_SYNCHRONIZED, LITERAL_SWITCH, \
         LAMBDA, LITERAL_NEW


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyValid {

    void method1() {}
    public void method2() {}
    private static void method3() {}

    public InputWhitespaceBeforeEmptyBodyValid() {}

    class InnerClass {}
    interface InnerInterface {}
    enum InnerEnum {}
    record InnerRecord() {}
    @interface InnerAnnotation {}

    void testLoops() {
        boolean b = true;

        // Loops with proper space
        while (b) {}
        for (int i = 0; i < 1; i++) {}
        do {} while (b);
    }

    static {}

    {}

    void testLambda() {
        Runnable r = () -> {};
    }

    abstract class Anonymous {}

    Anonymous anon = new Anonymous() {};
}
