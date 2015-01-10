package com.puppycrawl.tools.checkstyle.naming;

public class InputMethodNameOverridenMethods extends SomeClass 
{
    @Override
    public void PUBLICfoo() { //Ignored due to impossibility of change by developer
        
    }
    
    @java.lang.Override
    protected void PROTECTEDfoo() { //Ignored due to impossibility of change by developer
        
    }
}

class SomeClass {
    public void PUBLICfoo() { //Warning (broken naming convention)
        
    }
    protected void PROTECTEDfoo() { //Warning (broken naming convention)
        
    }
}
