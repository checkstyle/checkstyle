package com.puppycrawl.tools.checkstyle.coding;

import java.awt.List;
import java.util.*;
import com.puppycrawl.tools.checkstyle.coding.GregorianCalendar; 
import com.puppycrawl.tools.checkstyle.coding.GregorianCalendar.SubCalendar;

public class InputIllegalTypeSameFileName
{
    GregorianCalendar cal = AnObject.getInstance();
    java.util.Date date = null;
    SubCalendar subCalendar = null;
    
    private static class AnObject extends GregorianCalendar {

        public static GregorianCalendar getInstance()
        {
            return null;
        }
        
    }
    
    private void foo() {
        List l;
        java.io.File file = null;
    }
}
