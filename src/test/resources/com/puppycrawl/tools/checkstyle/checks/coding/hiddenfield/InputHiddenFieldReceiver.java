/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public class InputHiddenFieldReceiver { // ok
    public void foo4(InputHiddenFieldReceiver this) {}

    private class Inner {
        public Inner(InputHiddenFieldReceiver InputHiddenFieldReceiver.this) {}
    }
}
