/*
com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck
allowMissingPropertyJavadoc = true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodBasic {

    public void validAssign(String result) { // violation 'Missing a Javadoc comment'
         result = switch ("in") {
              case "correct" -> "true";
              default -> "also correct";
          };
    }
}
