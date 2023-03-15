/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = java.util.GregorianCalendar, SubCal, java.util.List
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;

import com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalTypeGregCal.SubCal;

public class InputIllegalTypeSameFileNameFalsePositive
{
    InputIllegalTypeGregCal cal = AnObject.getInstance(); // ok
    Date date = null;
    SubCal subCalendar = null; // violation

    private static class AnObject extends InputIllegalTypeGregCal { // ok

        public static InputIllegalTypeGregCal getInstance() // ok
        {
            return null;
        }

    }

    private void foo() {
        List l; // ok
        java.io.File file = null; // ok
    }
    java.util.List<Integer> list = new ArrayList<>(); // violation
    private ArrayList<String> values; // ok
    private Boolean d; // ok
    private Boolean[] d1;
    private Boolean[][] d2;
}
