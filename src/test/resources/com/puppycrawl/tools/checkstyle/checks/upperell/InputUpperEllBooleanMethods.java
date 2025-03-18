package com.puppycrawl.tools.checkstyle.checks.upperell;

public class InputUpperEllBooleanMethods {
    /** fully qualified Boolean instantiation in a method. **/
    Boolean getBoolean()
    {
        return new Boolean(true);
    }
}
