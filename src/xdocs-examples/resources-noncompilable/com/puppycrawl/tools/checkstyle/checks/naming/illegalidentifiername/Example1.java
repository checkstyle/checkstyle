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

  int open = 4;
  // ok above, because match the pattern
  Object transitive = "transitive";
  // ok above, because match the pattern

  int openInt = 4;
  // ok above, because match the pattern
  Object transitiveObject = "transitiveObject";
  // ok above, because match the pattern
}
// xdoc section -- end
