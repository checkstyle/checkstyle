package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

/*
 * Config:
 * illegalClassNames = { Boolean, Boolean[][] }
 */
public class InputIllegalTypeTestPlainAndArraysTypes {

    public Boolean flag; // violation

    public Boolean[] array;

    public Boolean[][] matrix; // violation

    public Boolean getFlag() { // violation
        return flag;
    }

    public Boolean[] getArray() {
        Boolean[] value = array != null ? array : new Boolean[0];
        return value;
    }

    public Boolean[][] getMatrix() { // violation
        Boolean[][] value = matrix != null ? matrix : new Boolean[0][0]; // violation
        return value;
    }

}
