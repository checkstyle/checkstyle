////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyDefault
{
    private interface PrivateInterface
    {
    }

    interface PackageInnerInterface
    {
    }

    protected interface ProtectedInnerInterface
    {
    }

    public interface PublicInnerInterface
    {
    }

    private
    class 
    MyClass1 {
    }

    class 
    MyClass2 {
    }

    private
    interface
    MyInterface1 {
    }

    interface
    MyInterface2 {
    }

    protected
    enum
    MyEnum {
    }
    
    private
    @interface
    MyAnnotation {
    }
}
