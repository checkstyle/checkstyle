/*
RegexpMultiline
format = (LOG\\.(?:info|debug|error)\\(\\s*.*)(password)(.*\\))
message = Do not log sensitive data
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = true
reportGroup = 2
fileExtensions = (default)""

*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

public class InputRegexpMultilineReportGroupLogging {
    private final Log LOG = new Log();

    void method(String user, String password) {
        LOG.error("Login failed for user: " +
                user +
                " with password: " +
                password); // violation 'Do not log sensitive data'
    }

    static class Log {
        void error(String s) { }
    }
}
