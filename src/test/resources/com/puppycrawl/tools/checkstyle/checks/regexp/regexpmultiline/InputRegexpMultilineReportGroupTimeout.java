/*
RegexpMultiline
format = (setConnectionTimeout\\(\\s*)([0-9]+)(\\s*\\))
message = Connection timeout must not be Hardcoded
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = true
reportGroup = 2
fileExtensions = (default)""

*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

public class InputRegexpMultilineReportGroupTimeout {
    void method(Client client) {
        // violation 2 lines below 'Connection timeout must not be Hardcoded'
        client.setConnectionTimeout(
                1000
        );
    }

    static class Client {
        void setConnectionTimeout(int timeout) { }
    }
}
