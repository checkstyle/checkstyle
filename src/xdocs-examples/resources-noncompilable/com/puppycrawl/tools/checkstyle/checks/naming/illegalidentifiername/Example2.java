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
    int open = 4; // violation
    Object transitive = "transitive"; // violation

    int openInt = 4; // ok, "open" is part of another word
    Object transitiveObject = "transitiveObject";
    // ok above, "transitive" is part of another word
  }
}
// xdoc section -- end
