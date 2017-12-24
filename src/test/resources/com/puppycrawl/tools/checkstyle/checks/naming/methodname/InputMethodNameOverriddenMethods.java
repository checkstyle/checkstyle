package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

public class InputMethodNameOverriddenMethods extends SomeClass
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
