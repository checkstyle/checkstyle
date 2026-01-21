/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

// Compact source file to reproduce NPE #18680
class FirstClass {
    private FirstClass() {}
}

class SecondClass {
    private SecondClass() {}
}

class ThirdClass {
    public ThirdClass() {}
}
