/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

/** tests for static fields */
class InputHiddenFieldStatic
{
    private static int hidden;

    public static void staticMethod()
    {
        int hidden; // violation, ''hidden' hides a field'
    }

    public void method()
    {
        int hidden; // violation, ''hidden' hides a field'
    }

    static
    {
        int hidden; // violation, ''hidden' hides a field'
    }

    {
        int hidden; // violation, ''hidden' hides a field'
    }
}

/** tests static methods & initializers */
class StaticMethods1
{
    private int notHidden;

    public static void method()
    {
        // local variables of static methods don't hide instance fields.
        int notHidden;
    }

    static
    {
        // local variables of static initializers don't hide instance fields
        int notHidden;
    }

    private int x;
    private static int y;
    static class Inner {
        void useX(int x) {
            x++;
        }
        void useY(int y) {
            y++;
        }
    }
}
