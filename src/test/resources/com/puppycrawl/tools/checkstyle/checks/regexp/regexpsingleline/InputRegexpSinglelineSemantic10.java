/*
RegexpSingleline
format = SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(
message =
ignoreCase = true
minimum = (default)0
maximum = (default)0
fileExtensions = (default)all files


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpsingleline;

public class InputRegexpSinglelineSemantic10 {
    public static void main(String[] args) {
        System.out.println("str"); // violation ''
    }
}
