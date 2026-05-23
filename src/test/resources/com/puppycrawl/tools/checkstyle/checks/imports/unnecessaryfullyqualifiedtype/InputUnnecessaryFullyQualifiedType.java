/*
UnnecessaryFullyQualifiedType


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

import java.util.Optional;

public class InputUnnecessaryFullyQualifiedType {

    // violation below, 'Unnecessary fully qualified type - java.util.Map.'
    private java.util.Map<Boolean, Optional<String>> choiceMap;

    // violation below, 'Unnecessary fully qualified type - java.lang.String.'
    private java.lang.String name;

    private Optional<String> present;

    private Optional<String> alsoPresent;

    void create() {
        // violation below, 'Unnecessary fully qualified type - java.util.HashMap.'
        Object map = new java.util.HashMap<String, String>();
    }

    // violation below, 'Unnecessary fully qualified type - java.io.IOException.'
    void method() throws java.io.IOException {
        // violation below, 'Unnecessary fully qualified type - java.util.List.'
        java.util.List<String> list = null;
        // violation below, 'Unnecessary fully qualified type - java.util.Set.'
        Object cast = (java.util.Set<String>) null;
        // violation below, 'Unnecessary fully qualified type - java.util.Collection.'
        boolean isCollection = cast instanceof java.util.Collection;
    }

    void notAType() {
        // a method/field access chain is not a type reference
        java.lang.System.out.println("ok");
        java.util.Collections.emptyList();
    }

    java.util.Map.Entry<String, String> nestedTypeReferenceIsNotReported() {
        return null;
    }
}
