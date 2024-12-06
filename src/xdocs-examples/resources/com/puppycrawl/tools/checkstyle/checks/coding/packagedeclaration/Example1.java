/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PackageDeclaration"/>
  </module>
</module>
*/

// xdoc section -- start
public class Example1{ // violation, 'Missing package declaration'
  String str = "Some Content";
}
// xdoc section -- end
