/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

interface InputOneTopLevelClassInterface3inner1 { // violation 'Top-level class InputOneTopLevelClassInterface3inner1 has to reside in its own source file.'
    int foo();
}

public interface InputOneTopLevelClassInterface3 {
    int foo();
}

interface InputOneTopLevelClassInterface3inner2 { // violation 'Top-level class InputOneTopLevelClassInterface3inner2 has to reside in its own source file.'
    int foo();
}
