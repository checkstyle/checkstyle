/*
ParameterName
accessModifiers = (default)public, protected, package, private
format = ^[a-z][_a-zA-Z0-9]+$
ignoreOverridden = (default)false


*/

package org.checkstyle.suppressionxpathfilter.naming.parametername;

class InputXpathParameterNameDifferentPattern {

    void method2(int V2) { // violation "Name 'V2' must match pattern '^[a-z][_a-zA-Z0-9]+$'."
    }
}
