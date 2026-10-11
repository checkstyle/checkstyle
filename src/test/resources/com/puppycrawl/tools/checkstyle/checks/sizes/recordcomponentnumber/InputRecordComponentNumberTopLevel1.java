/*
RecordComponentNumber
max = (default)8
accessModifiers = (default)public, protected, package, private


*/


package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

// violation below 'Number of record components is 15'
public record InputRecordComponentNumberTopLevel1(int x, int y, int z,
                                                int a, int b, int c,
                                                int d, int e, int f,
                                                int g, int h, int i,
                                                int j, int k,
                                                 String... myVarargs) {

}
