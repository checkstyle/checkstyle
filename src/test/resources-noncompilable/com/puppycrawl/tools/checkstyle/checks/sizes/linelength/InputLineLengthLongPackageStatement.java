/*
LineLength
fileExtensions = (default)
ignorePattern = (default)^(package|import) .*
max = (default)80


*/

//non-compiled with javac: contains different package by demand of test
package com.nameofcompany.nameofdivision.nameofproject.systemtests.
        parallel.areaoftest.featuretested.flowtested;

public class InputLineLengthLongPackageStatement {
    @Override
    public String toString() {
        return "This is very long line that should be logged because it is not package";// violation
    }
}
