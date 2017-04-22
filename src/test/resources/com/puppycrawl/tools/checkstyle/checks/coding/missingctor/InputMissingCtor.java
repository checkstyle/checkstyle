package com.puppycrawl.tools.checkstyle.checks.coding.missingctor;

public class InputMissingCtor
{
}
// we shouln't flag abstract classes
abstract class AbstactClass {
}

// this class has ctor
class CorrectClass {
    CorrectClass() {
    }
}
