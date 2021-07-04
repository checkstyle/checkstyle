package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

/*
 * Config:
 * illegalClassNames = { Boolean[], Boolean[][] }
 */
public class InputIllegalTypeArrays {

    public Boolean[] array; // violation

    public Boolean[][] matrix; // violation

    public Boolean[] getArray() { // violation
        Boolean[] value = array != null ? array : new Boolean[0]; // violation
        return value;
    }

    public Boolean[][] getMatrix() { // violation
        Boolean[][] value = matrix != null ? matrix : new Boolean[0][0]; // violation
        return value;
    }

}
