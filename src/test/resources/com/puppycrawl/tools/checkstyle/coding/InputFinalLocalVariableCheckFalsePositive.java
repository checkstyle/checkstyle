package com.puppycrawl.tools.checkstyle.coding;

public class InputFinalLocalVariableCheckFalsePositive
{
    public void method()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for ( int i = 0, s0 = list.size(); i < s0; i++ )
        {
        }
    }
}
