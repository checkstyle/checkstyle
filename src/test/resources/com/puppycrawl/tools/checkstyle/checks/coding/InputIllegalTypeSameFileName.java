package com.puppycrawl.tools.checkstyle.checks.coding;
import java.util.ArrayList;
import java.awt.List;
import java.util.*;
import com.puppycrawl.tools.checkstyle.checks.coding.InputGregorianCalendar; 
import com.puppycrawl.tools.checkstyle.checks.coding.InputGregorianCalendar.SubCalendar;
//configuration: "illegalClassNames": List, GregorianCalendar, java.io.File, SubCalendar, ArrayList
public class InputIllegalTypeSameFileName
{
    InputGregorianCalendar cal = AnObject.getInstance(); //WARNING
    java.util.Date date = null;
    SubCalendar subCalendar = null; //WARNING
    
    private static class AnObject extends InputGregorianCalendar {

        public static InputGregorianCalendar getInstance() //WARNING
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
