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
  int record = 15;
  String yield = "yield";

  String test$stuff = "test"; // violation, 'must match pattern'
  String when = "today";
  record Record(Record r){}

  record R(Record record){}

  String yieldString = "yieldString";

  record MyRecord(){}

  Integer variable = 2;

  int open = 4;
  Object transitive = "transitive";

  int openInt = 4;

  Object transitiveObject = "transitiveObject";

}
// xdoc section -- end
