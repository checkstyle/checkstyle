/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestAloneOrSinglelineOther {

    void foo25() {
        for (int i = 0; i < 10; i++) {
            System.identityHashCode("Hello, world!");
        }}
    // 2 violations above:
    // ''}' at column 9 should be alone on a line.'
    // ''}' at column 10 should be alone on a line.'

    void foo26() {
        for (int i = 0; i < 10; i++) {
            System.identityHashCode("Hello, world!");}}
    // 2 violations above:
    // ''}' at column 54 should be alone on a line.'
    // ''}' at column 55 should be alone on a line.'

    void foo27() {
        for (int i = 0; i < 10; i++) {for (int j = 0; j < 15; j++) {int a;}}}
    // 3 violations above:
    // ''}' at column 75 should be alone on a line.'
    // ''}' at column 76 should be alone on a line.'
    // ''}' at column 77 should be alone on a line.'

    private java.util.ArrayList<Integer> foo28(int delta) {
        return new java.util.ArrayList<Integer>() {
            @Override public int size() { return Math.max(0, super.size() + 1);};
        };
    }

    private void foo29() {
        boolean flag = true;
        if (flag) {
            System.identityHashCode("heh");
            flag = !flag; } String. // violation ''}' at column 27 should be alone on a line'
                CASE_INSENSITIVE_ORDER.equals("Xe-xe");
    }

    void foo30() {
        if (true) {
            getClass();} // violation ''}' at column 24 should be alone on a line'

        for (int i = 0; i == 0; i++) {
            getClass();} // violation ''}' at column 24 should be alone on a line'

        while (true) {
            getClass();} // violation ''}' at column 24 should be alone on a line'
    }

    public void emptyBlocks() {
        try {
            // comment
        } catch (RuntimeException e) { // violation ''}' at column 9 should be alone on a line'
            new Object();
        } catch (Exception e) { // violation ''}' at column 9 should be alone on a line'
            // comment
        } catch (Throwable e) { // violation ''}' at column 9 should be alone on a line'
        } finally { // violation ''}' at column 9 should be alone on a line'
            // comment
        }

        do {
        } while (true); // violation ''}' at column 9 should be alone on a line'
    }

    public void codeAfterLastRightCurly() {
        while (new Object().equals(new Object())) {
        }; // violation ''}' at column 9 should be alone on a line'
        for (int i = 0; i < 1; i++) { }; // violation ''}' at column 39 should be alone on a line'
    }

    public @interface TestAnnotation {}

    public @interface TestAnnotation1{ String value(); }

    public @interface TestAnnotation2 {
        String value();} // violation ''}' at column 24 should be alone on a line'

    public @interface TestAnnotation3 {
        String value();
    }

    public @interface TestAnnotation4 { String value();
    }

    interface Interface1
    {
        int i = 1;
        public void meth1(); } // violation ''}' at column 30 should be alone on a line'

    interface Interface2
    { int i = 1; public void meth1(); }

    private void foo28() {
        if (true) { return; } else { } if (false) {
            // 2 violations above:
            // ''}' at column 29 should be alone on a line.'
            // ''}' at column 38 should be alone on a line.'
        }
    }
}
