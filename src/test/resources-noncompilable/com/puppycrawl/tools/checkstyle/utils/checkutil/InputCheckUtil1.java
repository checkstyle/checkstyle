/*
com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck
allowMissingPropertyJavadoc = true


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.utils.checkutil;

public class InputCheckUtil1 {

    public void validAssign(String result) { // violation 'Missing a Javadoc comment'
         result = switch ("in") {
              case "correct" -> "true";
              default -> "also correct";
          };
    }
}
