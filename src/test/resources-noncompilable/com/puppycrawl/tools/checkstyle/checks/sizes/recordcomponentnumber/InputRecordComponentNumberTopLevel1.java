/*
RecordComponentNumber
max = (default)8
accessModifiers = (default)public, protected, package, private


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

public record InputRecordComponentNumberTopLevel1(int x, int y, int z, // violation
                                                int a, int b, int c,
                                                int d, int e, int f,
                                                int g, int h, int i,
                                                int j, int k,
                                                 String... myVarargs) {

}
