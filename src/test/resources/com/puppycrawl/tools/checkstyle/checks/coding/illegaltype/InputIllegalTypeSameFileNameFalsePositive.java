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
    InputIllegalTypeGregCal cal = AnObject.getInstance();
    Date date = null;
    SubCal subCalendar = null; // violation, 'Usage of type SubCal is not allowed'.

    private static class AnObject extends InputIllegalTypeGregCal {

        public static InputIllegalTypeGregCal getInstance()
        {
            return null;
        }

    }

    private void foo() {
        List l;
        java.io.File file = null;
    }
    java.util.List<Integer> list = new ArrayList<>(); // violation, 'Usage of type java.util.List is not allowed'.
    private ArrayList<String> values;
    private Boolean d;
    private Boolean[] d1;
    private Boolean[][] d2;
}
