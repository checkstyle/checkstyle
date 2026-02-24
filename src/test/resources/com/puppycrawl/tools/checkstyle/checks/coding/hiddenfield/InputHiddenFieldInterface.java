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

interface InputHiddenFieldInterface
{
    public static int notHidden = 0;

    // not a violation
    public void noShadow(int notHidden);
}
