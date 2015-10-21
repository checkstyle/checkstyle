package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputIllegalThrowsIgnoreOverriddenMethods
             extends InputIllegalThrows
{
    @Override
    public void methodTwo() throws RuntimeException {
        
    }
    
    @java.lang.Override
    public java.lang.Throwable methodOne() throws RuntimeException {
        return null;
    }
}
