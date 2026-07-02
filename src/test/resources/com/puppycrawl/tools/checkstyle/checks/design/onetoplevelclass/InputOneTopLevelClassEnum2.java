/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

enum InputOneTopLevelClassEnum2inner1 { // violation 'Top-level class InputOneTopLevelClassEnum2inner1 has to reside in its own source file.'
    VALUE1, VALUE2;
}

public enum InputOneTopLevelClassEnum2 {
    VALUE1, VALUE2;
}

enum InputOneTopLevelClassEnum2inner2 { // violation 'Top-level class InputOneTopLevelClassEnum2inner2 has to reside in its own source file.'
    VALUE1, VALUE2;
}
