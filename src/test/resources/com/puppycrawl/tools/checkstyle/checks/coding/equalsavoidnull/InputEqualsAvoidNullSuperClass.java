/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public abstract class InputEqualsAvoidNullSuperClass {
   protected String stringFromBaseClass = "ABC";
}

class DerivedClass extends InputEqualsAvoidNullSuperClass {
   protected String classField = "DEF";

    void m1() {
        if (this.stringFromBaseClass.equals("JKHKJ")) { // ok
        }
    }

    void m2() {
        if (this.classField.equals("JKHKJ")) { // violation 'left.*of.*equals'
        }
    }
}
