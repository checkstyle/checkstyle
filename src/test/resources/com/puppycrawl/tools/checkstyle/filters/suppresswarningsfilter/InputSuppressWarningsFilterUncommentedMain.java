/*
SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)(null)

com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck
id = ignore
excludedClasses = (default)^$

com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck
scope = PRIVATE
excludeScope = (default)null
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF

*/
package com.puppycrawl.tools.checkstyle.filters.suppresswarningsfilter;

/**
 * Test input for using comments to suppress violations.
 *
 * @author Trevor Robinson
 */
class InputSuppressWarningsFilterUncommentedMain {

    // filtered violation below 'Uncommented main method'
    @SuppressWarnings("checkstyle:uncommentedmain")
    public static void main(String[] args) { }

    static class TestClass1 { // violation 'Missing a Javadoc comment'
        // filtered violation below 'Uncommented main method found'
        @SuppressWarnings("uncommentedmain")
        public static void main(String[] args) { }
    }

    static class TestClass2 { // violation 'Missing a Javadoc comment'
        // filtered violation below 'Uncommented main method found'
        @SuppressWarnings("UncommentedMain")
        public static void main(String[] args) { }
    }

    static class TestClass3 { // violation 'Missing a Javadoc comment'
        // filtered violation below 'Uncomm.* main method'
        @SuppressWarnings("checkstyle:UncommentedMain")
        public static void main(String[] args) { }
    }

    // violation below 'Missing a Javadoc comment'
    @SuppressWarnings("checkstyle:javadoctype")
    public static abstract class Task { }
}
