package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

//configuration: "illegalClassNames": Boolean[], Boolean[][]
public class InputIllegalTypeArrays {

    public Boolean[] array; // WARNING

    public Boolean[][] matrix; // WARNING

    public Boolean[] getArray() { // WARNING
        Boolean[] value = array != null ? array : new Boolean[0]; // WARNING
        return value;
    }

    public Boolean[][] getMatrix() { // WARNING
        Boolean[][] value = matrix != null ? matrix : new Boolean[0][0]; // WARNING
        return value;
    }

}
