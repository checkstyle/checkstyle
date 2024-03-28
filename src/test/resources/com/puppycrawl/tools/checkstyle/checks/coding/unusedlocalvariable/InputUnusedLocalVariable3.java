/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariable3 {
    class Parent {
        protected int a = 0;
        public Parent(Child d) {
            a = 1;
        }
    }

    class Child extends InputUnusedLocalVariable3.Parent {
        protected int b = 0;
        public Child(Child d) {
            InputUnusedLocalVariable3.this.super(d);
            int a = 0; // violation, unused variable 'a'
            System.out.println(super.a);
        }
    }
}
