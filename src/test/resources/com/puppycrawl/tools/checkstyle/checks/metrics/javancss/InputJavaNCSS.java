// should give an ncss of 35
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


//should give an ncss of 22
public class InputJavaNCSS {
    
    private Object mObject;
    
    //should count as 2
    private void testMethod1() {
        
        //should count as 1
        int x = 1, y = 2;
    }
    
    //should count as 4
    private void testMethod2() {
        
        int abc = 0;
        
        //should count as 2
        testLabel: abc = 1;  
    }    
     
    //should give an ncss of 12
    private void testMethod3() {
        
        int a = 0;
        switch (a) {
            case 1: //falls through
            case 2: System.identityHashCode("Hello"); break;
            default: break;
        }
        
        ItemListener lis = new ItemListener() {

            //should give an ncss of 2
            public void itemStateChanged(ItemEvent e) {          
                System.identityHashCode("Hello");
            }
        };  
    }
    
    //should give an ncss of 2
    private class TestInnerClass {
        
        private Object test;
    } 
}

//should give an ncss of 10
class TestTopLevelNestedClass {
    
    private Object mObject;
    
    //should give an ncss of 8
    private void testMethod() {
        
        for (int i=0; i<10; i++) {
            
            if (i==0) {
                
                //should count as 1
                int x = 1, y = 2;
            }
            else {
                int abc = 0;
                
                //should count as 2
                testLabel: abc = 1;      
            }
        }
    }
}

class Input0 {
    static { }
    { }
    public Input0() { }
}
