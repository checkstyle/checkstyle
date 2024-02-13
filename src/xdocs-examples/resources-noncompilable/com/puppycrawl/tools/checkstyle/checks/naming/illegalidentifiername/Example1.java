/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalIdentifierName"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

// xdoc section -- start
public class Example1 {
  public static void main(String... args) {
    var var = 4; // violation
    int record = 15; // violation
    String yield = "yield"; // violation

    record Record (Record record){} // 2 violations

    String yieldString = "yieldString"; // ok, part of another word
    record MyRecord(){} // ok, part of another word
    var variable = 2; // ok, part of another word
    String _; // violation
  }
}
// xdoc section -- end
