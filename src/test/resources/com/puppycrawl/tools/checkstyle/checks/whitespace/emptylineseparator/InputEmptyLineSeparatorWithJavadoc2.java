/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator">
      <property name="allowNoEmptyLineBetweenFields" value="true"/>
      <property name="allowMultipleEmptyLines" value="false"/>
      <property name="allowMultipleEmptyLinesInsideClassMembers" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import jakarta.annotation.Nullable;

public class InputEmptyLineSeparatorWithJavadoc2 {

  public static class Good {
      /** my javadoc */
      public int [][] myInt = null;

      /** my javadoc */
      public int myOtherInt[][] = null;

      /**
        * My javadoc...
        */
      public static
          int [][] test10() throws Exception {
        return new int [][] {};
      }

      /**
        * My javadoc...
        *
        */
      public static
          int [] test11() throws Exception {
        return new int [] {};
      }

        /**
        * My javadoc...
        *
        */
      public static
          int [] test12() throws Exception {
        return new int [] {};
      }

        /**
        * My javadoc...
        *
        */
      public static
          @Nullable int [] test13() throws Exception {
        return new int [] {};
      }
  }

  public static class Bad {


    /** my javadoc */
    public int [][] myInt = null;
    // violation 2 lines above "has more than 1 empty lines before"


    /** my javadoc */
    public int myOtherInt[][] = null;
    // violation 2 lines above "has more than 1 empty lines before"


    /**
      * My javadoc...
      */
    public static
        int [][] test10() throws Exception {
      // violation 5 lines above "has more than 1 empty lines before"
      return new int [][] {};
    }


    /**
      * My javadoc...
      *
      */
    public static
        int [] test11() throws Exception {
      // violation 6 lines above "has more than 1 empty lines before"
      return new int [] {};
    }


      /**
      * My javadoc...
      *
      */
    public static
        int [] test12() throws Exception {
      // violation 6 lines above "has more than 1 empty lines before"
      return new int [] {};
    }


      /**
      * My javadoc...
      *
      */
    public static
        @Nullable int [] test13() throws Exception {
      // violation 6 lines above "has more than 1 empty lines before"
      return new int [] {};
    }

  }
}
