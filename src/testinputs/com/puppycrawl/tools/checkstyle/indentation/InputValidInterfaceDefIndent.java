/*
 * InputValidInterfaceDefIndent.java
 *
 * Created on December 12, 2002, 12:05 AM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public interface InputValidInterfaceDefIndent {

    void myfunc();

    
    interface myInterface2 {
    }

    public interface myInterface3 extends myInterface2 { }
    
    public interface myInterface4 
        extends myInterface2 
    { 
        void myFunc2();
    }


}
