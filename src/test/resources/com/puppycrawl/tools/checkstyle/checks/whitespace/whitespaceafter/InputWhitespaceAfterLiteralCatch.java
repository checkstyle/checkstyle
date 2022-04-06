/*
WhitespaceAfter
tokens = LITERAL_CATCH


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralCatch {
    public static void main(String[] args) {

      try {} catch (Exception e){} // OK
      try {} catch(Exception e){} // violation ''catch' is not followed by whitespace'

      try {} catch (Error e){} finally {} // OK
      try {} catch(Error e){} finally {} // violation ''catch' is not followed by whitespace'
    }
}
