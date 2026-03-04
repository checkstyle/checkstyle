/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

abstract class InputRightCurlyAloneOrEmptyNoViolations {
    public @interface TestAnnotation {} // ok, valid syntax

    public @interface TestAnnotation2 {
        String someValue(); } // violation ''}' at column 29 should be alone on a line.'

    enum TestEnum {
        SOME_VALUE; } // violation ''}' at column 21 should be alone on a line.'

    enum TestEnum2 {} // ok, valid syntax

    void method1() {
    } // ok, valid syntax

    void method2() { int x = 1; } // violation ''}' at column 33 should be alone on a line.'

    void method3() {} // ok, valid syntax

    void method4() {
        int x = 1;
    } // ok, valid syntax

    abstract void abstractMethod(); // ok, no RCURLY

    InputRightCurlyAloneOrEmptyNoViolations() {
        int x = 1; } // violation ''}' at column 20 should be alone on a line.'

    InputRightCurlyAloneOrEmptyNoViolations(int a) {} // ok, valid syntax

    { int x = 1; } // violation ''}' at column 18 should be alone on a line.'

    static { int x = 1; } // violation ''}' at column 25 should be alone on a line.'

    void loops() {
        while (false) { int x = 1; } // violation ''}' at column 36 should be alone on a line.'
        for (;;) { int x = 1; } // violation ''}' at column 31 should be alone on a line.'
    }

    interface I {
        void interfaceMethod(); // ok, no RCURLY
    } // ok, valid syntax

    record R() {} // ok, valid syntax

    record R2() {
        public R2 { int x = 1; } // violation ''}' at column 32 should be alone on a line.'
    }

    void switches(int x) {
        switch (x) {} // ok, valid syntax
        switch (x) {
            case 1: { int y = 1; } // ok, valid syntax
            case 2: {}
        } // ok, valid syntax
    }

    void trys() {
        try { int x = 1; } // ok, valid syntax
        catch (Exception e) {
            int x = 1; } // violation ''}' at column 24 should be alone on a line.'
        finally {
            int x = 1; } // violation ''}' at column 24 should be alone on a line.'
    }
}
