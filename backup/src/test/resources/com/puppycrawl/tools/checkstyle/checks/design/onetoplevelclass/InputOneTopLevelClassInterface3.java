/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

interface InputOneTopLevelClassInterface3inner1 { // violation
    int foo();
}

public interface InputOneTopLevelClassInterface3 {
    int foo();
}

interface InputOneTopLevelClassInterface3inner2 { // violation
    int foo();
}
