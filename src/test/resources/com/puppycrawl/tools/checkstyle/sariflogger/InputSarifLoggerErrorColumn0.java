/*xml
<module name="Checker">
  <module name="LineLength">
    <property name="max" value="80"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.sariflogger;

public class InputSarifLoggerErrorColumn0 {
    public void method() { // violation 'Line is longer than 80 characters.'
        System.out.println("This is a very long line that will exceed the 80 character limit.");
    }
}

