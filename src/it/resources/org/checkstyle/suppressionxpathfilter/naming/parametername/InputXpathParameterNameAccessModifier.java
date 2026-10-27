/*
ParameterName
accessModifiers = public
format = ^[a-z][a-z0-9][a-zA-Z0-9]*$
ignoreOverridden = (default)false


*/

package org.checkstyle.suppressionxpathfilter.naming.parametername;

class InputXpathParameterNameAccessModifier {

    private void method1(int a) {
    }

    // violation below "Name 'b' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'."
    public void method2(int b) {
    }
}
