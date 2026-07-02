/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

interface InputOneTopLevelClassInterface2inner1 { // violation 'Top-level class InputOneTopLevelClassInterface2inner1 has to reside in its own source file.'
    int foo();
}

public interface InputOneTopLevelClassInterface2 {
    int foo();
}

interface InputOneTopLevelClassInterface2inner2 { // violation 'Top-level class InputOneTopLevelClassInterface2inner2 has to reside in its own source file.'
    int foo();
}
