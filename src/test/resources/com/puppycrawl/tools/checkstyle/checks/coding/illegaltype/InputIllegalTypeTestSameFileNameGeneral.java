package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.awt.List;
import java.util.ArrayList;

import com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalTypeGregCal.SubCal;

/*
 * Config:
 * illegalClassNames = { List, InputIllegalTypeGregCal, java.io.File, ArrayList, Boolean }
 */
public class InputIllegalTypeTestSameFileNameGeneral
{
    InputIllegalTypeGregCal cal = AnObject.getInstance(); // violation
    java.util.Date date = null;
    SubCal subCalendar = null; // ok

    private static class AnObject extends InputIllegalTypeGregCal { // violation

        public static InputIllegalTypeGregCal getInstance() // violation
        {
            return null;
        }

    }

    private void foo() {
        List l; // violation
        java.io.File file = null; // violation
    }
    java.util.List<Integer> list = new ArrayList<>(); // violation
    private ArrayList<String> values; // violation
    private Boolean d; // violation
    private Boolean[] d1;
    private Boolean[][] d2;
}
