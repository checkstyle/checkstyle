/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisNestedClass {
    private int x = 10;
    private String name = "Outer Class";

    class Inner {
        private int x = 20;  // Same field name as outer
        private String name = "Inner Class";

        void display() {
            System.out.println("Inner x (this.x):        " + this.x);
            // violation above, 'Redundant "this", field 'x' can be accessed directly.'

            System.out.println("Inner name (this.name):  " + this.name);
            // violation above, 'Redundant "this", field 'name' can be accessed directly.'

            System.out.println("Outer x (Outer.this.x): "
                    + InputRedundantThisNestedClass .this.x);
            System.out.println("Outer name (Outer.this.name): "
                    + InputRedundantThisNestedClass .this.name);
        }

        void modify() {
            this.x = 99;
            // violation above, 'Redundant "this", field 'x' can be accessed directly.'

            InputRedundantThisNestedClass.this.x = 55;
            InputRedundantThisNestedClass.this.show();
        }
    }

    void show() {
        Inner inner = new Inner();
        inner.display();
        System.out.println("\n--- After modify() ---");
        inner.modify();
        inner.display();
    }
}
