/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SayHello"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.sayhello;

// xdoc section -- start
public class Example1 { // violation, 'Say Hello'
  public void sayHello() {
  }
}

class ExampleAnother { // ok, has hello variable
  String hello = "hello";
}

class ExampleWithOtherVariable { // violation, variable exists but isn't 'hello'
  String world = "world";
}

// xdoc section -- end
