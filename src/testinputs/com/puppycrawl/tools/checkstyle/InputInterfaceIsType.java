////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test input for InterfaceIsTypeCheck
 * @author lkuehne
 **/
class InputInterfaceIsType
{
    // OK, has method, so is a type
    interface OK
    {
        void method();
    }

    // Marker interface, OK for some configurations
    interface Marker
    {
    }

    // Always flagged
    interface ConstantPool
    {
        boolean BAD = true;
    }

}
