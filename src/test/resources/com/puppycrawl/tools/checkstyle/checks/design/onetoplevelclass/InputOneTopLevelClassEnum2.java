/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

// violation below 'Top-level class Input.* has to reside in its own source file.'
enum InputOneTopLevelClassEnum2inner1 {
    VALUE1, VALUE2;
}

public enum InputOneTopLevelClassEnum2 {
    VALUE1, VALUE2;
}

// violation below 'Top-level class Input.* has to reside in its own source file.'
enum InputOneTopLevelClassEnum2inner2 {
    VALUE1, VALUE2;
}
