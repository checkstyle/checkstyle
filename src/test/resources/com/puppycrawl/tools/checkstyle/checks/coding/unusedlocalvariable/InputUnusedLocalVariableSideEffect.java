/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLocalVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding;

class InputUnusedLocalVariableSideEffect {

    void testMethodCall() {
        int x = foo(); // should NOT be flagged
    }

    void testConstructor() {
        MyObject obj = new MyObject(); // should NOT be flagged
    }

    int foo() {
        return 1;
    }

    static class MyObject {
        MyObject() {
            System.out.println("side effect");
        }
    }
}