/*
LineLength
fileExtensions = (default)(null)
ignorePattern = (default)^(package|import) .*
max = (default)80


*/

// non-compiled with javac: package statement is different than folder
package com.nameofcompany.nameofdivision.nameofproject.systemtests.
        parallel.areaoftest.featuretested.flowtested;

public class InputLineLengthLongPackageStatement {
    @Override
    public String toString() {
        return "This is very long line that should be logged because it is not package";
        // violation above 'Line is longer than 80 characters (found 88).'
    }
}
