/*
MissingCtor


*/

package com.puppycrawl.tools.checkstyle.checks.coding.missingctor;

public class InputMissingCtorEmptyClass // violation
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
