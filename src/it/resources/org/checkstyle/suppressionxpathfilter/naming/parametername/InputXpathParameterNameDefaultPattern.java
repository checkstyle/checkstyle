/*
ParameterName
accessModifiers = (default)public, protected, package, private
format = (default)^[a-z][a-zA-Z0-9]*$
ignoreOverridden = (default)false


*/

package org.checkstyle.suppressionxpathfilter.naming.parametername;

class InputXpathParameterNameDefaultPattern {

    void method1(int v_1) { // violation "Name 'v_1' must match pattern '^[a-z][a-zA-Z0-9]*$'."
    }
}
