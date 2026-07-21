/*
MissingJavadocType
excludeScope = (default)null
scope = (default)public
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// violation below 'Missing a Javadoc comment.'
public class InputMissingJavadocTypeAboveComments {

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

    // violation below 'Missing a Javadoc comment.'
    public class Myclass2 { }

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
     */ public class Myclass3 { }

  /**javadoc*/ public class Myclass5 { }

  /** Test class for variable naming in for each clause.
   *
   */
  /*
     Input class for JavadocType
  */
  public class InputJavadocTypeAboveComments { }

}
