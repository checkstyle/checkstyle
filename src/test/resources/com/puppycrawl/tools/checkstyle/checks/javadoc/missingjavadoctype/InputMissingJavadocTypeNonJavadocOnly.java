/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeNonJavadocOnly { // violation, 'Missing a Javadoc comment.'
    /* This is NOT a Javadoc comment */
    public class InnerClass { // violation, 'Missing a Javadoc comment.'
    }

    /*
     This is NOT a Javadoc comment
    */
    public class InnerClass2 { // violation, 'Missing a Javadoc comment.'
    }

    // This is NOT a Javadoc comment
    public class InnerClass3 { // violation, 'Missing a Javadoc comment.'
    }

}
