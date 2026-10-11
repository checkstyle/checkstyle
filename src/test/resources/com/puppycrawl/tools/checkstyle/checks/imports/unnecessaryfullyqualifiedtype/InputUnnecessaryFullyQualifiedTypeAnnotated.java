/*
UnnecessaryFullyQualifiedType


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputUnnecessaryFullyQualifiedTypeAnnotated {

    @Target(ElementType.TYPE_USE)
    @interface Ann {
    }

    // violation below 'Unnecessary fully qualified type - java.lang.String.'
    private java.lang.@Ann String name;

}
