package com.puppycrawl.tools.checkstyle.coding;
import java.util.ArrayList;
import java.awt.List;
import java.util.*;
import com.puppycrawl.tools.checkstyle.coding.GregorianCalendar; 
import com.puppycrawl.tools.checkstyle.coding.GregorianCalendar.SubCalendar;
//configuration: "illegalClassNames": List, GregorianCalendar, java.io.File, SubCalendar, ArrayList
public class InputIllegalTypeSameFileName
{
    GregorianCalendar cal = AnObject.getInstance(); //WARNING
    java.util.Date date = null;
    SubCalendar subCalendar = null; //WARNING
    
    private static class AnObject extends GregorianCalendar {

        public static GregorianCalendar getInstance() //WARNING
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
