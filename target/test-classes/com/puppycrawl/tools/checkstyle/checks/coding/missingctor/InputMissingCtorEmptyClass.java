/*
MissingCtor


*/

package com.puppycrawl.tools.checkstyle.checks.coding.missingctor;

public class InputMissingCtorEmptyClass // violation, 'Class should define a constructor'
{
}
// we shouldn't flag abstract classes
abstract class AbstractClass {
}

// this class has ctor
class CorrectClass {
    CorrectClass() {
    }
}
