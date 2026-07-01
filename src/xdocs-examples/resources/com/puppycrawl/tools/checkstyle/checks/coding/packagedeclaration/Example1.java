/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PackageDeclaration"/>
  </module>
</module>
*/

// xdoc section -- start
package com.nonexistent.packages;
public class Example1{ // violation above 'Package name is not same as directory'
  String str = "Some Content";
}
// xdoc section -- end
