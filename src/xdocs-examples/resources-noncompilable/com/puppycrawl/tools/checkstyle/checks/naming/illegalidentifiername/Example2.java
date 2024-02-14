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
  var var = 4; // violation, 'Name 'var' must match pattern'
  int record = 15; // violation, 'Name 'record' must match pattern'
  String yield = "yield";
  // violation above, 'Name 'yield' must match pattern'

  record Record(Record r){} // violation, 'Name 'Record' must match pattern'

  record R(Record record){} // violation, 'Name 'record' must match pattern'

  String yieldString = "yieldString";
  // ok above, 'yield' is part of broader term, it is not alone
  record MyRecord(){}
  // ok above, 'Record' is part of broader term, it is not alone
  var variable = 2;
  // ok above, 'var' is part of broader term, it is not alone

  int open = 4; // violation, 'Name 'open' must match pattern'
  Object transitive = "transitive";
  // violation above, 'Name 'transitive' must match pattern'

  int openInt = 4;
  // ok above, 'open' is part of broader term, it is not alone
  Object transitiveObject = "transitiveObject";
  // ok above, 'transitive' is part of broader term, it is not alone
}
// xdoc section -- end
