/*
RegexpMultiline
format = (a)bc.*def
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = true
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

/**
 * Config format = 'ABC.*DEF'(lowercased)
 * matchAcrossLines = true
 */
public class InputRegexpMultilineMultilineSupport {
    void method() {
// abc // violation, Line matches the illegal pattern
// def
// abc
    }

    void method2() {
// def
// abc
    }
}
