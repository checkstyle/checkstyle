/*
MissingCtor


*/

package com.puppycrawl.tools.checkstyle.checks.coding.missingctor;

public class InputMissingCtorNestedClasses { // violation, 'Class should define a constructor'
    class Inner1 { // violation, 'Class should define a constructor'
        class Inner2 { // violation, 'Class should define a constructor'

        }
    }
}
