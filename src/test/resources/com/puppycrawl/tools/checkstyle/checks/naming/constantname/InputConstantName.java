/*
ConstantName
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.constantname;

import java.io.ObjectStreamField;

public class InputConstantName
{
    private static final long serialVersionUID = 1L; //should be ignored
    private static final ObjectStreamField[] serialPersistentFields = {}; // should be ignored too
    static int value1 = 10;
    final int value2 = 10;
}
