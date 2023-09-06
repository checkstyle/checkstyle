/*
OuterTypeFilename


*/

//non-compiled with javac: contains different class name by demand of test
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

public record IncorrectName1(int x, int y, String str) { // violation
}
