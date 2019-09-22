package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

/**
 * Config format = 'ABC.*DEF'(lowercased)
 * matchAcrossLines = true
 */
public class InputRegexpMultilineMultilineSupport {
    void method() {
// abc - violation
// def
// abc
    }

    void method2() {
// def
// abc
    }
}
