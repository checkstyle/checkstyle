/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public class InputOneTopLevelClassIndentation {
    // methods
}

// violation below 'Top-level class ViolatingIndentedClass1 has to reside in its own source file.'
 class ViolatingIndentedClass1 {
    // methods
 }
// violation below 'Top-level class ViolatingIndentedClass2 has to reside in its own source file.'
    class ViolatingIndentedClass2 {
        // methods
    }
// violation below 'Top-level class Violating.* has to reside in its own source file.'
interface ViolatingNonIndentedInterface {
    // methods
}
