////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

public class InputFinalClass 
{
    private InputFinalClass() {}
}

class test2 {}
class test3 
{
   class test4 
   {
       private test4() {}
   }
}

class test5
{
    private test5() {}
    test5(int i) {}
}

class test6 
{
    public test6() {}
}
