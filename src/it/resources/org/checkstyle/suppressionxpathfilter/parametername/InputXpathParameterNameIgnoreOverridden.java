package org.checkstyle.suppressionxpathfilter.parametername;

class InputXpathParameterNameIgnoreOverridden {

    void method2(int V2) { // warn
    }

    @Override
    public boolean equals(Object V3) { // ok
        return true;
    }
}
