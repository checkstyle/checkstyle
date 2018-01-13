package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

//configuration: "illegalClassNames": Boolean, Boolean[][]
public class InputIllegalTypePlainAndArrays {

    public Boolean flag; // WARNING

    public Boolean[] array;

    public Boolean[][] matrix; // WARNING

    public Boolean getFlag() { // WARNING
        return flag;
    }

    public Boolean[] getArray() {
        Boolean[] value = array != null ? array : new Boolean[0];
        return value;
    }

    public Boolean[][] getMatrix() { // WARNING
        Boolean[][] value = matrix != null ? matrix : new Boolean[0][0]; // WARNING
        return value;
    }

}
