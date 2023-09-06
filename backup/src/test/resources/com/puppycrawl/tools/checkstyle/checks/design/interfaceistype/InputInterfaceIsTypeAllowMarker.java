/*
InterfaceIsType
allowMarkerInterfaces = false


*/

package com.puppycrawl.tools.checkstyle.checks.design.interfaceistype;

/**
 * Test input for InterfaceIsTypeCheck
 * @author lkuehne
 **/
class InputInterfaceIsTypeAllowMarker
{
    // OK, has method, so is a type
    interface OK
    {
        void method();
    }

    // Marker interface, OK for some configurations
    interface Marker // violation
    {
    }

    // Always flagged
    interface ConstantPool // violation
    {
        boolean BAD = true;
    }

}
