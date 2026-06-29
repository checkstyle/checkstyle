/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

// violation below 'Top-level class Input.* has to reside in its own source file.'
interface InputOneTopLevelClassInterface3inner1 {
    int foo();
}

public interface InputOneTopLevelClassInterface3 {
    int foo();
}

// violation below 'Top-level class Input.* has to reside in its own source file.'
interface InputOneTopLevelClassInterface3inner2 {
    int foo();
}
