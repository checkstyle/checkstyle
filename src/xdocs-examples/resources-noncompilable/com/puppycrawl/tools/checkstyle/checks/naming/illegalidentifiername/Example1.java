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
  Integer var = 4; // violation, 'Name 'var' must match pattern'
  int record = 15; // violation, 'Name 'record' must match pattern'
  String yield = "yield";
  // violation above, 'Name 'yield' must match pattern'

  record Record(Record r){} // violation, 'Name 'Record' must match pattern'

  record R(Record record){} // violation, 'Name 'record' must match pattern'

  String yieldString = "yieldString";
  // ok above, word 'yield' is not used as an identifier by itself
  record MyRecord(){}
  // ok above, word 'Record' is not used as an identifier by itself
  Integer variable = 2;
  // ok above, word 'var' is not used as an identifier by itself

  int open = 4; // ok, word 'open' can be used as an identifier
  Object transitive = "transitive";
  // ok above, word 'transitive' can be used as an identifier

  int openInt = 4;
  // ok above, word 'openInt' can be used as an identifier
  Object transitiveObject = "transitiveObject";
  // ok above, word 'transitiveObject' can be used as an identifier
}
// xdoc section -- end
