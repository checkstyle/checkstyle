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
     * java doc comment
     */
    /* */
    //
    public class InputMissingJavadocTypeAboveComments2 { }

    /**
     *
     */

    public class Myclass { } /* My class */

    /**
     *
     */

    // singline comment missing javadoc
    public class InputMissingJavadocTypeAboveComments3 { }


    /**
     *
     */

    /* singline comment missing javadoc */
    public class InputMissingJavadocTypeAboveComments4 { }

    /**
     *
     */ public class Myclass2 { }

    /**
     *  InputJavadocTypeUnusedParamInJavadocForClass.
     *
     *  @param <T> This is type parameter.
     */
    /* This is Block Comment
        This is part of block comment
        This is also part of block comment
    */
    public class InputJavadocTypeWithBlockComment { }

  /**javadoc*/ public class Myclass5 { }
}
