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

    @Deprecated
    public
    class MyClass3 {
    }

    public class MyClass4 {
        void method() {
            while(true) {/*foo*/}
        }
    }

    public void testEmptyLineAfter() {
        if(true)

        { // violation
        }

        int x = 10;
        switch (x) {
            case 10:


            { // violation
                break;
            }

            case 20: {
                break;
            }
        }
    }

    public void testWithCommentAfter() {
        if(true)
        // A block of comment here
        // Another block of comment
        { // violation

        }

        // A block of comment before brace
        if(true) {
        }

        int x = 20;

        switch(x) {
            case 10:
                // A block of comment here
                // Another block of comment
            { // violation
                break;
            }

            case 20: {
                break;
            }
        }
    }

}
