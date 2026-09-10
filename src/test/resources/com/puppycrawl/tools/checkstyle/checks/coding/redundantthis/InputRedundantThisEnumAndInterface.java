/*
RedundantThis
checkMethods = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisEnumAndInterface {
    enum MyEnum {
        A, B;
        private String name;
        public void setName(String name) {
            this.name = name;
        }
        public void printName() {
            System.out.println(this.name);
            // violation above 'Redundant "this", field 'name' can be accessed directly.'
        }
    }

    interface MyInterface {
        default void doSomething() {
            this.doSomethingElse(); // ok, checkMethods is false
        }
        default void doSomethingElse() {}
    }
}
