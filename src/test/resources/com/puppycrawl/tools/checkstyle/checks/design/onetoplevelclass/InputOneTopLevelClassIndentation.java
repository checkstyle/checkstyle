/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public class InputOneTopLevelClassIndentation {
    // methods
}

 class ViolatingIndentedClass1 { // violation 'Top-level class ViolatingIndentedClass1 has to reside in its own source file.'
    // methods
 }

    class ViolatingIndentedClass2 { // violation 'Top-level class ViolatingIndentedClass2 has to reside in its own source file.'
        // methods
    }

interface ViolatingNonIndentedInterface { // violation 'Top-level class ViolatingNonIndentedInterface has to reside in its own source file.'
    // methods
}
