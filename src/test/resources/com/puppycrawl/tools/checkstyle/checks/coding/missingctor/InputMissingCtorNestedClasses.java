/*
MissingCtor


*/

package com.puppycrawl.tools.checkstyle.checks.coding.missingctor;

public class InputMissingCtorNestedClasses { // violation
    class Inner1 { // violation
        class Inner2 { // violation

        }
    }
}
