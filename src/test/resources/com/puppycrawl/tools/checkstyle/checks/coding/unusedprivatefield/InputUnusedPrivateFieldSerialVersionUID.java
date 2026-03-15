/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.io.Serializable;

public class InputUnusedPrivateFieldSerialVersionUID implements Serializable {

    private static final long serialVersionUID = 1L; // ok, excluded
    // violation below 'Unused private field 'otherConstant''
    private static final long otherConstant = 2L;

}
