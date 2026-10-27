/*
ParameterName
accessModifiers = (default)public, protected, package, private
format = (default)^[a-z][a-zA-Z0-9]*$
ignoreOverridden = true


*/

package org.checkstyle.suppressionxpathfilter.naming.parametername;

class InputXpathParameterNameIgnoreOverridden {

    void method2(int V2) { // violation "Name 'V2' must match pattern '^[a-z][a-zA-Z0-9]*$'."
    }

    @Override
    public boolean equals(Object V3) {
        return true;
    }
}
