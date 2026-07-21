/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// violation below 'Missing a Javadoc comment.'
public class InputMissingJavadocTypeNonJavadocOnly {
    /* This is NOT a Javadoc comment */
    // violation below 'Missing a Javadoc comment.'
    public class InnerClass {
    }

    /*
     This is NOT a Javadoc comment
    */
    // violation below 'Missing a Javadoc comment.'
    public class InnerClass2 {
    }

    // This is NOT a Javadoc comment
    // violation below 'Missing a Javadoc comment.'
    public class InnerClass3 {
    }

}
