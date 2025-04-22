/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeAboveComments { // violation, 'Missing a Javadoc comment.'

    /**
     *
     */
    /* */
    public class InputMissingJavadocTypeAboveComments2 {
    }

    /**
     *
     */
    public class Myclass { } /* My class */

    public class Myclass2 { } // violation, 'Missing a Javadoc comment.'
}
