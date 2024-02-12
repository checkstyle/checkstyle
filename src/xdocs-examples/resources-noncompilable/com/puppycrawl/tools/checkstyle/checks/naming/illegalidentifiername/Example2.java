/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalIdentifierName">
      <property name="format"
        value="(?i)^(?!(record|yield|var|permits|sealed|open|transitive|_)$).+$"/>
    </module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

// xdoc section -- start
public class Example2 {
  public static void main(String... args) {
    var var = 4; // violation
    int record = 15; // violation
    String yield = "yield"; // violation

    record Record (Record record){} // 2 violations

    String yieldString = "yieldString"; // ok, part of another word
    record MyRecord(){} // ok, part of another word
    var variable = 2; // ok, part of another word
    String __; // ok, because underscore is part of another word

    int open = 4; // violation
    Object transitive = "transitive"; // violation

    int openInt = 4; // ok, "open" is part of another word
    Object transitiveObject = "transitiveObject";
    // ok above, "transitive" is part of another word
  }
}
// xdoc section -- end
