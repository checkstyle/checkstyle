/*
OuterTypeFilename


*/

// non-compiled with javac: contains different class name by demand of test
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

// violation below 'The name of the outer type and the file do not match.'
public record IncorrectName1(int x, int y, String str) {
}
