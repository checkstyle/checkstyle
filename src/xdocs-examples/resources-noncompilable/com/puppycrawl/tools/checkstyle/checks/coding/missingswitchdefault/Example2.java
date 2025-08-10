/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingSwitchDefault"/>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

// xdoc section -- start
class Example2 {
  Example2(int i){
    switch (i) {
      case 1:
        break;
      case 2:
        break;
      default:
        break;
    }
    Object obj = "example";
    switch (obj) {
      case String s: // type pattern
        System.out.println(s);
        break;
      case Integer j: // type pattern
        System.out.println("Integer");
        break;
      default:    // will not compile without default label
        System.out.println("Unknown type");
        break;
    }
  }
}
// xdoc section -- end
