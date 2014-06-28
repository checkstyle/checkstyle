package com.puppycrawl.tools.checkstyle.coding;

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
