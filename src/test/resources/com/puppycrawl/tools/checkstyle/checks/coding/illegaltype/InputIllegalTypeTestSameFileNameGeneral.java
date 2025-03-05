/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = List, InputIllegalTypeGregCal, java.io.File, ArrayList, Boolean
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

import com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalTypeGregCal.SubCal;

public class InputIllegalTypeTestSameFileNameGeneral
{
    InputIllegalTypeGregCal cal = AnObject.getInstance(); // violation, 'Usage of type InputIllegalTypeGregCal is not allowed'.
    java.util.Date date = null;
    SubCal subCalendar = null;

    private static class AnObject extends InputIllegalTypeGregCal { // violation, 'Usage of type InputIllegalTypeGregCal is not allowed'.

        public static InputIllegalTypeGregCal getInstance() // violation, 'Usage of type InputIllegalTypeGregCal is not allowed'.
        {
            return null;
        }

    }

    private void foo() {
        List l; // violation, 'Usage of type List is not allowed'.
        java.io.File file = null; // violation, 'Usage of type java.io.File is not allowed'.
    }
    java.util.List<Integer> list = new ArrayList<>(); // violation, 'Usage of type java.util.List is not allowed'.
    private ArrayList<String> values; // violation, 'Usage of type ArrayList is not allowed'.
    private Boolean d; // violation, 'Usage of type Boolean is not allowed'.
    private Boolean[] d1;
    private Boolean[][] d2;
}
