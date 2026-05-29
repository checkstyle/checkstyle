/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

enum InputOneTopLevelClassEnum2inner1 { // violation
    VALUE1, VALUE2;
}

public enum InputOneTopLevelClassEnum2 {
    VALUE1, VALUE2;
}

@Deprecated
enum InputOneTopLevelClassEnum2inner2 { // violation
    VALUE1, VALUE2;
}
