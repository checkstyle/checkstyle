package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;
import java.util.ArrayList;
import java.awt.List;
import java.util.*;
import com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalTypeGregorianCalendar;
import com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalTypeGregorianCalendar.SubCalendar;
//configuration: "illegalClassNames": List, GregorianCalendar, java.io.File, SubCalendar, ArrayList
public class InputIllegalTypeSameFileName
{
    InputIllegalTypeGregorianCalendar cal = AnObject.getInstance(); //WARNING
    java.util.Date date = null;
    SubCalendar subCalendar = null; //WARNING
    
    private static class AnObject extends InputIllegalTypeGregorianCalendar {

        public static InputIllegalTypeGregorianCalendar getInstance() //WARNING
        {
            return null;
        }
        
    }
    
    private void foo() {
        List l; //WARNING
        java.io.File file = null; //WARNING
    }
    java.util.List<Integer> list = new ArrayList<>(); //WARNING
    private ArrayList<String> values;
}
