/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

// violation below 'Top-level class Input.* has to reside in its own source file.'
interface InputOneTopLevelClassInterface2inner1 {
    int foo();
}

public interface InputOneTopLevelClassInterface2 {
    int foo();
}

// violation 2 lines below 'Top-level class Input.* has to reside in its own source file.'
@Deprecated
interface InputOneTopLevelClassInterface2inner2 {
    int foo();
}
