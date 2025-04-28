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
    // ok, has method, so is a type
    interface OK
    {
        void method();
    }

    // Marker interface, OK for some configurations
    interface Marker // violation, 'interfaces should describe a type and hence have methods.'
    {
    }

    // Always flagged
    interface ConstantPool // violation, 'interfaces should describe a type and hence have methods.'
    {
        boolean BAD = true;
    }

}
