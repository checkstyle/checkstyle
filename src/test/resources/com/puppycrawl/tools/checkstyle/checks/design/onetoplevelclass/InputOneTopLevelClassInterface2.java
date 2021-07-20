/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

interface InputOneTopLevelClassInterface2inner1 { // violation
    int foo();
}

public interface InputOneTopLevelClassInterface2 {
    int foo();
}

interface InputOneTopLevelClassInterface2inner2 { // violation
    int foo();
}
