/*
RegexpMultiline
format = (a)bc\.*?def
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = true
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

/**
 * Config format = 'ABC.*?DEF'(lowercased)
 * matchAcrossLines = true
 */
public class InputRegexpMultilineMultilineSupport2 {
    void method() {
// abc // violation
// def
// abc // violation
    }

    void method2() {
// def
// abc
    }
}
