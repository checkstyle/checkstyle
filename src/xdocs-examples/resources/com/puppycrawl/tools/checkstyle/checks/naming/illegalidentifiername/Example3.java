/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalIdentifierName">
      <property name="format"
        value="(?i)^(?!(when|record|yield|var|permits|sealed|open|transitive|_)$|(.*\$)).+$"/>
      <property name="tokens" value="RECORD_DEF"/>
    </module>
  </module>
</module>
*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

// xdoc section - start
public class Example3 {
  Integer var = 4;
  int record = 15;
  String yield = "yield";

  String test$stuff = "test";
  String when = "today";
  record Record(Record r){} // violation 'Name 'Record' must match pattern'

  record R(Record record){}

  String yieldString = "yieldString";

  record MyRecord(){}

  Integer variable = 2;

  int open = 4;
  Object transitive = "transitive";

  int openInt = 4;

  Object transitiveObject = "transitiveObject";

}
// xdoc section - end
