/*
SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck=paramnum

com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
id = ignore
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
id =
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck
id = ignore
excludedClasses = (default)^$

com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck
max = (default)7
ignoreOverriddenMethods = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF

com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck
illegalClassNames = (default)Error, Exception, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.Exception, java.lang.RuntimeException, java.lang.Throwable

com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck
scope = PRIVATE
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF

*/

package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;

public class InputSuppressWarningsFilterById { // violation 'Missing a Javadoc comment'

    @SuppressWarnings("checkstyle:ignore")
    private int A1 = 1; // filtered violation ''A1' must match pattern \Q'^[a-z][a-zA-Z0-9]*$'\E'

    @SuppressWarnings("checkstyle:ignore") // filtered violation 'Uncommented main method found'
    public static void main(String[] args) {

    }

}
