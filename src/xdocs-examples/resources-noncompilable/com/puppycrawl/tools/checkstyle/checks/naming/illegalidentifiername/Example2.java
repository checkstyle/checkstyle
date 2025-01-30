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
  Integer var = 4; // violation, 'Name 'var' must match pattern'
  int record = 15; // violation, 'Name 'record' must match pattern'
  String yield = "yield";
  // violation above, 'Name 'yield' must match pattern'

  record Record(Record r){} // violation, 'Name 'Record' must match pattern'

  record R(Record record){} // violation, 'Name 'record' must match pattern'

  String yieldString = "yieldString";
  //above, word 'yield' is not used as an identifier by itself
  record MyRecord(){}
  //above, word 'Record' is not used as an identifier by itself
  Integer variable = 2;
  //above, word 'var' is not used as an identifier by itself

  int open = 4; // violation, 'Name 'open' must match pattern'
  Object transitive = "transitive";
  // violation above, 'Name 'transitive' must match pattern'

  int openInt = 4;
  //above, word 'open' is not used as an identifier by itself
  Object transitiveObject = "transitiveObject";
  //above, word 'transitive' is not used as an identifier by itself
}
// xdoc section -- end
