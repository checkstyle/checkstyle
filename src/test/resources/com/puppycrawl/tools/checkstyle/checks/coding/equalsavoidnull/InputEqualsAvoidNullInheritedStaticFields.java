/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullInheritedStaticFields {
    static class Parent {
        static String inherited = "value";
    }

    static class Child extends Parent {
        static String declared = "value";

        void inheritedFields() {
            Child.inherited.equals("value");
            Child.inherited.equalsIgnoreCase("VALUE");
        }

        void declaredFields() {
            Child.declared.equals("value"); // violation 'left.*of.*equals'
            Child.declared.equalsIgnoreCase("VALUE"); // violation 'left.*of.*equalsIgnoreCase'
        }

        static Object declaredObject = new Object();

        void nonStringField() {
            Child.declaredObject.equals("value");
        }
    }
}
