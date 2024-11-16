/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingSwitchDefault"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

// xdoc section -- start
class Example2 {
  void Example2(int i , int o){
    switch (i) {
      case 1:
        break;
      case 2:
        break;
      default:
        break;
    }
    switch (o) {
      case String s: // type pattern
        System.out.println(s);
        break;
      case Integer i: // type pattern
        System.out.println("Integer");
        break;
      default:    // will not compile without default label
        break;
    }
  }
}
// xdoc section -- end
