/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.ArrayList;
import java.util.function.Predicate;

public class InputUnusedLocalVariable3 {
    class Parent {
        protected int a = 0;
        public Parent() {
            a = 1;
        }
    }

    class Child extends InputUnusedLocalVariable3.Parent {
        public Child(Child d) {
            InputUnusedLocalVariable3.this.super();
            int a = 0; // violation, unused variable 'a'
            System.out.println(super.a);
        }
    }
}
